package com.iuh.TourBooking.service;

import com.iuh.TourBooking.models.dto.request.TourCreateRequest;
import com.iuh.TourBooking.models.dto.request.TourUpdateRequest;
import com.iuh.TourBooking.models.dto.request.TypeTourCreateRequest;
import com.iuh.TourBooking.models.dto.request.TypeTourUpdateRequest;
import com.iuh.TourBooking.models.dto.response.TourResponse;
import com.iuh.TourBooking.models.dto.response.TypeTourResponse;
import org.bson.types.ObjectId;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TourService {
    public TourResponse createTour(TourCreateRequest tourCreateRequest, MultipartFile image);

    public TourResponse updateTour(ObjectId id, TourUpdateRequest tourUpdateRequest, MultipartFile image);

    public TourResponse updateTourByTourCode(String tourCode, TourUpdateRequest tourUpdateRequest, MultipartFile image);
    public void deleteTour(String tourCode);
    public List<TourResponse> getAllTour();
    public List<TourResponse> getToursByTypeId(String typeTourId);

    public List<TourResponse> getToursByTypeTourName(String typeTourName);

    public TourResponse getTourByTourCode(String tourCode);

    public TourResponse getTourById(ObjectId id);

    public List<TourResponse> searchTours(String name, String durationTour, String locationStart, int limit);

    public List<TourResponse> searchToursAdmin(String name, String tourCode, int limit);

    public long getTotalActiveTours();
}
