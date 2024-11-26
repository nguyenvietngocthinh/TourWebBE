package com.iuh.TourBooking.repository;

import com.iuh.TourBooking.models.Booking;
import com.iuh.TourBooking.models.Tour;
import com.iuh.TourBooking.models.dto.response.BookingResponse;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends MongoRepository<Booking, ObjectId> {

    boolean existsByBookingCode(String bookingCode);

    Optional<Booking> findById(ObjectId Id);

    Optional<Booking> findByBookingCode(String bookingCode);

    void deleteByBookingCode(String bookingCode);

    long countByActiveBooking(String activeBooking);

    // Sử dụng Aggregation để tính tổng tiền theo điều kiện
    // Truy vấn để lấy danh sách các booking có bookingDate trong năm hiện tại@Query("{'payBooking': true, 'activeBooking': 'Hoàn thành', 'bookingDate': {$regex: '^2024'}}")
    //List<Booking> findBookingsInYear(String year);
}
