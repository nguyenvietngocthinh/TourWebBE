package com.iuh.TourBooking.repository;

import com.iuh.TourBooking.models.Customer;
import com.iuh.TourBooking.models.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, ObjectId> {
    Optional<User> findByCustomerPhoneNumber(String customerPhoneNumber);
    Optional<User> findByCustomerEmail(String customerEmail);
    boolean existsByCustomerEmail(String customerEmail);
    void deleteByCustomerEmail(String customerEmail);

    long count();
}
