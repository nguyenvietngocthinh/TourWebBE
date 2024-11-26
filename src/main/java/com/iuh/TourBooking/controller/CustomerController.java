package com.iuh.TourBooking.controller;

import com.iuh.TourBooking.models.dto.request.*;
import com.iuh.TourBooking.models.dto.response.ApiResponse;
import com.iuh.TourBooking.models.dto.response.CustomerResponse;
import com.iuh.TourBooking.models.dto.response.TourResponse;
import com.iuh.TourBooking.service.CustomerService;
import com.iuh.TourBooking.service.TourService;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping
    private ApiResponse<CustomerResponse> createCustomer(@RequestBody @Valid CustomerCreateRequest customerCreateRequest) {
        return ApiResponse.<CustomerResponse>builder()
                .result(customerService.createCustomer(customerCreateRequest))
                .build();
    }

    @GetMapping
    ApiResponse<List<CustomerResponse>> getAllCustomers() {
        return ApiResponse.<List<CustomerResponse>>builder()
                .result(customerService.getAllCustomer())
                .build();
    }

    @PutMapping("/{id}")
    private CustomerResponse updateCustomer(@PathVariable ObjectId id ,@RequestBody CustomerUpdateRequest customerUpdateRequest) {
        return customerService.updateCustomerById(id, customerUpdateRequest);
    }

    @DeleteMapping("/{id}")
    private ApiResponse<String> deleteCustomer(@PathVariable ObjectId id) {
        customerService.deleteCustomerById(id);
        return ApiResponse.<String>builder().result("Customer has been deleted").build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getTotalCustomers() {
        long totalCustomers = customerService.getTotalCustomers();
        return ResponseEntity.ok(totalCustomers);
    }
}
