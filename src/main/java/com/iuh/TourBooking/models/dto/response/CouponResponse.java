package com.iuh.TourBooking.models.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CouponResponse {
    private ObjectId id;
    private String codeCoupon;
    private int discount;
    private String description;
    private boolean activeCoupon;
}
