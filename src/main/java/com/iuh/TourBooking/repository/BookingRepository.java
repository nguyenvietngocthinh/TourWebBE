package com.iuh.TourBooking.repository;

import com.iuh.TourBooking.models.Booking;
import com.iuh.TourBooking.models.Tour;
import com.iuh.TourBooking.models.dto.response.BookingResponse;
import com.iuh.TourBooking.models.dto.response.TopTourResponse;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
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

    @Aggregation(pipeline = {
            "{ $group: { _id: '$tourCode', bookingCount: { $sum: 1 } } }",  // Nhóm theo tourCode và đếm số lượng booking
            "{ $sort: { bookingCount: -1 } }", // Sắp xếp theo số lượng booking giảm dần
            "{ $limit: 5 }"  // Giới hạn lấy 5 tourCode có nhiều booking nhất
    })
    List<TopTourResponse> getTop5Tours();

    List<Booking> findByBookingDateBetweenAndPayBookingIsTrueAndActiveBooking(
            String startDate, String endDate, String activeBooking);

    // Sử dụng @Query để viết truy vấn MongoDB tùy chỉnh
    // Phương thức tìm kiếm các booking có payBooking = true và activeBooking = "Hoạt động"
    List<Booking> findByPayBookingTrueAndActiveBooking(String activeBooking);

}
