package com.iuh.TourBooking.service;

import com.iuh.TourBooking.models.dto.request.TypeCreateRequest;
import com.iuh.TourBooking.models.dto.request.TypeTourCreateRequest;
import com.iuh.TourBooking.models.dto.request.TypeTourUpdateRequest;
import com.iuh.TourBooking.models.dto.request.TypeUpdateRequest;
import com.iuh.TourBooking.models.dto.response.TypeResponse;
import com.iuh.TourBooking.models.dto.response.TypeTourResponse;
import org.bson.types.ObjectId;

import java.util.List;

public interface TypeTourService {
    public TypeTourResponse createTypeTour(TypeTourCreateRequest typeTourCreateRequest);

    public TypeTourResponse updateTypeTour(ObjectId id, TypeTourUpdateRequest typeTourUpdateRequest);
    public void deleteTypeTour(String name);
    public List<TypeTourResponse> getAllTypeTour();

    List<TypeTourResponse> getTypeToursByTypeId(String typeId);

    List<TypeTourResponse> getTypeToursByName(String name);



}
