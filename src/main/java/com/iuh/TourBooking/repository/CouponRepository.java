package com.iuh.TourBooking.repository;

import com.iuh.TourBooking.models.Coupon;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponRepository extends MongoRepository<Coupon, ObjectId> {
    boolean existsByCodeCoupon(String codeCoupon);
    Optional<Coupon> findByCodeCoupon(String codeCoupon);
    void deleteByCodeCoupon(String codeCoupon);
}
