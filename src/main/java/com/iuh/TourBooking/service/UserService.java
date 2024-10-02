package com.iuh.TourBooking.service;

import com.iuh.TourBooking.models.User;
import com.iuh.TourBooking.models.dto.request.UserCreateRequest;
import com.iuh.TourBooking.models.dto.request.UserUpdateRequest;
import com.iuh.TourBooking.models.dto.response.UserResponse;
import org.bson.types.ObjectId;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface UserService {
    public UserResponse createUser(UserCreateRequest userCreateRequest);
    public UserResponse updateUserByPhoneNumber(String phoneNumber, UserUpdateRequest userUpdateRequest);
    public UserResponse updateUserByEmail(String email, UserUpdateRequest userUpdateRequest);
    public UserResponse updateUserByUserId(ObjectId userId, UserUpdateRequest userUpdateRequest);
    public void deleteUserByPhoneNumber(String phoneNumber);
    public void deleteUserByEmail(String email);
    public void deleteUserByUserId(ObjectId userId);
    public List<UserResponse> getAllUser();
    public UserResponse getUserByPhoneNumber(String phoneNumber);
    public UserResponse getUserByEmail(String email);
    public UserResponse getUserByUserId(ObjectId userId);
    public UserResponse getMyinfo();
}
