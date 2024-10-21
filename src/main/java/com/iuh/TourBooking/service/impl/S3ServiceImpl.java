package com.iuh.TourBooking.service.impl;

import com.iuh.TourBooking.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {
    private final S3Client s3Client;

    @Value("${aws.bucketName}")
    private String bucketName;

    @Override
    public String uploadFile(String keyPrefix, MultipartFile file) {
        String key = keyPrefix + "/" + UUID.randomUUID() + "-" + file.getOriginalFilename();

        try {
            // Lưu tạm file vào đĩa
            Path tempFile = Files.createTempFile("s3-upload-", file.getOriginalFilename());
            Files.write(tempFile, file.getBytes(), StandardOpenOption.WRITE);

            // Tạo PutObjectRequest và upload file
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            PutObjectResponse response = s3Client.putObject(request, tempFile);
            Files.deleteIfExists(tempFile);  // Xóa file tạm sau khi upload

            if (response.sdkHttpResponse().isSuccessful()) {
                return s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(key)).toString();
            } else {
                throw new RuntimeException("Lỗi khi upload file lên S3");
            }
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi xử lý file", e);
        }
    }
}
