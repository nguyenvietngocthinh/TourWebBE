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
    @Size(min = 0 ,message = "EMAIL_INVALID")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$", message = "EMAIL_INVALID")
    String email;

    @Size(min = 1, message = "PASSWORD_INVALID")
    String password;
    String username;
    String phoneNumber;
    String address;
    Boolean gender;
    Date dateOfBirth;
}
