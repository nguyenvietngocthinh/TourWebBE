package com.iuh.TourBooking.service.impl;

import com.iuh.TourBooking.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void send(String toEmail, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("nvnthinh12a6@gmail.com");
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(body);

            javaMailSender.send(message);
            System.out.println("Email sent successfully to " + toEmail);
        } catch (Exception e) {
            System.err.println("Failed to send email to " + toEmail + ". Error: " + e.getMessage());
        }
    }
}
