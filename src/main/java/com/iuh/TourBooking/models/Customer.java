package com.iuh.TourBooking.models;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Customer {
    private String id;
    private String typeCustomer;
    private String usernameBooking;
    private Boolean genderBooking;
    private LocalDate dateOfBirthBooking;
    private Double additionalFee;
}
