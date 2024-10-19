package com.iuh.TourBooking.service.impl;

import com.iuh.TourBooking.enums.ErrorCode;
import com.iuh.TourBooking.enums.Role;
import com.iuh.TourBooking.exception.AppException;
import com.iuh.TourBooking.mappers.UserMapper;
import com.iuh.TourBooking.models.User;
import com.iuh.TourBooking.models.dto.request.UserCreateRequest;
import com.iuh.TourBooking.models.dto.request.UserUpdateRequest;
import com.iuh.TourBooking.models.dto.response.UserResponse;
import com.iuh.TourBooking.repository.UserRepository;
import com.iuh.TourBooking.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

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
    public UserResponse updateUserByUserId(ObjectId userId, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findById(userId)
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

}
