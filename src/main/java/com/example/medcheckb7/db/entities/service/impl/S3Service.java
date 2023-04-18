package com.example.medcheckb7.db.entities.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.util.Map;

@Service
public class S3Service {
    @Autowired
    private S3Client s3;

    @Value("${aws.path}")
    private String BUCKET_PATH;

    @Value("${aws.bucket.name}")
    private String bucketName;

    public Map<String, String> uploadFile(MultipartFile file) throws IOException {
        String key = System.currentTimeMillis() + file.getOriginalFilename();
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .contentType("jpeg")
                .contentType("png")
                .contentType("pdf")
                .contentType("gif")
                .contentType("tiff")
                .contentLength(file.getSize())
                .key(key).build();
        s3.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        return Map.of("link", BUCKET_PATH + key);
    }

    public Map<String, String> delete(String fileLink) {
        try {

            String key = fileLink.substring(BUCKET_PATH.length());

            s3.deleteObject(dor -> dor.bucket(bucketName).key(key).build());

        } catch (S3Exception e) {
            throw new IllegalStateException(e.awsErrorDetails().errorMessage());
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }

        return Map.of(
                "message", fileLink + " has been deleted"
        );
    }

}
