package com.iuh.TourBooking.service;

import com.iuh.TourBooking.models.dto.request.AuthenticationRequest;
import com.iuh.TourBooking.models.dto.response.AuthenticationResponse;

public interface AuthenticationService {
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
}
