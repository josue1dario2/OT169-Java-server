package com.alkemy.ong.controller;

import com.alkemy.ong.service.impl.AmazonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/storage/")
public class AmazonController {

    private final AmazonService amazonService;
    @Autowired
    public AmazonController(AmazonService amazonService){
        this.amazonService = amazonService;
    }

    // Method recieves MultipartFile as a RequestPart
    @PostMapping(value = "/uploadFile", consumes = {MULTIPART_FORM_DATA_VALUE} )
    public String uploadFile(@RequestParam(value = "file") MultipartFile file) {
        return this.amazonService.uploadFile(file);
    }

    @DeleteMapping("/deleteFile")
    public String deleteFile(@RequestPart(value = "url") String fileUrl) {
        return this.amazonService.deleteFileFromS3Bucket(fileUrl);
    }

}