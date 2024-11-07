package com.iuh.TourBooking.service.impl;

import com.iuh.TourBooking.enums.ErrorCode;
import com.iuh.TourBooking.exception.AppException;
import com.iuh.TourBooking.mappers.CouponMapper;
import com.iuh.TourBooking.mappers.TypeMapper;
import com.iuh.TourBooking.models.Coupon;
import com.iuh.TourBooking.models.Type;
import com.iuh.TourBooking.models.dto.request.CouponCreateRequest;
import com.iuh.TourBooking.models.dto.request.CouponUpdateRequest;
import com.iuh.TourBooking.models.dto.request.TypeCreateRequest;
import com.iuh.TourBooking.models.dto.request.TypeUpdateRequest;
import com.iuh.TourBooking.models.dto.response.CouponResponse;
import com.iuh.TourBooking.models.dto.response.TypeResponse;
import com.iuh.TourBooking.repository.CouponRepository;
import com.iuh.TourBooking.repository.TypeRepository;
import com.iuh.TourBooking.service.CouponService;
import com.iuh.TourBooking.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CouponMapper couponMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public CouponResponse createCoupon(CouponCreateRequest couponCreateRequest) {
        if (couponRepository.existsByCodeCoupon(couponCreateRequest.getCodeCoupon())) {
            throw new AppException(ErrorCode.TYPE_EXISTED);
        }

        Coupon coupon = couponMapper.toCoupon(couponCreateRequest);

        coupon.setActiveCoupon(true);
        return couponMapper.toCouponResponse(couponRepository.save(coupon));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public CouponResponse updateCoupon(String codeCoupon, CouponUpdateRequest couponUpdateRequest) {
        Coupon coupon = couponRepository.findByCodeCoupon(codeCoupon)
                .orElseThrow(() -> new RuntimeException("Coupon not found"));

        couponMapper.updateCoupon(coupon, couponUpdateRequest);

        return couponMapper.toCouponResponse(couponRepository.save(coupon));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public void deleteCoupon(String codeCoupon) {
        couponRepository.deleteByCodeCoupon(codeCoupon);
    }


    @Override
    public List<CouponResponse> getAllCoupon() {
        return couponRepository.findAll().stream()
                .map(couponMapper::toCouponResponse)
                .toList();
    }
}
