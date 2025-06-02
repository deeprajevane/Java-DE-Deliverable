package com.practice.GcpLocalPubSub.controller;


import com.practice.GcpLocalPubSub.service.StorageService;
import com.practice.GcpLocalPubSub.service.PubSubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private StorageService storageService;

    @Autowired
    private PubSubService pubSubService;

    @PostMapping("/upload")
    public String uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("bucket") String bucketName
    ) throws IOException, InterruptedException {
        String result = storageService.uploadFile(file, bucketName);
        pubSubService.publishMessage("New file uploaded: " + file.getOriginalFilename());
        return result;
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> download(
            @RequestParam String bucketName,
            @RequestParam String fileName
    ) {
        pubSubService.publishMessage("File downloaded: " + fileName);
        return storageService.downloadFile(bucketName, fileName);
    }
}
