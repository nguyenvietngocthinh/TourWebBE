package com.iuh.TourBooking.service;

import com.iuh.TourBooking.models.dto.request.CouponCreateRequest;
import com.iuh.TourBooking.models.dto.request.CouponUpdateRequest;
import com.iuh.TourBooking.models.dto.request.TypeCreateRequest;
import com.iuh.TourBooking.models.dto.request.TypeUpdateRequest;
import com.iuh.TourBooking.models.dto.response.CouponResponse;
import com.iuh.TourBooking.models.dto.response.TypeResponse;

import java.util.List;

public interface CouponService {
    public CouponResponse createCoupon( CouponCreateRequest couponCreateRequest);
    public CouponResponse updateCoupon(String codeCoupon, CouponUpdateRequest couponUpdateRequest);
    public void deleteCoupon(String codeCoupon);
    public List<CouponResponse> getAllCoupon();
    public CouponResponse getCouponByCodeCoupon(String codeCoupon);

    public List<CouponResponse> searchCoupons(String codeCoupon, int discount,String description, int limit);

    public CouponResponse updateCouponToCancel(String codeCoupon);
}
