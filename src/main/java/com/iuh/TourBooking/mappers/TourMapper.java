package com.iuh.TourBooking.mappers;

import com.iuh.TourBooking.models.Tour;
import com.iuh.TourBooking.models.TourDescription;
import com.iuh.TourBooking.models.TypeTour;
import com.iuh.TourBooking.models.dto.request.TourCreateRequest;
import com.iuh.TourBooking.models.dto.request.TourUpdateRequest;
import com.iuh.TourBooking.models.dto.request.TypeTourCreateRequest;
import com.iuh.TourBooking.models.dto.request.TypeTourUpdateRequest;
import com.iuh.TourBooking.models.dto.response.TourResponse;
import com.iuh.TourBooking.models.dto.response.TypeTourResponse;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TourMapper {

    Tour toTour(TourCreateRequest tourCreateRequest);

    TourResponse toTourResponse(Tour tour);

    void updateTour(@MappingTarget Tour tour, TourUpdateRequest tourUpdateRequest);
}

