package com.iuh.TourBooking.controller;

import com.iuh.TourBooking.models.dto.request.CouponCreateRequest;
import com.iuh.TourBooking.models.dto.request.CouponUpdateRequest;
import com.iuh.TourBooking.models.dto.request.TypeCreateRequest;
import com.iuh.TourBooking.models.dto.request.TypeUpdateRequest;
import com.iuh.TourBooking.models.dto.response.ApiResponse;
import com.iuh.TourBooking.models.dto.response.CouponResponse;
import com.iuh.TourBooking.models.dto.response.TypeResponse;
import com.iuh.TourBooking.service.CouponService;
import com.iuh.TourBooking.service.TypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coupons")
public class CouponController {
    @Autowired
    private CouponService couponService;

    @PostMapping
    private ApiResponse<CouponResponse> createCoupon(@RequestBody @Valid CouponCreateRequest couponCreateRequest) {
        return ApiResponse.<CouponResponse>builder()
                .result(couponService.createCoupon(couponCreateRequest))
                .build();
    }
    @GetMapping
    ApiResponse<List<CouponResponse>> getAllCoupons() {
        return ApiResponse.<List<CouponResponse>>builder()
                .result(couponService.getAllCoupon())
                .build();
    }

    @PutMapping("/{codeCoupon}")
    private CouponResponse updateCoupon(@PathVariable String codeCoupon , @RequestBody CouponUpdateRequest couponUpdateRequest) {
        return couponService.updateCoupon(codeCoupon, couponUpdateRequest);
    }

    @DeleteMapping("{codeCoupon}")
    private ApiResponse<String> deleteCoupon(@PathVariable String codeCoupon) {
        couponService.deleteCoupon(codeCoupon);
        return ApiResponse.<String>builder().result("Coupon has been deleted").build();
    }


}
