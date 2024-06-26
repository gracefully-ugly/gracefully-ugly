package com.gracefullyugly.domain.image.controller;

import com.gracefullyugly.domain.image.service.ImageUploadService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
@Tag(name="이미지 업로드")
@Controller
public class ImageUploadController {

    private final ImageUploadService imageUploadService;

    @Autowired
    public ImageUploadController(ImageUploadService imageUploadService) {
        this.imageUploadService = imageUploadService;
    }

//    @PostMapping("/upload") //multipartFile null check 요망
//    public String upload(MultipartFile image, Model model) {
//        String imageUrl = imageUploadService.saveFile(image);
//
//        model.addAttribute("imageUrl", imageUrl);
//
//        return "image";
//    }
}
