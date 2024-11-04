package com.iuh.TourBooking.models.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.bson.types.ObjectId;

import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TypeTourResponse {
    private ObjectId id;
    private String name;
    private String typeId;

    // Phương thức để lấy ID dưới dạng chuỗi
    public String getIdAsString() {
        return id != null ? id.toHexString() : null;
    }
}
