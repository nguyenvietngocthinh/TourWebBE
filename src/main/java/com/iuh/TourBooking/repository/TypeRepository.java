package com.iuh.TourBooking.repository;

import com.iuh.TourBooking.models.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TypeRepository extends MongoRepository<User, ObjectId> {
}
