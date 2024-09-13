package com.iuh.TourBooking.models.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserCreateRequest {
    @Size(min = 10, max = 10, message = "Phone number must be 10 characters")
    @Pattern(regexp = "0\\d{9}", message = "Phone number must start with 0 and be 10 digits long")
    private String phoneNumber;


    private String password;
    private String username;
    private String email;
    private String address;
    private Boolean gender;
    private Date dateOfBirth;
}
