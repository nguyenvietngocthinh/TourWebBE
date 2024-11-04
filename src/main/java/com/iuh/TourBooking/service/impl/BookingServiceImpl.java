package com.iuh.TourBooking.service.impl;

import com.iuh.TourBooking.enums.ErrorCode;
import com.iuh.TourBooking.exception.AppException;
import com.iuh.TourBooking.mappers.BookingMapper;
import com.iuh.TourBooking.models.Booking;
import com.iuh.TourBooking.models.Tour;
import com.iuh.TourBooking.models.dto.request.BookingCreateRequest;
import com.iuh.TourBooking.models.dto.request.BookingUpdateRequest;
import com.iuh.TourBooking.models.dto.response.BookingResponse;
import com.iuh.TourBooking.models.dto.response.TourResponse;
import com.iuh.TourBooking.repository.BookingRepository;
import com.iuh.TourBooking.service.BookingService;
import com.iuh.TourBooking.service.EmailSenderService;
import com.iuh.TourBooking.utils.BookingGenerateCode;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingMapper bookingMapper;

    @Autowired
    private EmailSenderService emailSenderService;

    @Override
    public BookingResponse createBooking(BookingCreateRequest bookingCreateRequest) {

        // Sinh mã bookingCode và kiểm tra trùng lặp
        String bookingCode;
        do {
            bookingCode = BookingGenerateCode.generateBookingCode();
        } while (bookingRepository.existsByBookingCode(bookingCode));

        // Đặt mã bookingCode đã được tạo vào đối tượng booking
        bookingCreateRequest.setBookingCode(bookingCode);

        // Chuyển đổi từ DTO sang entity Booking
        Booking booking = bookingMapper.toBooking(bookingCreateRequest);
        booking.setPay(false);
        booking.setActive(true);

        // Gửi email với mã booking code
        String emailBody = "Đặt chỗ của bạn đã được tạo thành công. Mã đặt chỗ của bạn là: " + bookingCode;
        emailSenderService.send(bookingCreateRequest.getCustomerEmail(), "Xác nhận đặt chỗ", emailBody);

        // Lưu booking vào cơ sở dữ liệu và trả về response
        return bookingMapper.toBookingResponse(bookingRepository.save(booking));
    }


    @Override
    public BookingResponse updateBooking(ObjectId id, BookingUpdateRequest bookingUpdateRequest) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        bookingMapper.updateBooking(booking, bookingUpdateRequest);

        return bookingMapper.toBookingResponse(bookingRepository.save(booking));
    }

    @Override
    public void deleteBooking(String bookingCode) {
        bookingRepository.deleteByBookingCode(bookingCode);
    }

    @Override
    public List<BookingResponse> getAllBooking() {
        return bookingRepository.findAll().stream()
                .map(bookingMapper::toBookingResponse)
                .toList();
    }
}
