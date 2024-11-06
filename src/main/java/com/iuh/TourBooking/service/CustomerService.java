 package com.iuh.TourBooking.service;

import com.iuh.TourBooking.models.User;
import com.iuh.TourBooking.models.dto.request.CustomerCreateRequest;
import com.iuh.TourBooking.models.dto.request.CustomerUpdateRequest;
import com.iuh.TourBooking.models.dto.request.UserCreateRequest;
import com.iuh.TourBooking.models.dto.request.UserUpdateRequest;
import com.iuh.TourBooking.models.dto.response.CustomerResponse;
import com.iuh.TourBooking.models.dto.response.UserResponse;
import org.bson.types.ObjectId;

import java.util.List;

public interface CustomerService {
    public CustomerResponse createCustomer(CustomerCreateRequest customerCreateRequest);

    public CustomerResponse updateCustomerById(ObjectId id, CustomerUpdateRequest customerUpdateRequest);

    public void deleteCustomerById(ObjectId userId);

    public List<CustomerResponse> getAllCustomer();

}
