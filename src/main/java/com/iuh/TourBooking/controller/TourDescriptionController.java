package com.iuh.TourBooking.controller;

import com.iuh.TourBooking.models.dto.request.TourCreateRequest;
import com.iuh.TourBooking.models.dto.request.TourDescriptionCreateRequest;
import com.iuh.TourBooking.models.dto.request.TourDescriptionUpdateRequest;
import com.iuh.TourBooking.models.dto.request.TourUpdateRequest;
import com.iuh.TourBooking.models.dto.response.ApiResponse;
import com.iuh.TourBooking.models.dto.response.TourDescriptionResponse;
import com.iuh.TourBooking.models.dto.response.TourResponse;
import com.iuh.TourBooking.service.TourDescriptionService;
import com.iuh.TourBooking.service.TourService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/tours-description")
public class TourDescriptionController {
    @Autowired
    private TourDescriptionService tourDescriptionService;

    @PostMapping
    private ApiResponse<TourDescriptionResponse> createTourDescription(@RequestPart("tourDescription") TourDescriptionCreateRequest tourDescriptionCreateRequest,
                                                            @RequestPart(value = "image", required = false) MultipartFile image) {
        return ApiResponse.<TourDescriptionResponse>builder()
                .result(tourDescriptionService.createTourDescription(tourDescriptionCreateRequest, image))
                .build();
    }

    @GetMapping
    ApiResponse<List<TourDescriptionResponse>> getAllTourDescriptions() {
        return ApiResponse.<List<TourDescriptionResponse>>builder()
                .result(tourDescriptionService.getAllTourDescription())
                .build();
    }

    @PutMapping("/{id}")
    private TourDescriptionResponse updateTourDescription(@PathVariable ObjectId id,
                                    @RequestPart(value = "tourDescription") TourDescriptionUpdateRequest tourDescriptionUpdateRequest,
                                    @RequestPart(value = "image", required = false) MultipartFile image) {
        return tourDescriptionService.updateTourDescription(id, tourDescriptionUpdateRequest, image);
    }


    @DeleteMapping("/{id}")
    private ApiResponse<String> deleteTourDescription(@PathVariable ObjectId id) {
        tourDescriptionService.deleteTourDescription(id);
        return ApiResponse.<String>builder().result("Tour has been deleted").build();
    }

}
