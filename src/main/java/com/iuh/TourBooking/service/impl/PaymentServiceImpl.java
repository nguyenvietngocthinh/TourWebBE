package com.iuh.TourBooking.service.impl;

import com.iuh.TourBooking.models.Booking;
import com.iuh.TourBooking.models.dto.response.PaymentResponse;
import com.iuh.TourBooking.service.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    @Value("${stripe.api.key}")
    private String stripeSecretKey;
    @Override
    public PaymentResponse createPaymentLink(Booking booking) throws StripeException {
        Stripe.apiKey = stripeSecretKey;

        SessionCreateParams params = SessionCreateParams.builder().addPaymentMethodType(
                SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:3000/thank-you")
                .setCancelUrl("http://localhost:3000/thank-you")
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("VND")
                                .setUnitAmount((long) booking.getTotalMoney())
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName("Tour booking")
                                        .build())
                                .build())
                        .build())
                .build();

        Session session = Session.create(params);
        PaymentResponse res = new PaymentResponse();
        res.setPaymentUrl(session.getUrl());
        return res;
    }
}