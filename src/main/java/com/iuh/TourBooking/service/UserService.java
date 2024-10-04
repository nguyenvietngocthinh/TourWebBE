package com.iuh.TourBooking.service;

import com.iuh.TourBooking.models.User;
import com.iuh.TourBooking.models.dto.request.UserCreateRequest;
import com.iuh.TourBooking.models.dto.request.UserUpdateRequest;
import com.iuh.TourBooking.models.dto.response.UserResponse;
import org.bson.types.ObjectId;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface UserService {
    public List<User> getAllUserDB();
    public UserResponse createUser(UserCreateRequest userCreateRequest);
    public UserResponse updateUserByEmail(String email, UserUpdateRequest userUpdateRequest);
    public UserResponse updateUserByUserId(ObjectId userId, UserUpdateRequest userUpdateRequest);
    public void deleteUserByEmail(String email);
    public void deleteUserByUserId(ObjectId userId);
    public List<UserResponse> getAllUser();
    public UserResponse getUserByEmail(String email);
    public UserResponse getUserByUserId(ObjectId userId);
    public UserResponse getMyinfo();
}
