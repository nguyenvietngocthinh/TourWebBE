package com.iuh.TourBooking.models;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "coupons")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Coupon {
    @Id
    private ObjectId id;

    private String codeCoupon;
    private int discount;
    private String description;
    private boolean activeCoupon;
}
