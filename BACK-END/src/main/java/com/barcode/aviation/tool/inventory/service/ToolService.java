package com.barcode.aviation.tool.inventory.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.barcode.aviation.tool.inventory.dto.ToolDto;

public interface ToolService {
    ToolDto addTool(ToolDto toolDto, MultipartFile file) throws IOException;

    ToolDto getToolById(Long toolId);

    List<ToolDto> getAllTools();

    ToolDto updateTool(Long toolId, ToolDto updatedtool, MultipartFile file) throws IOException;

    String deleteTool(Long toolId) throws IOException;

}
