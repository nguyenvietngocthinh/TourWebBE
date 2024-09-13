package com.iuh.TourBooking.service;

import com.iuh.TourBooking.models.User;
import com.iuh.TourBooking.models.dto.request.UserCreateRequest;
import com.iuh.TourBooking.models.dto.request.UserUpdateRequest;
import org.bson.types.ObjectId;

import java.util.List;

public interface UserService {
    public User createUser(UserCreateRequest userCreateRequest);
    public User updateUserByPhoneNumber(String phoneNumber, UserUpdateRequest userUpdateRequest);
    public User updateUserByUserId(ObjectId userId, UserUpdateRequest userUpdateRequest);
    public void deleteUserByPhoneNumber(String phoneNumber);
    public void deleteUserByUserId(ObjectId userId);
    public List<User> getAllUser();
    public User getUserByPhoneNumber(String phoneNumber);
    public User getUserByUserId(ObjectId userId);
}
