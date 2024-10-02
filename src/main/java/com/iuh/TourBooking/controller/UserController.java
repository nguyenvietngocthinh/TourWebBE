package com.iuh.TourBooking.controller;

import com.iuh.TourBooking.models.User;
import com.iuh.TourBooking.models.dto.request.UserCreateRequest;
import com.iuh.TourBooking.models.dto.request.UserUpdateRequest;
import com.iuh.TourBooking.models.dto.response.ApiResponse;
import com.iuh.TourBooking.models.dto.response.UserResponse;
import com.iuh.TourBooking.service.UserService;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController{
    @Autowired
    private UserService userService;

    @PostMapping
    private ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreateRequest userCreateRequest) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.createUser(userCreateRequest))
                .build();
    }

    @GetMapping
    ApiResponse<List<UserResponse>> getAllUsers() {
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getAllUser())
                .build();
    }

    @GetMapping("/phone/{phoneNumber}")
    private ApiResponse<UserResponse> getUserByPhoneNumber (@PathVariable("phoneNumber") String phoneNumber) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUserByPhoneNumber(phoneNumber))
                .build();
    }

    @GetMapping("{email}")
    private ApiResponse<UserResponse> getUserByEmail (@PathVariable("email") String email) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUserByEmail(email))
                .build();
    }

    @GetMapping("/myinfo")
    private UserResponse getMyInfo () {
        return userService.getMyinfo();
    }

    @GetMapping("/id/{userId}")
    private UserResponse getUserByUserID(@PathVariable("userId") ObjectId userId) {
        return userService.getUserByUserId(userId);
    }

    @PutMapping("/phone/{phoneNumber}")
    private UserResponse updateUserByPhoneNumber(@PathVariable String phoneNumber ,@RequestBody UserUpdateRequest userUpdateRequest) {
        return userService.updateUserByPhoneNumber(phoneNumber, userUpdateRequest);
    }

    @PutMapping("/{email}")
    private UserResponse updateUserByEmail(@PathVariable String email ,@RequestBody UserUpdateRequest userUpdateRequest) {
        return userService.updateUserByEmail(email, userUpdateRequest);
    }

    @PutMapping("/id/{userId}")
    private UserResponse updateUserByUserId(@PathVariable ObjectId userId ,@RequestBody UserUpdateRequest userUpdateRequest) {
        return userService.updateUserByUserId(userId, userUpdateRequest);
    }

    @DeleteMapping("/phone/{phoneNumber}")
    private ApiResponse<String> deleteUserByPhoneNumber(@PathVariable String phoneNumber) {
        userService.deleteUserByPhoneNumber(phoneNumber);
        return ApiResponse.<String>builder().result("User has been deleted").build();
    }

    @DeleteMapping("{admin}")
    private ApiResponse<String> deleteUserByEmail(@PathVariable String email) {
        if (email == "admin@gmail.com"){
            return ApiResponse.<String>builder().result("User can not been deleted").build();
        }
        userService.deleteUserByEmail(email);
        return ApiResponse.<String>builder().result("User has been deleted").build();
    }

    @DeleteMapping("/id/{userId}")
    private ApiResponse<String> deleteUserByUserId(@PathVariable ObjectId userId){
        userService.deleteUserByUserId(userId);
        return ApiResponse.<String>builder().result("User has been deleted").build();
    }
}
