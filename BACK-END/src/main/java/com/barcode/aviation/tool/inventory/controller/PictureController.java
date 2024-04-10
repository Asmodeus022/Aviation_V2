package com.barcode.aviation.tool.inventory.controller;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.barcode.aviation.tool.inventory.service.PictureService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/toolpictures/")
public class PictureController {
    private final PictureService pictureService;

    public PictureController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @Value("${project.toolpictures}")
    private String path;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFileHandler(@RequestPart MultipartFile file) throws IOException{
        pictureService.uploadPicture(path, file);
        String uploadedFileName = pictureService.uploadPicture(path, file);
        return ResponseEntity.ok("File uploaded : " + uploadedFileName);
    }

    @GetMapping("/{fileName}")
    public void serveFileHandler(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        
        InputStream resourceFile = pictureService.getResourceFile(path, fileName);
        String contentType;
        if(fileName.endsWith(".png")){
            contentType = MediaType.IMAGE_PNG_VALUE;
        }else if(fileName.endsWith(".jpeg")|| fileName.endsWith(".jpg")){
            contentType = MediaType.IMAGE_JPEG_VALUE;
        }else{
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        response.setContentType(contentType);
        StreamUtils.copy(resourceFile, response.getOutputStream());
    }
}
