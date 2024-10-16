package com.iuh.TourBooking.controller;

import com.iuh.TourBooking.models.Type;
import com.iuh.TourBooking.models.dto.request.TypeCreateRequest;
import com.iuh.TourBooking.models.dto.request.TypeUpdateRequest;
import com.iuh.TourBooking.models.dto.request.UserCreateRequest;
import com.iuh.TourBooking.models.dto.request.UserUpdateRequest;
import com.iuh.TourBooking.models.dto.response.ApiResponse;
import com.iuh.TourBooking.models.dto.response.TypeResponse;
import com.iuh.TourBooking.models.dto.response.UserResponse;
import com.iuh.TourBooking.service.TypeService;
import com.iuh.TourBooking.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/types")
public class TypeController {
    @Autowired
    private TypeService typeService;


    @PostMapping
    private ApiResponse<TypeResponse> createType(@RequestBody @Valid TypeCreateRequest typeCreateRequest) {
        return ApiResponse.<TypeResponse>builder()
                .result(typeService.createType(typeCreateRequest))
                .build();
    }
    @GetMapping
    ApiResponse<List<TypeResponse>> getAllTypes() {
        return ApiResponse.<List<TypeResponse>>builder()
                .result(typeService.getAllType())
                .build();
    }

    @PutMapping("/{typeId}")
    private TypeResponse updateType(@PathVariable String typeId , @RequestBody TypeUpdateRequest typeUpdateRequest) {
        return typeService.updateType(typeId, typeUpdateRequest);
    }

    @DeleteMapping("{typeId}")
    private ApiResponse<String> deleteType(@PathVariable String typeId) {
        typeService.deleteType(typeId);
        return ApiResponse.<String>builder().result("Type has been deleted").build();
    }


}
