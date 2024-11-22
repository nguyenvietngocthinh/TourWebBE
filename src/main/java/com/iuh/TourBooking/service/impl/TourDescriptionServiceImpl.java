package com.iuh.TourBooking.service.impl;

import com.iuh.TourBooking.enums.ErrorCode;
import com.iuh.TourBooking.exception.AppException;
import com.iuh.TourBooking.mappers.TourDescriptionMapper;
import com.iuh.TourBooking.mappers.TourMapper;
import com.iuh.TourBooking.models.Tour;
import com.iuh.TourBooking.models.TourDescription;
import com.iuh.TourBooking.models.dto.request.TourCreateRequest;
import com.iuh.TourBooking.models.dto.request.TourDescriptionCreateRequest;
import com.iuh.TourBooking.models.dto.request.TourDescriptionUpdateRequest;
import com.iuh.TourBooking.models.dto.request.TourUpdateRequest;
import com.iuh.TourBooking.models.dto.response.TourDescriptionResponse;
import com.iuh.TourBooking.models.dto.response.TourResponse;
import com.iuh.TourBooking.repository.TourDescriptionRepository;
import com.iuh.TourBooking.repository.TourRepository;
import com.iuh.TourBooking.service.S3Service;
import com.iuh.TourBooking.service.TourDescriptionService;
import com.iuh.TourBooking.service.TourService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class TourDescriptionServiceImpl implements TourDescriptionService {

    @Autowired
    private TourDescriptionRepository tourDescriptionRepository;

    @Autowired
    private TourDescriptionMapper tourDescriptionMapper;

    @Autowired
    private S3Service s3Service;

    @Override
    public TourDescriptionResponse createTourDescription(TourDescriptionCreateRequest tourDescriptionCreateRequest, MultipartFile image) {

        // Khởi tạo tour từ request
        TourDescription tourDescription = tourDescriptionMapper.toTourDescription(tourDescriptionCreateRequest);

        // Upload ảnh nếu có
        String imageUrl = null;
        if (image != null && !image.isEmpty()) {
            imageUrl = s3Service.uploadFile(image);  // Không cần try-catch nếu uploadFile không ném IOException
        }
        tourDescription.setImage(imageUrl);

        // Lưu tour vào DB và trả về response
        return tourDescriptionMapper.toTourDescriptionResponse(tourDescriptionRepository.save(tourDescription));
    }


    @Override
    public TourDescriptionResponse updateTourDescription(ObjectId id, TourDescriptionUpdateRequest tourDescriptionUpdateRequest, MultipartFile image) {
        TourDescription tourDescription = tourDescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tour description not found"));

        // Cập nhật các trường của tour từ yêu cầu cập nhật
        tourDescriptionMapper.updateTourDescription(tourDescription, tourDescriptionUpdateRequest);

        // Nếu có hình ảnh mới được cung cấp, tải lên và cập nhật URL hình ảnh của tour
        if (image != null && !image.isEmpty()) {
            String imageUrl = s3Service.uploadFile(image); // Tải lên hình ảnh mới
            tourDescription.setImage(imageUrl); // Cập nhật URL hình ảnh mới
        }

        // Lưu tour đã cập nhật vào repository và trả về phản hồi
        return tourDescriptionMapper.toTourDescriptionResponse(tourDescriptionRepository.save(tourDescription));
    }


    @Override
    public void deleteTourDescription(ObjectId id) {
        tourDescriptionRepository.deleteById(id);
    }

    @Override
    public List<TourDescriptionResponse> getAllTourDescription() {
        return tourDescriptionRepository.findAll().stream()
                .map(tourDescriptionMapper::toTourDescriptionResponse)
                .toList();
    }
}