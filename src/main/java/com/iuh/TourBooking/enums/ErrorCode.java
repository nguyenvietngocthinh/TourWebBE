package com.iuh.TourBooking.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    PHONENUMBER_INVALID(1003, "Phone number must start with 0 and be 10 digits long", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1004, "Password must be at least 1 character long", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have a permission", HttpStatus.FORBIDDEN),
    TYPE_EXISTED(1008, "Type existed", HttpStatus.BAD_REQUEST),
    TYPETOUR_EXISTED(1009, "Type Tour existed", HttpStatus.BAD_REQUEST),
    TOUR_EXISTED(1010, "Tour existed", HttpStatus.BAD_REQUEST),
    UPLOADIMAGE_FAIL(1011, "Up file image fail", HttpStatus.BAD_REQUEST),
    INVALID_TYPETOUR_ID_FORMAT(1012, "Invalid typeTourId format", HttpStatus.BAD_REQUEST),
    BOOKING_EXISTED(1013, "Booking existed", HttpStatus.BAD_REQUEST),
    ;

    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;
}
