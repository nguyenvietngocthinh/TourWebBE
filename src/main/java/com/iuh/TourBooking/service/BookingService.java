package com.iuh.TourBooking.service;

import com.iuh.TourBooking.models.dto.request.BookingCreateRequest;
import com.iuh.TourBooking.models.dto.request.BookingUpdateRequest;
import com.iuh.TourBooking.models.dto.response.BookingResponse;
import com.iuh.TourBooking.models.dto.response.TourResponse;
import org.bson.types.ObjectId;

import java.util.List;


public interface BookingService {
    public BookingResponse createBooking(BookingCreateRequest bookingCreateRequest);

    public BookingResponse updateBooking(ObjectId id, BookingUpdateRequest bookingUpdateRequest);
    public void deleteBooking(String bookingCode);
    public List<BookingResponse> getAllBooking();
}
