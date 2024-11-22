package com.iuh.TourBooking.models;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tour_descriptions")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TourDescription {
    @Id
    private ObjectId id;

    private String tourCode;       // Reference to the Tour's id
    private String header;       // Header for the description section
    private String content;      // Content of the description
    private String image;    // Image link related to this section
}
