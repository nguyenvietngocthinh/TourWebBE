package com.iuh.TourBooking.controller;

import com.iuh.TourBooking.models.Booking;
import com.iuh.TourBooking.models.dto.request.*;
import com.iuh.TourBooking.models.dto.response.*;

import com.iuh.TourBooking.service.BookingService;
import com.iuh.TourBooking.service.TourService;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping
    private ApiResponse<BookingResponse> createBooking(@RequestBody @Valid BookingCreateRequest bookingCreateRequest) {
        return ApiResponse.<BookingResponse>builder()
                .result(bookingService.createBooking(bookingCreateRequest))
                .build();
    }

    @PostMapping("/admin")
    private ApiResponse<BookingResponse> createBookingAdmin(@RequestBody @Valid BookingCreateRequest bookingCreateRequest) {
        return ApiResponse.<BookingResponse>builder()
                .result(bookingService.createBookingAdmin(bookingCreateRequest))
                .build();
    }

    @GetMapping
    ApiResponse<List<BookingResponse>> getAllBookings() {
        return ApiResponse.<List<BookingResponse>>builder()
                .result(bookingService.getAllBooking())
                .build();
    }

    @PutMapping("/{id}")
    private BookingResponse updateBooking(@PathVariable ObjectId id, @RequestBody BookingUpdateRequest bookingUpdateRequest) {
        return bookingService.updateBooking(id, bookingUpdateRequest);
    }

    @PutMapping("/bookingCode/{bookingCode}")
    private BookingResponse updateBookingByBookingCode(@PathVariable String bookingCode, @RequestBody BookingUpdateRequest bookingUpdateRequest) {
        return bookingService.updateBookingCode(bookingCode, bookingUpdateRequest);
    }

    @DeleteMapping("/{bookingCode}")
    private ApiResponse<String> deleteTour(@PathVariable String bookingCode) {
        bookingService.deleteBooking(bookingCode);
        return ApiResponse.<String>builder().result("Booking has been deleted").build();
    }

    @GetMapping("/by-bookingcode/{bookingCode}")
    public ApiResponse<BookingResponse> getBookingByBookingCode(@PathVariable String bookingCode) {
        return ApiResponse.<BookingResponse>builder()
                .result(bookingService.getBookingByBookingCode(bookingCode))
                .build();
    }

    // API để cập nhật trạng thái thành "Chờ hủy"
    @PutMapping("/pendingcancel/{bookingCode}")
    public ApiResponse<BookingResponse> updateBookingToPendingCancel(
            @PathVariable String bookingCode) {
        BookingResponse bookingResponse = bookingService.updateBookingToPendingCancel(bookingCode);
        return ApiResponse.<BookingResponse>builder()
                .result(bookingResponse)
                .build();
    }

    // API để cập nhật trạng thái thành "Đã hủy" và gửi email
    @PutMapping("/cancel/{bookingCode}")
    public ApiResponse<BookingResponse> updateBookingToCancelled(
            @PathVariable String bookingCode) {
        BookingResponse bookingResponse = bookingService.updateBookingToCancelled(bookingCode);
        return ApiResponse.<BookingResponse>builder()
                .result(bookingResponse)
                .build();
    }

    @GetMapping("/count-completed")
    public ResponseEntity<Long> getTotalCompletedBookings() {
        long totalCompletedBookings = bookingService.getTotalCompletedBookings();
        return ResponseEntity.ok(totalCompletedBookings);
    }

    // API để lấy danh sách các booking trong năm hiện tại
    @GetMapping("/revenue")
    public double getTotalRevenue(@RequestParam int year) {
        return bookingService.getTotalRevenueForYear(year);
    }

    @GetMapping("/top-tours")
    public List<TopTourResponse> getTop5Tours() {
        return bookingService.getTop5Tours();
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Booking>> getActivePaidBookingsBetweenDates(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) throws Exception {
        List<Booking> bookings = bookingService.getActivePaidBookingsBetweenDates(startDate, endDate);
        return ResponseEntity.ok(bookings);
    }
    @GetMapping("/customer-statistics")
    public ResponseEntity<List<CustomerStatistics>> getCustomerStatistics() {
        List<CustomerStatistics> customerStatistics = bookingService.getCustomerStatistics();
        return ResponseEntity.ok(customerStatistics);
    }

    @GetMapping("/search")
    public ApiResponse<List<BookingResponse>> searchBookings(
            @RequestParam(value = "bookingCode", required = false) String bookingCode,
            @RequestParam(value = "limit", defaultValue = "100") int limit) {

        List<BookingResponse>  bookings = bookingService.searchBookings(bookingCode, limit);

        return ApiResponse.<List<BookingResponse>>builder()
                .result(bookings)
                .build();
    }

    @GetMapping("/searchCancel")
    public ApiResponse<List<BookingResponse>> searchBookingsCancel(
            @RequestParam(value = "bookingCode", required = false) String bookingCode,
            @RequestParam(value = "limit", defaultValue = "100") int limit) {

        List<BookingResponse>  bookings = bookingService.searchBookingsCancel(bookingCode, limit);

        return ApiResponse.<List<BookingResponse>>builder()
                .result(bookings)
                .build();
    }

}
