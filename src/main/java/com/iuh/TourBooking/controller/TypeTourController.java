package com.iuh.TourBooking.controller;

import com.iuh.TourBooking.models.dto.request.TypeCreateRequest;
import com.iuh.TourBooking.models.dto.request.TypeTourCreateRequest;
import com.iuh.TourBooking.models.dto.response.ApiResponse;
import com.iuh.TourBooking.models.dto.response.TypeResponse;
import com.iuh.TourBooking.models.dto.response.TypeTourResponse;
import com.iuh.TourBooking.service.TypeTourService;
import com.iuh.TourBooking.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/typetours")
public class TypeTourController {
    @Autowired
    private TypeTourService typeTourService;

    @PostMapping
    private ApiResponse<TypeTourResponse> createTypeTour(@RequestBody @Valid TypeTourCreateRequest typeTourCreateRequest) {
        return ApiResponse.<TypeTourResponse>builder()
                .result(typeTourService.createTypeTour(typeTourCreateRequest))
                .build();
    }
}
