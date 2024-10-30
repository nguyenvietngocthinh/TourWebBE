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

    @DeleteMapping("/{bookingCode}")
    private ApiResponse<String> deleteTour(@PathVariable String bookingCode) {
        bookingService.deleteBooking(bookingCode);
        return ApiResponse.<String>builder().result("Booking has been deleted").build();
    }
}
