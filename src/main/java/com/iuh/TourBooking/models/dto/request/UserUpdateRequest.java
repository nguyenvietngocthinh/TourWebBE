package com.iuh.TourBooking.models.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserUpdateRequest {
    private String password;
    private String username;
    private String email;
    private String address;
    private Boolean gender;
    private Date dateOfBirth;
}
