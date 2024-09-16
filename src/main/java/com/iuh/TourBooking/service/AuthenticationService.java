package com.iuh.TourBooking.service;

import com.iuh.TourBooking.models.dto.request.AuthenticationRequest;

public interface AuthenticationService {
    public boolean authenticate(AuthenticationRequest authenticationRequest);
}
