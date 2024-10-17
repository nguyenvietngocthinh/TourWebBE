package com.iuh.TourBooking.controller;

import com.iuh.TourBooking.models.dto.request.TypeCreateRequest;
import com.iuh.TourBooking.models.dto.request.TypeTourCreateRequest;
import com.iuh.TourBooking.models.dto.request.TypeTourUpdateRequest;
import com.iuh.TourBooking.models.dto.request.TypeUpdateRequest;
import com.iuh.TourBooking.models.dto.response.ApiResponse;
import com.iuh.TourBooking.models.dto.response.TypeResponse;
import com.iuh.TourBooking.models.dto.response.TypeTourResponse;
import com.iuh.TourBooking.service.TypeTourService;
import com.iuh.TourBooking.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    ApiResponse<List<TypeTourResponse>> getAllTypeTours() {
        return ApiResponse.<List<TypeTourResponse>>builder()
                .result(typeTourService.getAllTypeTour())
                .build();
    }

    @PutMapping("/{typeTourId}")
    private TypeTourResponse updateTypeTour(@PathVariable String typeTourId , @RequestBody TypeTourUpdateRequest typeTourUpdateRequest) {
        return typeTourService.updateTypeTour(typeTourId, typeTourUpdateRequest);
    }

    @DeleteMapping("{typeTourId}")
    private ApiResponse<String> deleteTypeTour(@PathVariable String typeTourId) {
        typeTourService.deleteTypeTour(typeTourId);
        return ApiResponse.<String>builder().result("Type Tour has been deleted").build();
    }
}
