package com.barcode.aviation.tool.inventory.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.barcode.aviation.tool.inventory.dto.ToolDto;
import com.barcode.aviation.tool.inventory.entities.ToolEntity;
import com.barcode.aviation.tool.inventory.repository.ToolRepository;

import com.barcode.aviation.tool.inventory.exception.FileExistsException;
import com.barcode.aviation.tool.inventory.exception.ToolNotFoundException;
import com.barcode.aviation.tool.inventory.mapper.ToolMapper;

@Service
public class ToolServiceImpl implements ToolService {
    private final ToolRepository toolRepository;

    private final PictureService pictureService;

    public ToolServiceImpl(PictureService pictureService, ToolRepository toolRepository) {
        this.pictureService = pictureService;
        this.toolRepository =toolRepository;
    }

    @Value("${project.toolpictures}")
    private String path;

    @Value("${base.url}")
    private String baseUrl;

    @Override
    public ToolDto addTool(ToolDto toolDto, MultipartFile file) throws IOException {
        if(Files.exists(Paths.get(path + File.separator + file.getOriginalFilename()))){
            throw new FileExistsException("File already exists! Please enter another file name");
        }
        String uploadedFileName = pictureService.uploadPicture(path, file);
        toolDto.setPicture(uploadedFileName);
        
        ToolEntity tool = ToolMapper.mapToToolEntity(toolDto);
        ToolEntity saveTool = toolRepository.save(tool);

        String pictureUrl = baseUrl + "/toolpictures/" + uploadedFileName;

        return ToolMapper.mapToToolDto(saveTool, pictureUrl);
    }

    @Override
    public ToolDto getToolById(Long toolId) {
        ToolEntity tool = toolRepository.findById(toolId).orElseThrow(()-> new ToolNotFoundException("Tool not found with id = " + toolId));
        String pictureUrl = baseUrl + "/toolpictures/" + tool.getPicture();

        ToolDto toolDto = ToolMapper.mapToToolDto(tool, pictureUrl);
        return toolDto;
    }

    @Override
    public List<ToolDto> getAllTools() {

        List<ToolEntity> tools =toolRepository.findAll();
        
        List<ToolDto> toolDtos = new ArrayList<>();
        
        for(ToolEntity tool: tools){
            String pictureUrl = baseUrl + "/toolpictures/" + tool.getPicture();
            ToolDto toolDto = ToolMapper.mapToToolDto(tool, pictureUrl);
            toolDtos.add(toolDto);
        }
       return toolDtos;
    }

    @Override
    public ToolDto updateTool(Long toolId, ToolDto toolDto, MultipartFile file) throws IOException {

        ToolEntity tl = toolRepository.findById(toolId).orElseThrow(()-> new ToolNotFoundException("Tool not found with id = " + toolId));
        
        String fileName= tl.getPicture();
        if(file != null){
            Files.deleteIfExists(Paths.get(path + File.separator + fileName));
            fileName = pictureService.uploadPicture(path, file);
        }

        toolDto.setPicture(fileName);
        ToolEntity tool = ToolMapper.mapToToolEntityWithId(toolDto, toolId);


        ToolEntity saveTool = toolRepository.save(tool);
        String pictureUrl = baseUrl + "/toolpictures/" + fileName;

        return ToolMapper.mapToToolDto(saveTool, pictureUrl);    
    }

    @Override
    public String deleteTool(Long toolId) throws IOException {
         ToolEntity tl = toolRepository.findById(toolId).orElseThrow(()-> new ToolNotFoundException("Tool not found with id = " + toolId));
         Long id = tl.getId();
         Files.deleteIfExists(Paths.get(path + File.separator + tl.getPicture()));
         toolRepository.delete(tl);
         return "Tool Deleted with id = " + id;
     }

    @Override
    public ToolDto getToolByBarcodeId(String barcodeId) {
        ToolEntity tool = toolRepository.findByBarcodeId(barcodeId).orElseThrow(()-> new ToolNotFoundException("Tool not found with id = " + barcodeId));
        String pictureUrl = baseUrl + "/toolpictures/" + tool.getPicture();

        ToolDto toolDto = ToolMapper.mapToToolDto(tool, pictureUrl);
        return toolDto;
    }
}