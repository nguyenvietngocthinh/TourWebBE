package com.iuh.TourBooking.models;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
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

    private String tourCode; //Mã tour
    private String name;// Tên tour
    private List<ObjectId> description;// Mô tả
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
