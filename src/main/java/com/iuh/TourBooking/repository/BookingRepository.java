package com.iuh.TourBooking.repository;

import com.iuh.TourBooking.models.Booking;
import com.iuh.TourBooking.models.Tour;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingRepository extends MongoRepository<Booking, ObjectId> {

    boolean existsByBookingCode(String bookingCode);

    Optional<Booking> findById(ObjectId Id);

    Optional<Booking> findByBookingCode(String bookingCode);

    void deleteByBookingCode(String bookingCode);
}
