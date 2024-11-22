package com.iuh.TourBooking.controller;

import com.iuh.TourBooking.models.dto.request.*;
import com.iuh.TourBooking.models.dto.response.ApiResponse;
import com.iuh.TourBooking.models.dto.response.BookingResponse;
import com.iuh.TourBooking.models.dto.response.TourResponse;

import com.iuh.TourBooking.service.BookingService;
import com.iuh.TourBooking.service.TourService;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
}
