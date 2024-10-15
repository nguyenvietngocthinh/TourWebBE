package com.iuh.TourBooking.service.impl;

import com.iuh.TourBooking.enums.ErrorCode;

import com.iuh.TourBooking.exception.AppException;
import com.iuh.TourBooking.mappers.TypeMapper;
import com.iuh.TourBooking.models.Type;
import com.iuh.TourBooking.models.dto.request.TypeCreateRequest;
import com.iuh.TourBooking.models.dto.request.TypeUpdateRequest;
import com.iuh.TourBooking.models.dto.response.TypeResponse;
import com.iuh.TourBooking.repository.TypeRepository;
import com.iuh.TourBooking.service.TypeService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private TypeMapper typeMapper;

    @Override
    public TypeResponse createType(TypeCreateRequest typeCreateRequest) {
        if (typeRepository.existsById(typeCreateRequest.getTypeId())) {
            throw new AppException(ErrorCode.TYPE_EXISTED);
        }

        Type type = typeMapper.toType(typeCreateRequest);
        return typeMapper.toTypeResponse(typeRepository.save(type));
    }

    @Override
    public TypeResponse updateType(ObjectId id, TypeUpdateRequest typeUpdateRequest) {
        return null;
    }

    @Override
    public void deleteType(ObjectId id) {

    }

    @Override
    public List<TypeResponse> getAllUser() {
        return typeRepository.findAll().stream()
                .map(typeMapper::toTypeResponse)
                .toList();
    }
}
