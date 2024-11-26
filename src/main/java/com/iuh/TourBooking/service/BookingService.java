package com.iuh.TourBooking.service;

import com.iuh.TourBooking.models.Booking;
import com.iuh.TourBooking.models.dto.request.BookingCancelRequest;
import com.iuh.TourBooking.models.dto.request.BookingCreateRequest;
import com.iuh.TourBooking.models.dto.request.BookingPendingRequest;
import com.iuh.TourBooking.models.dto.request.BookingUpdateRequest;
import com.iuh.TourBooking.models.dto.response.BookingResponse;
import com.iuh.TourBooking.models.dto.response.TourResponse;
import org.bson.types.ObjectId;

import java.util.List;


public interface BookingService {
    public BookingResponse createBooking(BookingCreateRequest bookingCreateRequest);

    public BookingResponse createBookingAdmin(BookingCreateRequest bookingCreateRequest);

    public BookingResponse updateBooking(ObjectId id, BookingUpdateRequest bookingUpdateRequest);
    public void deleteBooking(String bookingCode);
    public List<BookingResponse> getAllBooking();

    public BookingResponse getBookingByBookingCode(String bookingCode);

    public BookingResponse updateBookingCode(String bookingCode, BookingUpdateRequest bookingUpdateRequest);

    public BookingResponse updateBookingToPendingCancel(String bookingCode);

    public BookingResponse updateBookingToCancelled(String bookingCode);
    public void updateBookingPaymentStatus(String bookingCode, boolean payBooking);

    public void sendBookingConfirmationEmail(String bookingCode);

    public long getTotalCompletedBookings();

    public double getTotalRevenueForYear(int year);
}
