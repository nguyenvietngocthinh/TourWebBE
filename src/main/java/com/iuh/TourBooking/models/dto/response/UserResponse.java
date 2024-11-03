package com.iuh.TourBooking.models.dto.response;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    private ObjectId id;
    String phoneNumber;
    String username;
    String email;
    String address;
    Boolean gender;
    Date dateOfBirth;
    Boolean isOnline;
    Set<String> roles;

}
