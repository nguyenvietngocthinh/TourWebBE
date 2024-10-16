package com.iuh.TourBooking.service;

import com.iuh.TourBooking.models.dto.request.TypeCreateRequest;
import com.iuh.TourBooking.models.dto.request.TypeUpdateRequest;
import com.iuh.TourBooking.models.dto.response.TypeResponse;
import com.iuh.TourBooking.models.dto.response.UserResponse;
import org.bson.types.ObjectId;

import java.util.List;

public interface TypeService {
    public TypeResponse createType(TypeCreateRequest typeCreateRequest);
    public TypeResponse updateType(String typeId, TypeUpdateRequest typeUpdateRequest);
    public void deleteType(String typeId);
    public List<TypeResponse> getAllType();
}
