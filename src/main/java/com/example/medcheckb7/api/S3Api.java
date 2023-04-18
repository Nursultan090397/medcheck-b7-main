package com.example.medcheckb7.api;

import com.example.medcheckb7.db.entities.service.impl.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("api/file")
public class S3Api {
    private final S3Service service;

    @Operation(summary = "Upload file", description = "Upload file to database")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> uploadFile(@RequestParam(name = "file") MultipartFile file) throws IOException {
        return service.uploadFile(file);
    }

    @Operation(summary = "Delete file", description = "Delete file from database")
    @DeleteMapping("/")
    public Map<String, String> deleteFile(@RequestParam String fileLink) {
        return service.delete(fileLink);
    }
}
