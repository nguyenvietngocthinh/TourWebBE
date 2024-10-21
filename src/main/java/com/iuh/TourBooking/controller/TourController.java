package com.iuh.TourBooking.controller;

import com.iuh.TourBooking.models.dto.request.TourCreateRequest;
import com.iuh.TourBooking.models.dto.request.TourUpdateRequest;
import com.iuh.TourBooking.models.dto.request.TypeTourCreateRequest;
import com.iuh.TourBooking.models.dto.request.TypeTourUpdateRequest;
import com.iuh.TourBooking.models.dto.response.ApiResponse;
import com.iuh.TourBooking.models.dto.response.TourResponse;
import com.iuh.TourBooking.models.dto.response.TypeTourResponse;
import com.iuh.TourBooking.service.TourService;
import com.iuh.TourBooking.service.TypeTourService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/tours")
public class TourController {
    @Autowired
    private TourService tourService;

    @PostMapping
    private ApiResponse<TourResponse> createTour( @RequestPart("tour") TourCreateRequest tourCreateRequest,
                                                  @RequestPart(value = "image", required = false) MultipartFile image) {
        return ApiResponse.<TourResponse>builder()
                .result(tourService.createTour(tourCreateRequest, image))
                .build();
    }

    @GetMapping
    ApiResponse<List<TourResponse>> getAllTours() {
        return ApiResponse.<List<TourResponse>>builder()
                .result(tourService.getAllTour())
                .build();
    }

    @PutMapping("/{tourId}")
    private TourResponse updateTour(@PathVariable String tourId , @RequestBody TourUpdateRequest tourUpdateRequest) {
        return tourService.updateTour(tourId, tourUpdateRequest);
    }

    @DeleteMapping("{tourId}")
    private ApiResponse<String> deleteTour(@PathVariable String tourId) {
        tourService.deleteTour(tourId);
        return ApiResponse.<String>builder().result("Tour has been deleted").build();
    }
}
