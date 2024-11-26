package com.iuh.TourBooking.service;

import com.iuh.TourBooking.models.Booking;
import com.iuh.TourBooking.models.dto.response.PaymentResponse;
import com.stripe.exception.StripeException;
import jakarta.servlet.http.HttpServletRequest;

public interface PaymentService {
    public PaymentResponse createPaymentLink(Booking booking) throws StripeException;
}
