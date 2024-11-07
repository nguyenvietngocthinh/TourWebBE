package com.iuh.TourBooking.controller;

import com.iuh.TourBooking.models.dto.request.TypeCreateRequest;
import com.iuh.TourBooking.models.dto.request.TypeTourCreateRequest;
import com.iuh.TourBooking.models.dto.request.TypeTourUpdateRequest;
import com.iuh.TourBooking.models.dto.request.TypeUpdateRequest;
import com.iuh.TourBooking.models.dto.response.ApiResponse;
import com.iuh.TourBooking.models.dto.response.TypeResponse;
import com.iuh.TourBooking.models.dto.response.TypeTourResponse;
import com.iuh.TourBooking.models.dto.response.UserResponse;
import com.iuh.TourBooking.service.TypeTourService;
import com.iuh.TourBooking.service.UserService;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
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


    @PutMapping("/{id}")
    private TypeTourResponse updateTypeTour(@PathVariable ObjectId id , @RequestBody TypeTourUpdateRequest typeTourUpdateRequest) {
        return typeTourService.updateTypeTour(id, typeTourUpdateRequest);
    }

    @DeleteMapping("/{name}")
    private ApiResponse<String> deleteTypeTour(@PathVariable String name) {
        typeTourService.deleteTypeTour(name);
        return ApiResponse.<String>builder().result("Type Tour has been deleted").build();
    }

    @GetMapping("/by-type/{typeId}")
    public ApiResponse<List<TypeTourResponse>> getTypeToursByTypeId(@PathVariable String typeId) {
        return ApiResponse.<List<TypeTourResponse>>builder()
                .result(typeTourService.getTypeToursByTypeId(typeId))
                .build();
    }

    @GetMapping("/by-typename/{name}")
    public ApiResponse<List<TypeTourResponse>> getTypeToursByName(@PathVariable String name) {
        return ApiResponse.<List<TypeTourResponse>>builder()
                .result(typeTourService.getTypeToursByName(name))
                .build();
    }

    @GetMapping("/search")
    public ApiResponse<List<TypeTourResponse>> searchTypeTours(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "limit", defaultValue = "1") int limit) {

        List<TypeTourResponse>  typeTours = typeTourService.searchTypeTours(name, limit);

        return ApiResponse.<List<TypeTourResponse>>builder()
                .result(typeTours)
                .build();
    }

}
