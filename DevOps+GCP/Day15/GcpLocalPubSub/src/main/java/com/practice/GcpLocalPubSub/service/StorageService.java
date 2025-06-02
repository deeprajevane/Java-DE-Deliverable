package com.practice.GcpLocalPubSub.service;


import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
public class StorageService {

    @Autowired
    private Storage storage;

    public String uploadFile(MultipartFile file, String bucketName) throws IOException {
        BlobId blobId = BlobId.of(bucketName, file.getOriginalFilename());
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        storage.create(blobInfo, file.getBytes());
        return "File uploaded to GCS emulator!";
    }

    public ResponseEntity<Resource> downloadFile(String bucketName, String fileName) {
        // Get the file from GCS
        Blob blob = storage.get(bucketName, fileName);

        if (blob == null) {
            throw new RuntimeException("File not found: " + fileName);
        }

        // Prepare the response
        ByteArrayResource resource = new ByteArrayResource(blob.getContent());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}