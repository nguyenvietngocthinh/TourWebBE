package com.iuh.TourBooking.service.impl;

import com.iuh.TourBooking.enums.ErrorCode;
import com.iuh.TourBooking.exception.AppException;
import com.iuh.TourBooking.mappers.TypeTourMapper;
import com.iuh.TourBooking.models.Type;
import com.iuh.TourBooking.models.TypeTour;
import com.iuh.TourBooking.models.dto.request.TypeTourCreateRequest;
import com.iuh.TourBooking.models.dto.request.TypeTourUpdateRequest;
import com.iuh.TourBooking.models.dto.response.TypeTourResponse;
import com.iuh.TourBooking.repository.TypeTourRepository;
import com.iuh.TourBooking.service.TypeTourService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TypeTourServiceImpl implements TypeTourService {

    @Autowired
    private TypeTourRepository typeTourRepository;

    @Autowired
    private TypeTourMapper typeTourMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public TypeTourResponse createTypeTour(TypeTourCreateRequest typeTourCreateRequest) {

        if (typeTourRepository.existsByName(typeTourCreateRequest.getName())) {
            throw new AppException(ErrorCode.TYPETOUR_EXISTED);
        }

        // Tạo TypeTour với typeTourId mới
        TypeTour typeTour = typeTourMapper.toTypeTour(typeTourCreateRequest);


        // Lưu vào database và trả về response
        return typeTourMapper.toTypeTourResponse(typeTourRepository.save(typeTour));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public TypeTourResponse updateTypeTour(ObjectId id, TypeTourUpdateRequest typeTourUpdateRequest) {
        TypeTour typeTour = typeTourRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Type not found"));

        typeTourMapper.updateTypeTour(typeTour, typeTourUpdateRequest);

        return typeTourMapper.toTypeTourResponse(typeTourRepository.save(typeTour));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public void deleteTypeTour(String name) {
        typeTourRepository.deleteByName(name);
    }

    @Override
    public List<TypeTourResponse> getAllTypeTour() {
        return typeTourRepository.findAll().stream()
                .map(typeTourMapper::toTypeTourResponse)
                .toList();
    }

    @Override
    public List<TypeTourResponse> getTypeToursByTypeId(String typeId) {
        List<TypeTour> typeTours = typeTourRepository.findAllByTypeId(typeId);
        return typeTours.stream()
                .map(typeTourMapper::toTypeTourResponse)
                .toList();
    }

    @Override
    public List<TypeTourResponse> getTypeToursByName(String name) {
        List<TypeTour> typeTours = typeTourRepository.findAllByName(name);
        return typeTours.stream()
                .map(typeTourMapper::toTypeTourResponse)
                .toList();
    }


}
