package com.iuh.TourBooking.repository;

import com.iuh.TourBooking.models.Tour;
import com.iuh.TourBooking.models.TypeTour;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TourRepository extends MongoRepository<Tour, ObjectId> {

    boolean existsByTourCode(String tourCode);

    Optional<Tour> findById(ObjectId Id);

    Optional<Tour> findByTourCode(String tourCode);

    void deleteByTourCode(String tourCode);

    List<Tour> findAllByTypeId(String typeId);


    List<Tour> findAllByTypeTourName(String typeTourName);
}
