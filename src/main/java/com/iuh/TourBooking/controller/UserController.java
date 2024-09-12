package com.iuh.TourBooking.controller;

import com.iuh.TourBooking.models.User;
import com.iuh.TourBooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController{
    @Autowired
    private UserService userService;

    @PostMapping
    private User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }
}
