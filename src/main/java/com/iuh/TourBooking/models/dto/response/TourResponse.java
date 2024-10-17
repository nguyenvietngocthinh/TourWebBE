package com.iuh.TourBooking.models.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TourResponse {
    private String tourId;
    private String tourCode;
    private String name;
    private String description;
    private String image;
    private String typeTourId;
    private String typeId;
    private String locationStart;
    private String locationFinish;
    private List<LocalDate> availableDates;  // Chứa nhiều ngày khởi hành
    private String timeDate;
    private String endDate;
    private Double price;
    private Integer maxPeople;
    private Integer currentPeople;
    private String vehicle;
    private String note;
    private Boolean isActive;
}
