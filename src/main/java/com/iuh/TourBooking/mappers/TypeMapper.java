package com.iuh.TourBooking.mappers;

import com.iuh.TourBooking.models.Type;
import com.iuh.TourBooking.models.User;
import com.iuh.TourBooking.models.dto.request.TypeCreateRequest;
import com.iuh.TourBooking.models.dto.request.TypeUpdateRequest;
import com.iuh.TourBooking.models.dto.request.UserCreateRequest;
import com.iuh.TourBooking.models.dto.request.UserUpdateRequest;
import com.iuh.TourBooking.models.dto.response.TypeResponse;
import com.iuh.TourBooking.models.dto.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TypeMapper {
    Type toType(TypeCreateRequest typeCreateRequest);
    TypeResponse toTypeResponse(Type type);
    void updateUser(@MappingTarget Type type, TypeUpdateRequest typeUpdateRequest);
}
