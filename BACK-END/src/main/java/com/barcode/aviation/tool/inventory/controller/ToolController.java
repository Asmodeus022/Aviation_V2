package com.barcode.aviation.tool.inventory.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.barcode.aviation.tool.inventory.dto.ToolDto;
import com.barcode.aviation.tool.inventory.exception.FileExistsException;
import com.barcode.aviation.tool.inventory.service.ToolService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/tools")
public class ToolController {
    private final ToolService toolService;

    // Build Add Tool Rest API
    @PostMapping
    public ResponseEntity<ToolDto> addTool(@RequestPart MultipartFile file,
                                           @RequestPart String toolDto) throws IOException {
        if (file.isEmpty()) {
            throw new FileExistsException("File is empty! Please send another file!");
        }
        ToolDto dto = convertToToolDto(toolDto);
        return new ResponseEntity<>(toolService.addTool(dto, file), HttpStatus.CREATED);
    }

    // Build Get Tool by ID REST API
    @GetMapping("/{id}")
    public ResponseEntity<ToolDto> getToolById(@PathVariable("id") Long toolId) {
        ToolDto toolDto = toolService.getToolById(toolId);
        return ResponseEntity.ok(toolDto);
    }

    // Build Get Tool by Barcode ID REST API
    @GetMapping("/barcode/{id}")
    public ResponseEntity<ToolDto> getToolByBarcodeId(@PathVariable("id") String toolBarcodeId) {
        ToolDto toolDto = toolService.getToolByBarcodeId(toolBarcodeId);
        return ResponseEntity.ok(toolDto);
    }

    // Build Get All Tools REST API
    @GetMapping
    public ResponseEntity<List<ToolDto>> getAllTools() {
        List<ToolDto> tools = toolService.getAllTools();
        return ResponseEntity.ok(tools);
    }

    // Build Update Tool REST API
    @PutMapping("/{toolId}")
    public ResponseEntity<ToolDto> updateToolHandler(@PathVariable Long toolId,
                                                     @RequestPart MultipartFile file,
                                                     @RequestPart String toolDtoObj) throws IOException {
        if (file.isEmpty()) {
            file = null;
        }
        ToolDto toolDto = convertToToolDto(toolDtoObj);
        return ResponseEntity.ok(toolService.updateTool(toolId, toolDto, file));
    }
    
    // Build Delete Tool REST API
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteToolHandler(@PathVariable Long id) throws IOException {
        return ResponseEntity.ok(toolService.deleteTool(id));
    }

    private ToolDto convertToToolDto(String toolDtoObj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(toolDtoObj, ToolDto.class);
    }
}
