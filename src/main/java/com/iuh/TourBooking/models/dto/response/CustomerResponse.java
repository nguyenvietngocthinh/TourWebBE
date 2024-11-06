package com.iuh.TourBooking.models.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.bson.types.ObjectId;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerResponse {
    private ObjectId id;
    private String customerName;
    private String customerEmail;
    private String customerPhoneNumber;
    private Date CustomerDateOfBirth;
    private String customerType;
    private String customerCity;
    private String customerDistrict;
    private String customerAddress;

    public String getIdAsString() {
        return id != null ? id.toHexString() : null;
    }
}
