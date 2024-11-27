package com.iuh.TourBooking.models.dto.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerStatistics {
    private String customerEmail;
    private String customerName;
    private double totalSpent;  // Tổng tiền đã chi cho các booking
}
