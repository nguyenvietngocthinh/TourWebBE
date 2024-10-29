package com.iuh.TourBooking.repository;

import com.iuh.TourBooking.models.Type;
import com.iuh.TourBooking.models.TypeTour;
import com.iuh.TourBooking.models.User;
import com.iuh.TourBooking.models.dto.response.TypeTourResponse;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TypeTourRepository extends MongoRepository<TypeTour, ObjectId> {

    Optional<TypeTour> findById(ObjectId id);

    boolean existsByName(String name);

    void deleteById(ObjectId id);

    void deleteByName(String name);

    List<TypeTour> findAllByTypeId(String typeId);

}
