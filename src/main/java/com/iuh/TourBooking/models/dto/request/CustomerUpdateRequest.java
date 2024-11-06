package com.iuh.TourBooking.models.dto.request;

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
public class CustomerUpdateRequest {
    private String customerName;
    private String customerEmail;
    private String customerPhoneNumber;
    private Date CustomerDateOfBirth;
    private String customerType;
    private String customerCity;
    private String customerDistrict;
    private String customerAddress;
}
