package com.iuh.TourBooking.models.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TourDescriptionCreateRequest {

    private String tourCode;       // Reference to the Tour's id
    private String header;       // Header for the description section
    private String content;      // Content of the description
    private String linkImage;    // Image link related to this section
}
