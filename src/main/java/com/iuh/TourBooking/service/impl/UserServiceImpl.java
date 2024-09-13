package com.iuh.TourBooking.service.impl;

import com.iuh.TourBooking.models.User;
import com.iuh.TourBooking.models.dto.request.UserCreateRequest;
import com.iuh.TourBooking.models.dto.request.UserUpdateRequest;
import com.iuh.TourBooking.repository.UserRepository;
import com.iuh.TourBooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(UserCreateRequest userCreateRequest) {
        User user = new User();

        user.setPhoneNumber(userCreateRequest.getPhoneNumber());
        user.setPassword(userCreateRequest.getPassword());
        user.setUsername(userCreateRequest.getUsername());
        user.setEmail(userCreateRequest.getEmail());
        user.setAddress(userCreateRequest.getAddress());
        user.setGender(userCreateRequest.getGender());
        user.setDateOfBirth(userCreateRequest.getDateOfBirth());

        return userRepository.save(user);
    }

    @Override
    public User updateUserByPhoneNumber(String phoneNumber, UserUpdateRequest userUpdateRequest) {
        User user = getUserByPhoneNumber(phoneNumber);

        user.setUsername(userUpdateRequest.getUsername());
        user.setPassword(userUpdateRequest.getPassword());
        user.setEmail(userUpdateRequest.getEmail());
        user.setAddress(userUpdateRequest.getAddress());
        user.setGender(userUpdateRequest.getGender());
        user.setDateOfBirth(userUpdateRequest.getDateOfBirth());

        return userRepository.save(user);
    }

    @Override
    public void deleteUserByPhoneNumber(String phoneNumber) {
        userRepository.deleteByPhoneNumber(phoneNumber);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }


}
