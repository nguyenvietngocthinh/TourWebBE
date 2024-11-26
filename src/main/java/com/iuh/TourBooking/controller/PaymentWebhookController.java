package com.iuh.TourBooking.controller;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.iuh.TourBooking.service.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping("/pay")
@Slf4j
public class PaymentWebhookController {

    @Value("${stripe.webhook.secret}")
    private String endpointSecret;  // Secret từ Stripe Dashboard

    @Value("${stripe.api.key}")
    private String stripeSecretKey;

    private final BookingService bookingService;

    private static final Logger logger = LoggerFactory.getLogger(PaymentWebhookController.class);

    public PaymentWebhookController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // Phương thức này sẽ được gọi khi người dùng chuyển hướng đến trang "done"
    // API xử lý trang paydone
    @GetMapping("/done")
    public ResponseEntity<String> handlePaymentSuccess(@RequestParam("session_id") String sessionId) {
        if (sessionId == null || sessionId.isEmpty()) {
            return ResponseEntity.badRequest().body("Không tìm thấy session_id.");
        }

        try {
            // Lấy thông tin session từ Stripe sử dụng session_id
            Stripe.apiKey = stripeSecretKey; // Đảm bảo bạn đã cấu hình stripe secret key
            Session session = Session.retrieve(sessionId);

            // Kiểm tra xem thanh toán đã thành công chưa
            if (session != null) {
                String bookingCode = session.getMetadata().get("bookingCode");

                // Cập nhật trạng thái thanh toán của booking
                bookingService.updateBookingPaymentStatus(bookingCode, true);

                // Gửi email xác nhận thanh toán thành công
                bookingService.sendBookingConfirmationEmail(bookingCode);

                return ResponseEntity.ok("Thanh toán thành công và email đã được gửi!");
            } else {
                return ResponseEntity.badRequest().body("Thanh toán không thành công hoặc session không hợp lệ.");
            }
        } catch (StripeException e) {
            log.error("Stripe error: " + e.getMessage(), e);
            return ResponseEntity.status(500).body("Lỗi khi xử lý thanh toán.");
        } catch (Exception e) {
            log.error("Error: " + e.getMessage(), e);
            return ResponseEntity.status(500).body("Lỗi không xác định.");
        }
    }



    // Cách này vẫn giữ nguyên phương thức webhook để xử lý từ Stripe
    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader) {

        try {
            // Xác thực sự kiện từ Stripe
            Event event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
            logger.info("Received event: " + event);

            if ("checkout.session.completed".equals(event.getType())) {
                // Lấy session từ sự kiện
                Session session = (Session) event.getDataObjectDeserializer().getObject().orElse(null);
                if (session != null) {
                    // Log thông tin metadata để kiểm tra bookingCode
                    logger.info("Received session metadata: " + session.getMetadata());

                    // Lấy bookingCode từ metadata
                    String bookingCode = session.getMetadata().get("bookingCode");
                    logger.info("Booking Code: " + bookingCode);

                    // Cập nhật trạng thái thanh toán
                    if (bookingCode != null) {
                        bookingService.updateBookingPaymentStatus(bookingCode, true);
                    } else {
                        logger.error("Booking Code không tồn tại trong metadata");
                        return ResponseEntity.badRequest().body("Booking code not found in metadata");
                    }
                }
            }

            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            logger.error("Webhook error: " + e.getMessage(), e);
            return ResponseEntity.badRequest().body("Webhook error: " + e.getMessage());
        }
    }

}
