package com.iuh.TourBooking.repository;

import com.iuh.TourBooking.models.Type;
import com.iuh.TourBooking.models.TypeTour;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypeTourRepository extends MongoRepository<TypeTour, ObjectId> {
    boolean existsByTypeTourId(String typedTourId);

    Optional<TypeTour> findByTypeTourId(String typeTourId);

    boolean existsByName(String name);

    @Query(value = "{}", sort = "{ 'typeTourId': -1 }", fields = "{ 'typeTourId': 1 }")
    Optional<TypeTour> findTopByOrderByTypeTourIdDesc();

    void deleteByTypeTourId(String typeTourId);

}
