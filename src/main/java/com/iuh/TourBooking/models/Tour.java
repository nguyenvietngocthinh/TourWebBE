package com.iuh.TourBooking.models;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "tours")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tour {
    @Id
    private ObjectId id;

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
