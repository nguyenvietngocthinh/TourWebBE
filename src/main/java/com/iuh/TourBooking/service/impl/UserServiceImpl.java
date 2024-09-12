package com.iuh.TourBooking.service.impl;

import com.iuh.TourBooking.models.User;
import com.iuh.TourBooking.repository.UserRepository;
import com.iuh.TourBooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }
}
