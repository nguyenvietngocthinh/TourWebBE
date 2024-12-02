package com.iuh.TourBooking.service.impl;

import com.iuh.TourBooking.enums.ErrorCode;
import com.iuh.TourBooking.enums.Role;
import com.iuh.TourBooking.exception.AppException;
import com.iuh.TourBooking.mappers.UserMapper;
import com.iuh.TourBooking.models.User;
import com.iuh.TourBooking.models.dto.request.UserCreateRequest;
import com.iuh.TourBooking.models.dto.request.UserUpdatePasswordRequest;
import com.iuh.TourBooking.models.dto.request.UserUpdateRequest;
import com.iuh.TourBooking.models.dto.response.UserResponse;
import com.iuh.TourBooking.repository.UserRepository;
import com.iuh.TourBooking.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public UserResponse createUser(UserCreateRequest userCreateRequest) {
        if (userRepository.existsByEmail(userCreateRequest.getEmail())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        User user = userMapper.toUser(userCreateRequest);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(userCreateRequest.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());

        user.setRoles(roles);
        user.setIsOnline(false);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public UserResponse createUserAdmin(UserCreateRequest userCreateRequest) {
        if (userRepository.existsByEmail(userCreateRequest.getEmail())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        User user = userMapper.toUser(userCreateRequest);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(userCreateRequest.getPassword()));

        user.setIsOnline(false);



        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public UserResponse updateUserByEmail(String email, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userMapper.updateUser(user, userUpdateRequest);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public UserResponse updateUserById(ObjectId id, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userMapper.updateUser(user, userUpdateRequest);

        return userMapper.toUserResponse(userRepository.save(user));
    }



    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public void deleteUserByEmail(String email) {
        userRepository.deleteByEmail(email);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public void deleteUserByUserId(ObjectId userId) {
        userRepository.deleteById(userId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public List<UserResponse> getAllUser() {
        log.info("In method get Users");
        return userRepository.findAll().stream()
                .filter(user -> !"admin@gmail.com".equals(user.getEmail()))  // Bỏ qua user có email là admin@gmail.com
                .map(userMapper::toUserResponse)
                .toList();
    }

    @Override
    public List<User> getAllUserDB() {
        return userRepository.findAll();  // Giả sử bạn đang lấy tất cả người dùng từ cơ sở dữ liệu
    }


    @PostAuthorize("returnObject.email == authentication.name || hasRole('ADMIN')")
    @Override
    public UserResponse getUserByEmail(String email) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userMapper.toUserResponse(userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found")));
    }

    @PostAuthorize("returnObject.email == authentication.name || hasRole('ADMIN')")
    @Override
    public UserResponse getUserByUserId(ObjectId userId) {
        return userMapper.toUserResponse(userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found")));
    }

    @Override
    public UserResponse getMyinfo() {
        var context = SecurityContextHolder.getContext();
        String email = context.getAuthentication().getName();
        log.info("Email of authenticated user: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(user);
    }

    @Override
    public List<UserResponse> searchUsers(String username, String email, String phoneNumber, int limit) {
        // Tạo query với các điều kiện lọc
        Query query = new Query();

        // Nếu tên người dùng được cung cấp, tìm kiếm theo tên
        if (username != null && !username.isEmpty()) {
            query.addCriteria(Criteria.where("username").regex(username, "i"));  // Tìm kiếm theo tên (không phân biệt chữ hoa/thường)
        }

        // Nếu email được cung cấp, tìm kiếm theo email
        if (email != null && !email.isEmpty()) {
            query.addCriteria(Criteria.where("email").regex(email, "i"));  // Tìm kiếm theo email
        }

        // Nếu số điện thoại được cung cấp, tìm kiếm theo số điện thoại
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            query.addCriteria(Criteria.where("phoneNumber").regex(phoneNumber, "i"));  // Tìm kiếm theo số điện thoại
        }

        // Loại trừ tài khoản có email là admin@gmail.com
        query.addCriteria(Criteria.where("email").ne("admin@gmail.com"));

        // Giới hạn số lượng kết quả trả về
        query.limit(limit > 0 ? limit : 3);  // Mặc định trả về 3 kết quả nếu không có tham số giới hạn

        // Thực hiện tìm kiếm và trả về kết quả
        List<User> users = mongoTemplate.find(query, User.class);

        // Chuyển đổi kết quả tìm kiếm thành danh sách UserResponse
        return users.stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse updateUserPassword(ObjectId id, UserUpdatePasswordRequest userUpdatePasswordRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userMapper.updateUserPassword(user, userUpdatePasswordRequest);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(userUpdatePasswordRequest.getPassword()));

        return userMapper.toUserResponse(userRepository.save(user));
    }


}
