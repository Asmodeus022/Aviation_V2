package com.barcode.aviation.tool.inventory.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.barcode.aviation.tool.inventory.service.ToolService;
import com.google.zxing.WriterException;

import java.io.IOException;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/barcode")
public class BarcodeController {
    private final ToolService toolService;

    public BarcodeController(ToolService toolService) {
        this.toolService = toolService;
    }

    @PostMapping("/generate")
    public ResponseEntity<byte[]> generateBarcode(@RequestParam String barcodeId,
                                                  @RequestParam int width,
                                                  @RequestParam int height) throws WriterException {
        byte[] barcodeImage = toolService.generateBarcodeForTool(barcodeId, width, height);
        if (barcodeImage != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(barcodeImage);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/decode")
    public ResponseEntity<String> decodeBarcode(@RequestParam("image") MultipartFile image) {
        try {
            byte[] imageBytes = image.getBytes();
            String decodedBarcode = toolService.decodeBarcodeFromImage(imageBytes);
            if (decodedBarcode != null) {
                return ResponseEntity.ok(decodedBarcode);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @PostMapping("/generateBarcode")
    public ResponseEntity<byte[]> generateBarcode(@RequestParam String barcodeId,
                                                   @RequestParam int width,
                                                   @RequestParam int height,
                                                   @RequestParam int margin) {
        try {
            byte[] barcodeImage = toolService.generateBarcodeForToolAndSaveAsImage(barcodeId, width, height, margin);
            if (barcodeImage != null) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_PNG)
                        .body(barcodeImage);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
}

