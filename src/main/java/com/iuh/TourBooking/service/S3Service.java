package com.iuh.TourBooking.service;

import org.springframework.web.multipart.MultipartFile;

public interface S3Service {
    public String uploadFile(MultipartFile file);
}
