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
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TourServiceImpl implements TourService {

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private TourMapper tourMapper;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public TourResponse createTour(TourCreateRequest tourCreateRequest, MultipartFile image) {

        // Kiểm tra mã tour có tồn tại chưa
        if (tourRepository.existsByTourCode(tourCreateRequest.getTourCode())) {
                throw new AppException(ErrorCode.TOUR_EXISTED);
        }

        // Khởi tạo tour từ request
        Tour tour = tourMapper.toTour(tourCreateRequest);

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
    public TourResponse updateTour(ObjectId id, TourUpdateRequest tourUpdateRequest, MultipartFile image) {
        Tour tour = tourRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tour not found"));

        // Cập nhật các trường của tour từ yêu cầu cập nhật
        tourMapper.updateTour(tour, tourUpdateRequest);

        // Nếu có hình ảnh mới được cung cấp, tải lên và cập nhật URL hình ảnh của tour
        if (image != null && !image.isEmpty()) {
            String imageUrl = s3Service.uploadFile(image); // Tải lên hình ảnh mới
            tour.setImage(imageUrl); // Cập nhật URL hình ảnh mới
        }

        // Lưu tour đã cập nhật vào repository và trả về phản hồi
        return tourMapper.toTourResponse(tourRepository.save(tour));
    }

    @Override
    public TourResponse updateTourByTourCode(String tourCode, TourUpdateRequest tourUpdateRequest, MultipartFile image) {
        Tour tour = tourRepository.findByTourCode(tourCode)
                .orElseThrow(() -> new RuntimeException("Tour not found"));

        // Cập nhật các trường của tour từ yêu cầu cập nhật
        tourMapper.updateTour(tour, tourUpdateRequest);

        // Nếu có hình ảnh mới được cung cấp, tải lên và cập nhật URL hình ảnh của tour
        if (image != null && !image.isEmpty()) {
            String imageUrl = s3Service.uploadFile(image); // Tải lên hình ảnh mới
            tour.setImage(imageUrl); // Cập nhật URL hình ảnh mới
        }

        // Lưu tour đã cập nhật vào repository và trả về phản hồi
        return tourMapper.toTourResponse(tourRepository.save(tour));
    }


    @Override
    public void deleteTour(String tourCode) {
        tourRepository.deleteByTourCode(tourCode);
    }

    @Override
    public List<TourResponse> getAllTour() {
        return tourRepository.findAll().stream()
                .map(tourMapper::toTourResponse)
                .toList();
    }

    @Override
    public List<TourResponse> getToursByTypeId(String typeId) {
        List<Tour> tours = tourRepository.findAllByTypeId(typeId);
        return tours.stream()
                .map(tourMapper::toTourResponse)
                .toList();
    }

    @Override
    public List<TourResponse> getToursByTypeTourName(String typeTourName) {
        List<Tour> tours = tourRepository.findAllByTypeTourName(typeTourName);
        return tours.stream()
                .map(tourMapper::toTourResponse)
                .toList();
    }
    @Override
    public TourResponse getTourByTourCode(String tourCode) {
        Tour tour = tourRepository.findByTourCode(tourCode)
                .orElseThrow(() -> new AppException(ErrorCode.TOUR_NOTFOUND));
        return tourMapper.toTourResponse(tour);
    }

    @Override
    public TourResponse getTourById(ObjectId id) {
        Tour tour = tourRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TOUR_NOTFOUND));
        return tourMapper.toTourResponse(tour);
    }

    @Override
    public List<TourResponse> searchTours(String name, String locationStart, String durationTour, int limit) {
        // Tạo query với các điều kiện lọc
        Query query = new Query();

        // Nếu tên được cung cấp, tìm kiếm theo tên
        if (name != null && !name.isEmpty()) {
            query.addCriteria(Criteria.where("name").regex(name, "i")); // Tìm kiếm theo tên (không phân biệt chữ hoa/thường)
        }

        if (locationStart != null && !locationStart.isEmpty()) {
            query.addCriteria(Criteria.where("locationStart").regex(locationStart, "i")); // Tìm kiếm theo thời gian (chuỗi)
        }

        // Nếu thời gian được cung cấp, tìm kiếm theo thời gian
        if (durationTour != null && !durationTour.isEmpty()) {
            query.addCriteria(Criteria.where("durationTour").regex(durationTour, "i")); // Tìm kiếm theo thời gian (chuỗi)
        }

        // Nếu giá được cung cấp, lọc theo giá
//        if (price != null) {
//            query.addCriteria(Criteria.where("price").lte(price)); // Lọc giá nhỏ hơn hoặc bằng giá đã cung cấp
//        }



        // Giới hạn số lượng kết quả trả về
        query.limit(limit);

        // Thực hiện tìm kiếm và trả về kết quả
        List<Tour> tours = mongoTemplate.find(query, Tour.class);

        // Chuyển đổi kết quả tìm kiếm thành danh sách TourResponse
        return tours.stream()
                .map(tourMapper::toTourResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<TourResponse> searchToursAdmin(String name, String tourCode, int limit) {
        // Tạo query với các điều kiện lọc
        Query query = new Query();

        // Nếu tên được cung cấp, tìm kiếm theo tên
        if (name != null && !name.isEmpty()) {
            query.addCriteria(Criteria.where("name").regex(name, "i")); // Tìm kiếm theo tên (không phân biệt chữ hoa/thường)
        }

        // Nếu thời gian được cung cấp, tìm kiếm theo thời gian
        if (tourCode != null && !tourCode.isEmpty()) {
            query.addCriteria(Criteria.where("tourCode").regex(tourCode, "i")); // Tìm kiếm theo thời gian (chuỗi)
        }


        // Giới hạn số lượng kết quả trả về
        query.limit(limit);

        // Thực hiện tìm kiếm và trả về kết quả
        List<Tour> tours = mongoTemplate.find(query, Tour.class);

        // Chuyển đổi kết quả tìm kiếm thành danh sách TourResponse
        return tours.stream()
                .map(tourMapper::toTourResponse)
                .collect(Collectors.toList());
    }

}
