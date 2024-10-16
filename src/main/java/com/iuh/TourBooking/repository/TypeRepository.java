package com.iuh.TourBooking.repository;

import com.iuh.TourBooking.models.Type;
import com.iuh.TourBooking.models.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypeRepository extends MongoRepository<Type, ObjectId> {
    boolean existsByTypeId(String typedId);
    Optional<Type> findByTypeId(String typeId);
    boolean existsByName(String name);
    void deleteByTypeId(String typeId);
}
