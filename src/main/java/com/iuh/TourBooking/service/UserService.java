package com.iuh.TourBooking.service;

import com.iuh.TourBooking.models.User;
import com.iuh.TourBooking.models.dto.request.UserCreateRequest;
import com.iuh.TourBooking.models.dto.request.UserUpdatePasswordRequest;
import com.iuh.TourBooking.models.dto.request.UserUpdateRequest;
import com.iuh.TourBooking.models.dto.response.UserResponse;
import org.bson.types.ObjectId;

import java.util.List;

public interface UserService {
    public List<User> getAllUserDB();
    public UserResponse createUser(UserCreateRequest userCreateRequest);
    public UserResponse createUserAdmin(UserCreateRequest userCreateRequest);
    public UserResponse updateUserByEmail(String email, UserUpdateRequest userUpdateRequest);
    public UserResponse updateUserById(ObjectId id, UserUpdateRequest userUpdateRequest);
    public void deleteUserByEmail(String email);
    public void deleteUserByUserId(ObjectId userId);
    public List<UserResponse> getAllUser();
    public UserResponse getUserByEmail(String email);
    public UserResponse getUserByUserId(ObjectId userId);
    public UserResponse getMyinfo();

    public List<UserResponse> searchUsers(String username, String email, String phoneNumber, int limit);

    public UserResponse updateUserPassword(ObjectId id, UserUpdatePasswordRequest userUpdatePasswordRequest);

}
