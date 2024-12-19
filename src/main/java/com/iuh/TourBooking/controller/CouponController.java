package com.iuh.TourBooking.controller;

import com.iuh.TourBooking.models.dto.request.CouponCreateRequest;
import com.iuh.TourBooking.models.dto.request.CouponUpdateRequest;
import com.iuh.TourBooking.models.dto.request.TypeCreateRequest;
import com.iuh.TourBooking.models.dto.request.TypeUpdateRequest;
import com.iuh.TourBooking.models.dto.response.*;
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

    @GetMapping("/by-codecoupon/{codeCoupon}")
    public ApiResponse<CouponResponse> getCouponByCodeCoupon(@PathVariable String codeCoupon) {
        return ApiResponse.<CouponResponse>builder()
                .result(couponService.getCouponByCodeCoupon(codeCoupon))
                .build();
    }

    @GetMapping("/searchCoupon")
    public ApiResponse<List<CouponResponse>> searchCoupons(
            @RequestParam(value = "codeCoupon", required = false) String codeCoupon,
            @RequestParam(value = "discount", required = false) Integer discount,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {

        List<CouponResponse> coupons = couponService.searchCoupons(
                (codeCoupon != null && !codeCoupon.trim().isEmpty()) ? codeCoupon : null,
                discount != null ? discount : 0,  // Gán giá trị mặc định nếu null
                (description != null && !description.trim().isEmpty()) ? description : null,
                limit
        );

        return ApiResponse.<List<CouponResponse>>builder()
                .result(coupons)
                .build();
    }


    @PutMapping("/couponCancel/{codeCoupon}")
    public ApiResponse<CouponResponse> updateCouponToCancel(
            @PathVariable String codeCoupon) {
        CouponResponse coupon = couponService.updateCouponToCancel(codeCoupon);
        return ApiResponse.<CouponResponse>builder()
                .result(coupon)
                .build();
    }
}
