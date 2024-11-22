package com.iuh.TourBooking.mappers;

import com.iuh.TourBooking.models.Booking;
import com.iuh.TourBooking.models.Tour;
import com.iuh.TourBooking.models.dto.request.*;
import com.iuh.TourBooking.models.dto.response.BookingResponse;
import com.iuh.TourBooking.models.dto.response.TourResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    Booking toBooking(BookingCreateRequest bookingCreateRequest);
    BookingResponse toBookingResponse(Booking booking);
    void updateBooking(@MappingTarget Booking booking, BookingUpdateRequest bookingUpdateRequest);

}
