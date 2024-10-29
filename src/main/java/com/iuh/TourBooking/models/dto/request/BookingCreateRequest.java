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
public class BookingCreateRequest {
    private String bookingCode;
    private String usernameContract;
    private String emailContract;
    private String phoneNumberContract;
    private String address;
    private List<Customer> customers;
    private String note;

    private String typePay;
    private double totalMoney;
    private boolean isPay;
}
