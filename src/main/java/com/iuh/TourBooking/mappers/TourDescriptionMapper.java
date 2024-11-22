package com.iuh.TourBooking.mappers;

import com.iuh.TourBooking.models.Tour;
import com.iuh.TourBooking.models.TourDescription;
import com.iuh.TourBooking.models.dto.request.TourCreateRequest;
import com.iuh.TourBooking.models.dto.request.TourDescriptionCreateRequest;
import com.iuh.TourBooking.models.dto.request.TourDescriptionUpdateRequest;
import com.iuh.TourBooking.models.dto.request.TourUpdateRequest;
import com.iuh.TourBooking.models.dto.response.TourDescriptionResponse;
import com.iuh.TourBooking.models.dto.response.TourResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TourDescriptionMapper {
    TourDescription toTourDescription(TourDescriptionCreateRequest tourDescriptionCreateRequest);
    TourDescriptionResponse toTourDescriptionResponse(TourDescription tourDescription);
    void updateTourDescription(@MappingTarget TourDescription tourDescription, TourDescriptionUpdateRequest tourDescriptionUpdateRequest);
}
