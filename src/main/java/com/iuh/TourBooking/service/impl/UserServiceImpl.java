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
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public User createUser(UserCreateRequest userCreateRequest) {
        User user = new User();

        if (userRepository.existsByPhoneNumber(userCreateRequest.getPhoneNumber())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        user = userMapper.toUser(userCreateRequest);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(userCreateRequest.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
        user.setRoles(roles);

        return userRepository.save(user);
    }

    @Override
    public UserResponse updateUserByPhoneNumber(String phoneNumber, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userMapper.updateUser(user, userUpdateRequest);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public UserResponse updateUserByUserId(ObjectId userId, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(userUpdateRequest.getUsername());
        user.setPassword(userUpdateRequest.getPassword());
        user.setEmail(userUpdateRequest.getEmail());
        user.setAddress(userUpdateRequest.getAddress());
        user.setGender(userUpdateRequest.getGender());
        user.setDateOfBirth(userUpdateRequest.getDateOfBirth());

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public void deleteUserByPhoneNumber(String phoneNumber) {
        userRepository.deleteByPhoneNumber(phoneNumber);
    }

    @Override
    public void deleteUserByUserId(ObjectId userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public UserResponse getUserByPhoneNumber(String phoneNumber) {
        return userMapper.toUserResponse(userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("User not found")));
    }

    @Override
    public UserResponse getUserByUserId(ObjectId userId) {
        return userMapper.toUserResponse(userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found")));
    }

}
