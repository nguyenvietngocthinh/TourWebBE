package com.iuh.TourBooking.service;

import com.iuh.TourBooking.models.dto.request.AuthenticationRequest;
import com.iuh.TourBooking.models.dto.request.IntrospectRequest;
import com.iuh.TourBooking.models.dto.response.AuthenticationResponse;
import com.iuh.TourBooking.models.dto.response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthenticationService {
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);

    public IntrospectResponse introspect(IntrospectRequest introspectRequest) throws JOSEException, ParseException;
}
