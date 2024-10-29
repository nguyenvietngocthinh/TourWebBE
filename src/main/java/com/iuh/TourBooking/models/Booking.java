package com.iuh.TourBooking.models;


import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "bookings")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Booking {
    @Id
    private ObjectId id;

    private String bookingId;
    private String bookingCode;
    private String usernameContract;
    private String emailContract;
    private String phoneNumberContract;
    private String address;
    private List<Customer> customers;
    private String note;

    private String typePay;
    private boolean isPay;
}
