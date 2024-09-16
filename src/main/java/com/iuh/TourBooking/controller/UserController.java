package com.iuh.TourBooking.controller;

import com.iuh.TourBooking.models.User;
import com.iuh.TourBooking.models.dto.request.UserCreateRequest;
import com.iuh.TourBooking.models.dto.request.UserUpdateRequest;
import com.iuh.TourBooking.models.dto.response.ApiResponse;
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
    private ApiResponse<User> createUser(@RequestBody @Valid UserCreateRequest userCreateRequest) {
        ApiResponse<User> apiResponse = new ApiResponse<>();

        apiResponse.setResult(userService.createUser(userCreateRequest));

        return apiResponse;
    }

    @GetMapping
    private List<User> getAllUser() {
        return userService.getAllUser();
    }

    @GetMapping("{phoneNumber}")
    private User getUserByPhoneNumber (@PathVariable("phoneNumber") String phoneNumber) {
        return userService.getUserByPhoneNumber(phoneNumber);
    }

    @GetMapping("/id/{userId}")
    private User getUserByUserID(@PathVariable("userId") ObjectId userId) {
        return userService.getUserByUserId(userId);
    }

    @PutMapping("{phoneNumber}")
    private User updateUserByPhoneNumber(@PathVariable String phoneNumber ,@RequestBody UserUpdateRequest userUpdateRequest) {
        return userService.updateUserByPhoneNumber(phoneNumber, userUpdateRequest);
    }
    @PutMapping("/id/{userId}")
    private User updateUserByUserId(@PathVariable ObjectId userId ,@RequestBody UserUpdateRequest userUpdateRequest) {
        return userService.updateUserByUserId(userId, userUpdateRequest);
    }

    @DeleteMapping("{phoneNumber}")
    private String deleteUserByPhoneNumber(@PathVariable String phoneNumber) {
        userService.deleteUserByPhoneNumber(phoneNumber);
        return "Delete success";
    }

    @DeleteMapping("/id/{userId}")
    private String deleteUserByUserId(@PathVariable ObjectId userId){
        userService.deleteUserByUserId(userId);
        return "Delete success";
    }
}
