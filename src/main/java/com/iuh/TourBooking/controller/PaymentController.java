package com.iuh.TourBooking.controller;

import com.iuh.TourBooking.models.Booking;
import com.iuh.TourBooking.models.dto.response.PaymentResponse;
import com.iuh.TourBooking.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/payment-link")
    public PaymentResponse createPaymentLink(@RequestBody Booking booking) throws Exception {
        return paymentService.createPaymentLink(booking);
    }
}
