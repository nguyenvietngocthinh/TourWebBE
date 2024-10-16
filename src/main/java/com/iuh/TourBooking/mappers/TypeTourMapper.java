package com.iuh.TourBooking.mappers;

import com.iuh.TourBooking.models.Type;
import com.iuh.TourBooking.models.TypeTour;
import com.iuh.TourBooking.models.dto.request.TypeCreateRequest;
import com.iuh.TourBooking.models.dto.request.TypeTourCreateRequest;
import com.iuh.TourBooking.models.dto.request.TypeTourUpdateRequest;
import com.iuh.TourBooking.models.dto.request.TypeUpdateRequest;
import com.iuh.TourBooking.models.dto.response.TypeResponse;
import com.iuh.TourBooking.models.dto.response.TypeTourResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TypeTourMapper {
    TypeTour toTypeTour(TypeTourCreateRequest typeTourCreateRequest);
    TypeTourResponse toTypeTourResponse(TypeTour typeTour);
    void updateTypeTour(@MappingTarget TypeTour typeTour, TypeTourUpdateRequest typeTourUpdateRequest);
}
