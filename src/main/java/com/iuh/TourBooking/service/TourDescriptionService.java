package com.iuh.TourBooking.service;

import com.iuh.TourBooking.models.dto.request.TourCreateRequest;
import com.iuh.TourBooking.models.dto.request.TourDescriptionCreateRequest;
import com.iuh.TourBooking.models.dto.request.TourDescriptionUpdateRequest;
import com.iuh.TourBooking.models.dto.request.TourUpdateRequest;
import com.iuh.TourBooking.models.dto.response.TourDescriptionResponse;
import com.iuh.TourBooking.models.dto.response.TourResponse;
import org.bson.types.ObjectId;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TourDescriptionService {
    public TourDescriptionResponse createTourDescription(TourDescriptionCreateRequest tourDescriptionCreateRequest, MultipartFile image);

    public TourDescriptionResponse updateTourDescription(ObjectId id, TourDescriptionUpdateRequest tourDescriptionUpdateRequest, MultipartFile image);

    public void deleteTourDescription(ObjectId id);
    public List<TourDescriptionResponse> getAllTourDescription();

}
