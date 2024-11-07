package com.iuh.TourBooking.models.dto.request;

import com.iuh.TourBooking.models.Customer;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingUpdateRequest {
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
