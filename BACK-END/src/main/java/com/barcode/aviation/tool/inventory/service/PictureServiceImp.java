package com.barcode.aviation.tool.inventory.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PictureServiceImp implements PictureService{
    @Override
    public String uploadPicture(String path, MultipartFile file) throws IOException {
        //get name of the file
        String fileName = file.getOriginalFilename();
        //to get the file path
        String filePath = path + File.separator + fileName;
        //create a file object
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }
        //copy the file or upload file to the path
        Files.copy(file.getInputStream(),Paths.get(filePath));
        return fileName;
    }

    @Override
    public InputStream getResourceFile(String path, String fileName) throws FileNotFoundException {
        String filePath= path + File.separator + fileName;
        return new FileInputStream(filePath);
    }
    
}
