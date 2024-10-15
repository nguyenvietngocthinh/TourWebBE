package com.iuh.TourBooking.repository;

import com.iuh.TourBooking.models.Type;
import com.iuh.TourBooking.models.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends MongoRepository<Type, ObjectId> {
    boolean existsById(String id);
}
