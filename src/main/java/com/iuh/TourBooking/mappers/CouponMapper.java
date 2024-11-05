package com.iuh.TourBooking.mappers;

import com.iuh.TourBooking.models.Coupon;
import com.iuh.TourBooking.models.Type;
import com.iuh.TourBooking.models.dto.request.CouponCreateRequest;
import com.iuh.TourBooking.models.dto.request.CouponUpdateRequest;
import com.iuh.TourBooking.models.dto.request.TypeCreateRequest;
import com.iuh.TourBooking.models.dto.request.TypeUpdateRequest;
import com.iuh.TourBooking.models.dto.response.CouponResponse;
import com.iuh.TourBooking.models.dto.response.TypeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CouponMapper {
    Coupon toCoupon(CouponCreateRequest couponCreateRequest);
    CouponResponse toCouponResponse(Coupon coupon);
    void updateCoupon(@MappingTarget Coupon coupon, CouponUpdateRequest couponUpdateRequest);
}
