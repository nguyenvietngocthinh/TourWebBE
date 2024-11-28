package com.iuh.TourBooking.utils;

import java.security.SecureRandom;

public class CouponGenerateCode {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 10;
    private static final SecureRandom random = new SecureRandom();

    public static String generateCodeCoupon() {
        StringBuilder codeCoupon = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
             codeCoupon.append(CHARACTERS.charAt(index));
        }
        return codeCoupon.toString();
    }
}
