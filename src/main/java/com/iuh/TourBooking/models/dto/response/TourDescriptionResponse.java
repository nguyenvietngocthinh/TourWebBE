package com.iuh.TourBooking.models.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TourDescriptionResponse {
    @Id
    private ObjectId id;

    private String tourCode;       // Reference to the Tour's id
    private String header;       // Header for the description section
    private String content;      // Content of the description
    private String image;    // Image link related to this section
    public String getIdAsString() {
        return id != null ? id.toHexString() : null;
    }
}
