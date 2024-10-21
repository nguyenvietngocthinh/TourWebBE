package com.iuh.TourBooking.service.impl;

import com.iuh.TourBooking.enums.ErrorCode;
import com.iuh.TourBooking.exception.AppException;
import com.iuh.TourBooking.mappers.TourMapper;
import com.iuh.TourBooking.mappers.TypeTourMapper;
import com.iuh.TourBooking.models.Tour;
import com.iuh.TourBooking.models.TypeTour;
import com.iuh.TourBooking.models.dto.request.TourCreateRequest;
import com.iuh.TourBooking.models.dto.request.TourUpdateRequest;
import com.iuh.TourBooking.models.dto.request.TypeTourCreateRequest;
import com.iuh.TourBooking.models.dto.request.TypeTourUpdateRequest;
import com.iuh.TourBooking.models.dto.response.TourResponse;
import com.iuh.TourBooking.models.dto.response.TypeTourResponse;
import com.iuh.TourBooking.repository.TourRepository;
import com.iuh.TourBooking.repository.TypeTourRepository;
import com.iuh.TourBooking.service.S3Service;
import com.iuh.TourBooking.service.TourService;
import com.iuh.TourBooking.service.TypeTourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class TourServiceImpl implements TourService {

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private TourMapper tourMapper;

    @Autowired
    private S3Service s3Service;

    @Override
    public TourResponse createTour(TourCreateRequest tourCreateRequest, MultipartFile image) {

        // Kiểm tra mã tour có tồn tại chưa
        if (tourRepository.existsByTourCode(tourCreateRequest.getTourCode())) {
            throw new AppException(ErrorCode.TOUR_EXISTED);
        }

        // Lấy tourId lớn nhất hiện có trong database
        Optional<Tour> latestTour = tourRepository.findTopByOrderByTourIdDesc();
        String nextTourId = latestTour.map(tour -> String.valueOf(Integer.parseInt(tour.getTourId()) + 1))
                .orElse("1");

        // Khởi tạo tour từ request
        Tour tour = tourMapper.toTour(tourCreateRequest);
        tour.setTourId(nextTourId);

        // Upload ảnh nếu có
        String imageUrl = null;
        if (image != null && !image.isEmpty()) {
            imageUrl = s3Service.uploadFile(image);  // Không cần try-catch nếu uploadFile không ném IOException
        }
        tour.setImage(imageUrl);

        // Lưu tour vào DB và trả về response
        return tourMapper.toTourResponse(tourRepository.save(tour));
    }



    @Override
    public TourResponse updateTour(String tourId, TourUpdateRequest tourUpdateRequest) {
        Tour tour = tourRepository.findByTourId(tourId)
                .orElseThrow(() -> new RuntimeException("Tour not found"));

        tourMapper.updateTour(tour, tourUpdateRequest);

        return tourMapper.toTourResponse(tourRepository.save(tour));
    }

    @Override
    public void deleteTour(String tourId) {
        tourRepository.deleteByTourId(tourId);
    }

    @Override
    public List<TourResponse> getAllTour() {
        return tourRepository.findAll().stream()
                .map(tourMapper::toTourResponse)
                .toList();
    }
}
