package com.iuh.TourBooking.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error"),
    INVALID_KEY(1001, "Uncategorized error"),
    USER_EXISTED(1002, "User existed"),
    PHONENUMBER_INVALID(1003, "Phone number must start with 0 and be 10 digits long"),
    PASSWORD_INVALID(1004, "Password must be at least 1 character long"),
    ;

    private int code;
    private String message;
}
