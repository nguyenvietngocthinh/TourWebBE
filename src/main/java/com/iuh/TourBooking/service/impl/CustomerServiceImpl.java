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
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private MongoTemplate mongoTemplate;

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

    @Override
    public List<CustomerResponse> searchCustomers(String customerName, String customerEmail, String customerPhoneNumber, int limit) {
        // Tạo query với các điều kiện lọc
        Query query = new Query();

        // Nếu tên người dùng được cung cấp, tìm kiếm theo tên
        if (customerName != null && !customerName.isEmpty()) {
            query.addCriteria(Criteria.where("customerName").regex(customerName, "i"));  // Tìm kiếm theo tên (không phân biệt chữ hoa/thường)
        }

        // Nếu email được cung cấp, tìm kiếm theo email
        if (customerEmail != null && !customerEmail.isEmpty()) {
            query.addCriteria(Criteria.where("customerEmail").regex(customerEmail, "i"));  // Tìm kiếm theo email
        }

        // Nếu số điện thoại được cung cấp, tìm kiếm theo số điện thoại
        if (customerPhoneNumber != null && !customerPhoneNumber.isEmpty()) {
            query.addCriteria(Criteria.where("customerPhoneNumber").regex(customerPhoneNumber, "i"));  // Tìm kiếm theo số điện thoại
        }


        // Giới hạn số lượng kết quả trả về
        query.limit(limit > 0 ? limit : 3);  // Mặc định trả về 3 kết quả nếu không có tham số giới hạn

        // Thực hiện tìm kiếm và trả về kết quả
        List<Customer> customers = mongoTemplate.find(query, Customer.class);

        // Chuyển đổi kết quả tìm kiếm thành danh sách UserResponse
        return customers.stream()
                .map(customerMapper::toCustomerResponse)
                .collect(Collectors.toList());
    }

    @Override
    public long getTotalCustomers() {
        return customerRepository.count();
    }

}
