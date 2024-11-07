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

    private String bookingCode;
    private String customerName;
    private String customerEmail;
    private String customerPhoneNumber;
    private String customerCity;
    private String customerDistrict;
    private String customerAddress;
    private int numberOfCustomer;
    private String bookingDate;
    private String expectedDate;
    private String note;

    private String tourCode;

    private boolean saleTour;
    private int percentSale;

    private String typePay;
    private double totalMoney;
    private boolean payBooking;
    private boolean activeBooking;
}
