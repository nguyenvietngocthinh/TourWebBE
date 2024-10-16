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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

        // Lấy typeTourId lớn nhất hiện có trong database
        Optional<TypeTour> latestTypeTour = typeTourRepository.findTopByOrderByTypeTourIdDesc();
        String nextTypeTourId;

        // Gán typeTourId mới dựa trên kết quả tìm kiếm
        if (latestTypeTour.isPresent()) {
            int maxId = Integer.parseInt(latestTypeTour.get().getTypeTourId());
            nextTypeTourId = String.valueOf(maxId + 1);  // Tăng thêm 1
        } else {
            nextTypeTourId = "1";  // Nếu chưa có dữ liệu thì bắt đầu từ 1
        }

        // Tạo TypeTour với typeTourId mới
        TypeTour typeTour = typeTourMapper.toTypeTour(typeTourCreateRequest);
        typeTour.setTypeTourId(nextTypeTourId);  // Gán typeTourId mới

        // Lưu vào database và trả về response
        return typeTourMapper.toTypeTourResponse(typeTourRepository.save(typeTour));
    }

    @Override
    public TypeTourResponse updateTypeTour(String typeTourId, TypeTourUpdateRequest typeTourUpdateRequest) {
        TypeTour typeTour = typeTourRepository.findByTypeTourId(typeTourId)
                .orElseThrow(() -> new RuntimeException("Type not found"));

        typeTourMapper.updateTypeTour(typeTour, typeTourUpdateRequest);

        return typeTourMapper.toTypeTourResponse(typeTourRepository.save(typeTour));
    }

    @Override
    public void deleteTypeTour(String typeTourId) {
        typeTourRepository.deleteByTypeTourId(typeTourId);
    }

    @Override
    public List<TypeTourResponse> getAllTypeTour() {
        return typeTourRepository.findAll().stream()
                .map(typeTourMapper::toTypeTourResponse)
                .toList();
    }
}
