package com.iuh.TourBooking.service.impl;

import com.iuh.TourBooking.enums.ErrorCode;

import com.iuh.TourBooking.exception.AppException;
import com.iuh.TourBooking.mappers.TypeMapper;
import com.iuh.TourBooking.models.Type;
import com.iuh.TourBooking.models.dto.request.TypeCreateRequest;
import com.iuh.TourBooking.models.dto.request.TypeUpdateRequest;
import com.iuh.TourBooking.models.dto.response.TypeResponse;
import com.iuh.TourBooking.models.dto.response.UserResponse;
import com.iuh.TourBooking.repository.TypeRepository;
import com.iuh.TourBooking.service.TypeService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
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
    public TypeResponse updateType(String typeId, TypeUpdateRequest typeUpdateRequest) {
        Type type = typeRepository.findByTypeId(typeId)
                .orElseThrow(() -> new RuntimeException("Type not found"));

        typeMapper.updateType(type, typeUpdateRequest);

        return typeMapper.toTypeResponse(typeRepository.save(type));
    }

    @Override
    public void deleteType(String typeId) {
        typeRepository.deleteId(typeId);
    }

    @Override
    public List<TypeResponse> getAllType() {
        return typeRepository.findAll().stream()
                .map(typeMapper::toTypeResponse)
                .toList();
    }
}
