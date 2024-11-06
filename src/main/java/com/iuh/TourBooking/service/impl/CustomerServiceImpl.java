package com.iuh.TourBooking.service.impl;

import com.iuh.TourBooking.enums.ErrorCode;
import com.iuh.TourBooking.enums.Role;
import com.iuh.TourBooking.exception.AppException;
import com.iuh.TourBooking.mappers.CustomerMapper;
import com.iuh.TourBooking.mappers.UserMapper;
import com.iuh.TourBooking.models.Customer;
import com.iuh.TourBooking.models.User;
import com.iuh.TourBooking.models.dto.request.CustomerCreateRequest;
import com.iuh.TourBooking.models.dto.request.CustomerUpdateRequest;
import com.iuh.TourBooking.models.dto.request.UserCreateRequest;
import com.iuh.TourBooking.models.dto.request.UserUpdateRequest;
import com.iuh.TourBooking.models.dto.response.CustomerResponse;
import com.iuh.TourBooking.models.dto.response.UserResponse;
import com.iuh.TourBooking.repository.CustomerRepository;
import com.iuh.TourBooking.repository.UserRepository;
import com.iuh.TourBooking.service.CustomerService;
import com.iuh.TourBooking.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;


    @Override
    public CustomerResponse createCustomer(CustomerCreateRequest customerCreateRequest) {
        if (customerRepository.existsByCustomerEmail((customerCreateRequest.getCustomerEmail()))) {
            throw new AppException(ErrorCode.CUSTOMER_EXISTED);
        }

        Customer customer = customerMapper.toCustomer(customerCreateRequest);

        return customerMapper.toCustomerResponse(customerRepository.save(customer));
    }

    @Override
    public CustomerResponse updateCustomerById(ObjectId id, CustomerUpdateRequest customerUpdateRequest) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        customerMapper.updateCustomer(customer, customerUpdateRequest);

        return customerMapper.toCustomerResponse(customerRepository.save(customer));
    }

    @Override
    public void deleteCustomerById(ObjectId id) {
        customerRepository.deleteById(id);
    }

    @Override
    public List<CustomerResponse> getAllCustomer() {
        log.info("In method get Users");
        return customerRepository.findAll().stream()
                .map(customerMapper::toCustomerResponse)
                .toList();
    }
}
