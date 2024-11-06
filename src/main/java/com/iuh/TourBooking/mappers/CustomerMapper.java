package com.iuh.TourBooking.mappers;

import com.iuh.TourBooking.models.Customer;
import com.iuh.TourBooking.models.User;
import com.iuh.TourBooking.models.dto.request.CustomerCreateRequest;
import com.iuh.TourBooking.models.dto.request.CustomerUpdateRequest;
import com.iuh.TourBooking.models.dto.request.UserCreateRequest;
import com.iuh.TourBooking.models.dto.request.UserUpdateRequest;
import com.iuh.TourBooking.models.dto.response.CustomerResponse;
import com.iuh.TourBooking.models.dto.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer toCustomer(CustomerCreateRequest customerCreateRequest);
    CustomerResponse toCustomerResponse(Customer customer);
    void updateCustomer(@MappingTarget Customer customer, CustomerUpdateRequest customerUpdateRequest);
}
