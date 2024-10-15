package com.iuh.TourBooking.service;

import com.iuh.TourBooking.models.dto.request.TypeCreateRequest;
import com.iuh.TourBooking.models.dto.request.TypeUpdateRequest;
import com.iuh.TourBooking.models.dto.response.TypeResponse;
import org.bson.types.ObjectId;

import java.util.List;

public interface TypeService {
    public TypeResponse createType(TypeCreateRequest typeCreateRequest);
    public TypeResponse updateType(ObjectId id, TypeUpdateRequest typeUpdateRequest);
    public void deleteType(ObjectId id);
    public List<TypeResponse> getAllUser();
}
