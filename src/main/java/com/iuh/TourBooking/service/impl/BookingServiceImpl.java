package com.iuh.TourBooking.service.impl;

import com.iuh.TourBooking.enums.ErrorCode;
import com.iuh.TourBooking.exception.AppException;
import com.iuh.TourBooking.mappers.BookingMapper;
import com.iuh.TourBooking.models.Booking;
import com.iuh.TourBooking.models.Tour;
import com.iuh.TourBooking.models.dto.request.*;
import com.iuh.TourBooking.models.dto.response.BookingResponse;
import com.iuh.TourBooking.repository.BookingRepository;
import com.iuh.TourBooking.repository.TourRepository;
import com.iuh.TourBooking.service.BookingService;
import com.iuh.TourBooking.service.EmailSenderService;
import com.iuh.TourBooking.utils.BookingGenerateCode;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.*;
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
        booking.setActiveBooking("Hoạt động");

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
        booking.setActiveBooking("Hoạt động");

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
    public BookingResponse updateBookingToPendingCancel(String bookingCode) {
        Booking booking = bookingRepository.findByBookingCode(bookingCode)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // Cập nhật trạng thái activeBooking thành "Chờ hủy"
        booking.setActiveBooking("Chờ hủy");

        // Lưu lại booking với trạng thái đã cập nhật
        return bookingMapper.toBookingResponse(bookingRepository.save(booking));
    }

    @Override
    public BookingResponse updateBookingToCancelled(String bookingCode) {
        // Tìm booking theo bookingCode
        Booking booking = bookingRepository.findByBookingCode(bookingCode)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // Cập nhật trạng thái activeBooking thành "Đã hủy"
        booking.setActiveBooking("Đã hủy");

        // Lưu lại booking với trạng thái đã cập nhật
        Booking updatedBooking = bookingRepository.save(booking);

        // Tạo nội dung email thông báo hủy booking
        String emailBody = "Đặt chỗ của bạn với mã đặt chỗ " + bookingCode + " đã được hủy thành công." ;

        // Gửi email thông báo đến khách hàng
        emailSenderService.send(booking.getCustomerEmail(), "Thông báo hủy đặt chỗ", emailBody);

        // Trả về booking response
        return bookingMapper.toBookingResponse(updatedBooking);
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

    public void updateBookingPaymentStatus(String bookingCode, boolean payBooking) {
        Booking booking = bookingRepository.findByBookingCode(bookingCode)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // Cập nhật trạng thái thanh toán của booking
        booking.setPayBooking(payBooking);

        // Lưu lại booking với trạng thái đã cập nhật
        bookingRepository.save(booking);
    }

    @Override
    public void sendBookingConfirmationEmail(String bookingCode) {
        Optional<Booking> bookingOptional = bookingRepository.findByBookingCode(bookingCode);
        if (bookingOptional.isPresent()) {
            Booking booking = bookingOptional.get();

            // Tạo nội dung email
            String emailBody = "Thanh toán của bạn đã thành công. Dưới đây là thông tin đặt chỗ của bạn:\n\n" +
                    "Mã đặt chỗ: " + bookingCode + "\n" +
                    "Tên khách hàng: " + booking.getCustomerName() + "\n" +
                    "Số tiền: " + booking.getTotalMoney() + " VNĐ\n" +
                    "Trạng thái thanh toán: Đã thanh toán";

            // Gửi email
            emailSenderService.send(booking.getCustomerEmail(), "Xác nhận thanh toán", emailBody);
        }
    }

    public long getTotalCompletedBookings() {
        return bookingRepository.countByActiveBooking("Hoạt động");  // Đếm số lượng booking với activeBooking = "Hoàn thành"
    }

    // Hàm chuyển đổi từ String sang Date với định dạng DD-MM-YYYY
    private Date convertStringToDate(String dateStr) throws Exception {
        // Thay đổi dấu phân cách thành "/" để khớp với định dạng ngày tháng của bạn
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.parse(dateStr);
    }

    // Hàm tính tổng doanh thu của bookings trong năm hiện tại
    public double getTotalRevenueForYear(int year) {
        // Lấy danh sách tất cả các bookings
        List<Booking> bookings = bookingRepository.findAll();

        double totalRevenue = 0.0;

        // Lặp qua tất cả các bookings để tính doanh thu
        for (Booking booking : bookings) {
            try {
                // Chuyển đổi bookingDate thành Date
                Date bookingDate = convertStringToDate(booking.getBookingDate());

                // Kiểm tra nếu bookingDate nằm trong năm hiện tại và các điều kiện khác
                if (bookingDate.getYear() + 1900 == year && booking.isPayBooking() && "Hoạt động".equals(booking.getActiveBooking())) {
                    totalRevenue += booking.getTotalMoney();  // Thêm doanh thu vào tổng
                }
            } catch (Exception e) {
                e.printStackTrace();
                // Nếu có lỗi khi chuyển đổi ngày, bạn có thể xử lý theo cách riêng
            }
        }
        return totalRevenue;
    }

}
