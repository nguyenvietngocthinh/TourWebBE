package com.iuh.TourBooking.repository;

import com.iuh.TourBooking.models.Tour;
import com.iuh.TourBooking.models.TypeTour;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TourRepository extends MongoRepository<Tour, ObjectId> {
    boolean existsByTourId(String tourId);
    boolean existsByTourCode(String tourCode);

    Optional<Tour> findByTourId(String tourId);

    Optional<Tour> findByTourCode(String tourCode);


    @Query(value = "{}", sort = "{ 'tourId': -1 }", fields = "{ 'tourId': 1 }")
    Optional<Tour> findTopByOrderByTourIdDesc();

    void deleteByTourId(String tourId);
}
