package com.iuh.TourBooking.service;

public interface EmailSenderService {
    void send(String toEmail, String subject, String body);
}
