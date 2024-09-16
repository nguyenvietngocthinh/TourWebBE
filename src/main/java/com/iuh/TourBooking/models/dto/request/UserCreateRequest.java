package com.iuh.TourBooking.models.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreateRequest {
    @Size(min = 10, max = 10,message = "PHONENUMBER_INVALID")
    @Pattern(regexp = "0\\d{9}",message = "PHONENUMBER_INVALID")
    String phoneNumber;

    @Size(min = 1, message = "PASSWORD_INVALID")
    String password;
    String username;
    String email;
    String address;
    Boolean gender;
    Date dateOfBirth;
}
