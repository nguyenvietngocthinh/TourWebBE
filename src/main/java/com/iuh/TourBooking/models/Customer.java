package com.iuh.TourBooking.models;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Date;

@Document(collection = "customers")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {
    @Id
    private ObjectId id;

    private String customerName;
    private String customerEmail;
    private String customerPhoneNumber;
    private Date CustomerDateOfBirth;
    private String customerType;
    private String customerCity;
    private String customerDistrict;
    private String customerAddress;
}
