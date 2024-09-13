package com.iuh.TourBooking.controller;

import com.iuh.TourBooking.models.User;
import com.iuh.TourBooking.models.dto.request.UserCreateRequest;
import com.iuh.TourBooking.models.dto.request.UserUpdateRequest;
import com.iuh.TourBooking.service.UserService;
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
    private User createUser(@RequestBody UserCreateRequest userCreateRequest) {
        return userService.createUser(userCreateRequest);
    }

    @GetMapping
    private List<User> getAllUser() {
        return userService.getAllUser();
    }

    @GetMapping("{phoneNumber}")
    private User getUserByPhoneNumber (@PathVariable("phoneNumber") String phoneNumber) {
        return userService.getUserByPhoneNumber(phoneNumber);
    }

    @PutMapping("{phoneNumber}")
    private User updateUser(@PathVariable String phoneNumber ,@RequestBody UserUpdateRequest userUpdateRequest) {
        return userService.updateUserByPhoneNumber(phoneNumber, userUpdateRequest);
    }

    @DeleteMapping("{phoneNumber}")
    private String deleteUser(@PathVariable String phoneNumber) {
        userService.deleteUserByPhoneNumber(phoneNumber);
        return "Delete success";
    }
}
