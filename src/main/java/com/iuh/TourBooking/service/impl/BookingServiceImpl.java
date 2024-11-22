package com.iuh.TourBooking.service.impl;

import com.iuh.TourBooking.enums.ErrorCode;
import com.iuh.TourBooking.exception.AppException;
import com.iuh.TourBooking.mappers.BookingMapper;
import com.iuh.TourBooking.models.Booking;
import com.iuh.TourBooking.models.Tour;
import com.iuh.TourBooking.models.dto.request.BookingCreateRequest;
import com.iuh.TourBooking.models.dto.request.BookingUpdateRequest;
import com.iuh.TourBooking.models.dto.request.TourUpdateRequest;
import com.iuh.TourBooking.models.dto.response.BookingResponse;
import com.iuh.TourBooking.models.dto.response.TourResponse;
import com.iuh.TourBooking.repository.BookingRepository;
import com.iuh.TourBooking.repository.TourRepository;
import com.iuh.TourBooking.service.BookingService;
import com.iuh.TourBooking.service.EmailSenderService;
import com.iuh.TourBooking.utils.BookingGenerateCode;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.text.NumberFormat;
import java.util.Locale;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private TourRepository tourRepository;

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
        booking.setPayBooking(false);

        // Tìm tour theo mã tourCode để lấy tên tour
        Optional<Tour> tour = tourRepository.findByTourCode(bookingCreateRequest.getTourCode());
        String tourName = tour.map(Tour::getName).orElse("N/A");

        // Định dạng số tiền VNĐ
        NumberFormat currencyFormatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        String formattedTotalMoney = currencyFormatter.format(bookingCreateRequest.getTotalMoney());

        // Tạo nội dung email với thông tin chi tiết của booking
        String emailBody = "Đặt chỗ của bạn đã được tạo thành công.\n" +
                "Mã đặt chỗ: " + bookingCode + "\n" +
                "Tên tour: " + tourName + "\n" +
                "Thông tin khách hàng:\n" +
                "- Tên: " + bookingCreateRequest.getCustomerName() + "\n" +
                "- Email: " + bookingCreateRequest.getCustomerEmail() + "\n" +
                "- Số điện thoại: " + bookingCreateRequest.getCustomerPhoneNumber() + "\n" +
                "Chi tiết thanh toán:\n" +
                "- Số tiền: " + formattedTotalMoney + " VNĐ\n" +
                "- Phương thức thanh toán: " + bookingCreateRequest.getTypePay() + "\n" +
                "- Trạng thái thanh toán: " + (bookingCreateRequest.isPayBooking() ? "Đã thanh toán" : "Chưa thanh toán");

        // Gửi email cho khách hàng
        emailSenderService.send(bookingCreateRequest.getCustomerEmail(), "Xác nhận đặt chỗ", emailBody);


        return bookingMapper.toBookingResponse(bookingRepository.save(booking));
    }





    @Override
    public BookingResponse createBookingAdmin(BookingCreateRequest bookingCreateRequest) {

        // Sinh mã bookingCode và kiểm tra trùng lặp
        String bookingCode;
        do {
            bookingCode = BookingGenerateCode.generateBookingCode();
        } while (bookingRepository.existsByBookingCode(bookingCode));

        // Đặt mã bookingCode đã được tạo vào đối tượng booking
        bookingCreateRequest.setBookingCode(bookingCode);

        // Chuyển đổi từ DTO sang entity Booking
        Booking booking = bookingMapper.toBooking(bookingCreateRequest);

        // Tìm tour theo mã tourCode để lấy tên tour
        Optional<Tour> tour = tourRepository.findByTourCode(bookingCreateRequest.getTourCode());
        String tourName = tour.map(Tour::getName).orElse("N/A");

        // Định dạng số tiền VNĐ
        NumberFormat currencyFormatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        String formattedTotalMoney = currencyFormatter.format(bookingCreateRequest.getTotalMoney());

        // Tạo nội dung email với thông tin chi tiết của booking
        String emailBody = "Đặt chỗ của bạn đã được tạo thành công.\n" +
                "Mã đặt chỗ: " + bookingCode + "\n" +
                "Tên tour: " + tourName + "\n" +
                "Thông tin khách hàng:\n" +
                "- Tên: " + bookingCreateRequest.getCustomerName() + "\n" +
                "- Email: " + bookingCreateRequest.getCustomerEmail() + "\n" +
                "- Số điện thoại: " + bookingCreateRequest.getCustomerPhoneNumber() + "\n" +
                "Chi tiết thanh toán:\n" +
                "- Số tiền: " + formattedTotalMoney + " VNĐ\n" +
                "- Phương thức thanh toán: " + bookingCreateRequest.getTypePay() + "\n" +
                "- Trạng thái thanh toán: " + (bookingCreateRequest.isPayBooking() ? "Đã thanh toán" : "Chưa thanh toán");

        // Gửi email cho khách hàng
        emailSenderService.send(bookingCreateRequest.getCustomerEmail(), "Xác nhận đặt chỗ", emailBody);


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
    public BookingResponse updateBookingCode(String bookingCode, BookingUpdateRequest bookingUpdateRequest) {
        Booking booking = bookingRepository.findByBookingCode(bookingCode)
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

    @Override
    public BookingResponse getBookingByBookingCode(String bookingCode) {
        Booking booking = bookingRepository.findByBookingCode(bookingCode)
                .orElseThrow(() -> new AppException(ErrorCode.BOOKING_NOTFOUND));
        return bookingMapper.toBookingResponse(booking);
    }
}
