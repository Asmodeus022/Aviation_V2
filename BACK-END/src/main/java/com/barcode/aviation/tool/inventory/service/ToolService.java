package com.barcode.aviation.tool.inventory.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.barcode.aviation.tool.inventory.dto.ToolDto;
import com.google.zxing.WriterException;

public interface ToolService {
    ToolDto addTool(ToolDto toolDto, MultipartFile file) throws IOException;

    ToolDto getToolById(Long toolId);

    ToolDto getToolByBarcodeId(String barcodeId);

    List<ToolDto> getAllTools();

    ToolDto updateTool(Long toolId, ToolDto updatedtool, MultipartFile file) throws IOException;

    String deleteTool(Long toolId) throws IOException;

    byte[] generateBarcodeForTool(String barcodeId, int width, int height) throws WriterException;

    String decodeBarcodeFromImage(byte[] barcodeImageBytes);

    byte[] generateBarcodeForToolAndSaveAsImage(String barcodeId, int width, int height, int margin);

}
