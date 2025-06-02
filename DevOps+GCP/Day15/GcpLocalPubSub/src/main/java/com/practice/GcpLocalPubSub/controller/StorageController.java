package com.practice.GcpLocalPubSub.controller;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/storage")
public class StorageController {

    @Autowired
    private Storage storage;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file,
                             @RequestParam("bucket") String bucketName) throws IOException {
        BlobId blobId = BlobId.of(bucketName, file.getOriginalFilename());
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        storage.create(blobInfo, file.getBytes());
        return "File uploaded to GCS emulator!";
    }

    @GetMapping("/list")
    public List<String> listFiles(@RequestParam String bucketName) {
        List<String> files = new ArrayList<>();
        for (Blob blob : storage.list(bucketName).iterateAll()) {
            files.add(blob.getName());
        }
        return files;
    }
}
