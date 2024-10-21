package com.iuh.TourBooking.service;

import com.iuh.TourBooking.models.dto.request.TourCreateRequest;
import com.iuh.TourBooking.models.dto.request.TourUpdateRequest;
import com.iuh.TourBooking.models.dto.request.TypeTourCreateRequest;
import com.iuh.TourBooking.models.dto.request.TypeTourUpdateRequest;
import com.iuh.TourBooking.models.dto.response.TourResponse;
import com.iuh.TourBooking.models.dto.response.TypeTourResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TourService {
    public TourResponse createTour(TourCreateRequest tourCreateRequest, MultipartFile image);

    public TourResponse updateTour(String tourId, TourUpdateRequest tourUpdateRequest);
    public void deleteTour(String tourId);
    public List<TourResponse> getAllTour();
}
