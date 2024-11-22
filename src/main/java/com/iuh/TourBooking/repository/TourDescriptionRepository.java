package com.iuh.TourBooking.repository;

import com.iuh.TourBooking.models.Tour;
import com.iuh.TourBooking.models.TourDescription;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TourDescriptionRepository extends MongoRepository<TourDescription, ObjectId> {

}
