package com.iuh.TourBooking.service.impl;

import com.iuh.TourBooking.enums.ErrorCode;
import com.iuh.TourBooking.exception.AppException;
import com.iuh.TourBooking.mappers.CouponMapper;
import com.iuh.TourBooking.mappers.TypeMapper;
import com.iuh.TourBooking.models.Booking;
import com.iuh.TourBooking.models.Coupon;
import com.iuh.TourBooking.models.Type;
import com.iuh.TourBooking.models.TypeTour;
import com.iuh.TourBooking.models.dto.request.CouponCreateRequest;
import com.iuh.TourBooking.models.dto.request.CouponUpdateRequest;
import com.iuh.TourBooking.models.dto.request.TypeCreateRequest;
import com.iuh.TourBooking.models.dto.request.TypeUpdateRequest;
import com.iuh.TourBooking.models.dto.response.BookingResponse;
import com.iuh.TourBooking.models.dto.response.CouponResponse;
import com.iuh.TourBooking.models.dto.response.TypeResponse;
import com.iuh.TourBooking.models.dto.response.TypeTourResponse;
import com.iuh.TourBooking.repository.CouponRepository;
import com.iuh.TourBooking.repository.TypeRepository;
import com.iuh.TourBooking.service.CouponService;
import com.iuh.TourBooking.service.TypeService;
import com.iuh.TourBooking.utils.BookingGenerateCode;
import com.iuh.TourBooking.utils.CouponGenerateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private MongoTemplate mongoTemplate;

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public CouponResponse createCoupon(CouponCreateRequest couponCreateRequest) {
        // Sinh mã bookingCode và kiểm tra trùng lặp
        String codeCoupon;
        do {
            codeCoupon = CouponGenerateCode.generateCodeCoupon();
        } while (couponRepository.existsByCodeCoupon(codeCoupon));

        // Đặt mã bookingCode đã được tạo vào đối tượng booking
        couponCreateRequest.setCodeCoupon(codeCoupon);

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

    @Override
    public CouponResponse getCouponByCodeCoupon(String codeCoupon) {
        Coupon coupon = couponRepository.findByCodeCoupon(codeCoupon)
                .orElseThrow(() -> new AppException(ErrorCode.BOOKING_NOTFOUND));
        return couponMapper.toCouponResponse(coupon);
    }

    @Override
    public List<CouponResponse> searchCoupons(String codeCoupon, int limit) {
        // Tạo query với các điều kiện lọc
        Query query = new Query();

        // Nếu tên người dùng được cung cấp, tìm kiếm theo tên
        if (codeCoupon != null && !codeCoupon.isEmpty()) {
            query.addCriteria(Criteria.where("codeCoupon").regex(codeCoupon, "i"));  // Tìm kiếm theo tên (không phân biệt chữ hoa/thường)
        }
        // Thực hiện tìm kiếm và trả về kết quả
        List<Coupon> coupons = mongoTemplate.find(query, Coupon.class);

        // Chuyển đổi kết quả tìm kiếm thành danh sách UserResponse
        return coupons.stream()
                .map(couponMapper::toCouponResponse)
                .collect(Collectors.toList());
    }
}
