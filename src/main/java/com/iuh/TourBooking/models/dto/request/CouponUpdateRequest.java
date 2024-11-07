package com.iuh.TourBooking.models.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CouponUpdateRequest {
    private int discount;
    private String description;
    private boolean activeCoupon;
}
