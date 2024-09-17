package com.iuh.TourBooking.models;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Set;

@Document(collection = "users")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    private ObjectId id;
    private String phoneNumber;
    private String password;
    private String username;
    private String email;
    private String address;
    private Boolean gender;
    private Date dateOfBirth;
    private Set<String> roles;
    private Date createdAt = new Date();
    private Boolean isActive = true;

}
