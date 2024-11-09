package com.iuh.TourBooking.models.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TourCreateRequest {
    private String tourCode; //Mã tour
    private String name;// Tên tour
    private String description;// Mô tả
    private String image; // Hình ảnh
    private String typeTourName; // Loại
    private String typeId;// Loại tour
    private String locationStart; // Điểm khởi hành
    private String locationFinish;  // Điểm kết thúc
    private List<String> startDay; // Ngày khởi hành
    private String durationTour;    // 2 Ngày 1 đêm
    private Double price;
    private String vehicle;
    private Boolean isActive;

    private boolean saleTour;
    private int percentSale;

}
