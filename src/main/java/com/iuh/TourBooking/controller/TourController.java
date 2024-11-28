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
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PutMapping("/{id}")
    private TourResponse updateTour(@PathVariable ObjectId id,
                                    @RequestPart(value = "tour") TourUpdateRequest tourUpdateRequest,
                                    @RequestPart(value = "image", required = false) MultipartFile image) {
        return tourService.updateTour(id, tourUpdateRequest, image);
    }

    @PutMapping("/by-tourcode/{tourCode}")
    private TourResponse updateTourByTourCode(@PathVariable String tourCode,
                                    @RequestPart(value = "tour") TourUpdateRequest tourUpdateRequest,
                                    @RequestPart(value = "image", required = false) MultipartFile image) {
        return tourService.updateTourByTourCode(tourCode, tourUpdateRequest, image);
    }


    @DeleteMapping("/{tourCode}")
    private ApiResponse<String> deleteTour(@PathVariable String tourCode) {
        tourService.deleteTour(tourCode);
        return ApiResponse.<String>builder().result("Tour has been deleted").build();
    }

    @GetMapping("/by-type/{typeId}")
    public ApiResponse<List<TourResponse>> getToursByTypeId(@PathVariable String typeId) {
        return ApiResponse.<List<TourResponse>>builder()
                .result(tourService.getToursByTypeId(typeId))
                .build();
    }

    @GetMapping("/by-typetourname/{typeTourName}")
    public ApiResponse<List<TourResponse>> getToursByTypeTourName(@PathVariable String typeTourName) {
        return ApiResponse.<List<TourResponse>>builder()
                .result(tourService.getToursByTypeTourName(typeTourName))
                .build();
    }

    @GetMapping("/by-tourcode/{tourCode}")
    public ApiResponse<TourResponse> getTourByTourCode(@PathVariable String tourCode) {
        return ApiResponse.<TourResponse>builder()
                .result(tourService.getTourByTourCode(tourCode))
                .build();
    }

    @GetMapping("/by-id/{id}")
    public ApiResponse<TourResponse> getTourById(@PathVariable ObjectId id) {
        return ApiResponse.<TourResponse>builder()
                .result(tourService.getTourById(id))
                .build();
    }

    @GetMapping("/searchTour")
    public ApiResponse<List<TourResponse>> searchTours(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "durationTour", required = false) String durationTour,
            @RequestParam(value = "locationStart", required = false) String locationStart,
            @RequestParam(value = "limit", defaultValue = "100") int limit) {

        List<TourResponse>  tours = tourService.searchTours(name,durationTour,locationStart, limit);

        return ApiResponse.<List<TourResponse>>builder()
                .result(tours)
                .build();
    }

    @GetMapping("/searchTourTypeId1")
    public ApiResponse<List<TourResponse>> searchToursTypeId1(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "durationTour", required = false) String durationTour,
            @RequestParam(value = "locationStart", required = false) String locationStart,
            @RequestParam(value = "limit", defaultValue = "100") int limit) {

        List<TourResponse>  tours = tourService.searchToursTypeId1(name,durationTour,locationStart, limit);

        return ApiResponse.<List<TourResponse>>builder()
                .result(tours)
                .build();
    }

    @GetMapping("/searchTourTypeId2")
    public ApiResponse<List<TourResponse>> searchToursTypeId2(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "durationTour", required = false) String durationTour,
            @RequestParam(value = "locationStart", required = false) String locationStart,
            @RequestParam(value = "limit", defaultValue = "100") int limit) {

        List<TourResponse>  tours = tourService.searchToursTypeId2(name,durationTour,locationStart, limit);

        return ApiResponse.<List<TourResponse>>builder()
                .result(tours)
                .build();
    }

    @GetMapping("/searchTourTypeTour")
    public ApiResponse<List<TourResponse>> searchToursTypeTour(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "durationTour", required = false) String durationTour,
            @RequestParam(value = "locationStart", required = false) String locationStart,
            @RequestParam(value = "typeTour", required = false) String typeTour,
            @RequestParam(value = "limit", defaultValue = "100") int limit) {

        List<TourResponse>  tours = tourService.searchToursTypeTour(name,durationTour,locationStart,typeTour ,limit);

        return ApiResponse.<List<TourResponse>>builder()
                .result(tours)
                .build();
    }

    @GetMapping("/searchTourAdmin")
    public ApiResponse<List<TourResponse>> searchToursAdmin(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "tourCode", required = false) String tourCode,
            @RequestParam(value = "limit", defaultValue = "2") int limit) {

        List<TourResponse>  tours = tourService.searchToursAdmin(name, tourCode, limit);

        return ApiResponse.<List<TourResponse>>builder()
                .result(tours)
                .build();
    }

    @GetMapping("/count-active")
    public ResponseEntity<Long> getTotalActiveTours() {
        long totalActiveTours = tourService.getTotalActiveTours();
        return ResponseEntity.ok(totalActiveTours);
    }
}
