package com.progmasters.fundraiser.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/upload")
public class FileUploadController {

    @PostMapping
    public ResponseEntity<?> singleFileUpload(@RequestParam("file") MultipartFile file) {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dcv2q68tl",
                "api_key", "215793984119191",
                "api_secret", "-qQXvNWiKYF8SgQlOtSwIlNhm5w"));

        String uploadedURL = null;
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            uploadedURL = uploadResult.get("url").toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(uploadedURL, HttpStatus.CREATED);
    }


}
