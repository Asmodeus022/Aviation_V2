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
        
        ToolEntity tool = new ToolEntity(
            null,
            toolDto.getPicture(),
            toolDto.getBarcodeId(),
            toolDto.getToolName(),
            toolDto.getStatus(),
            toolDto.getDateTime()
        );
        ToolEntity savedtool = toolRepository.save(tool);
        String pictureUrl = baseUrl + "/toolpictures/" + uploadedFileName;

        ToolDto response = new ToolDto(
            savedtool.getId(),
            savedtool.getBarcodeId(),
            savedtool.getToolName(),
            savedtool.getPicture(),
            savedtool.getStatus(),
            savedtool.getDateTime(),
            pictureUrl
        );
        return response;
    }

    @Override
    public ToolDto getToolById(Long toolId) {
        // 1. check the data in DB and if exists, fetch the data of given ID
        ToolEntity tool = toolRepository.findById(toolId).orElseThrow(()-> new ToolNotFoundException("Tool not found with id = " + toolId));
        // 2. generate posterUrl
        String pictureUrl = baseUrl + "/toolpictures/" + tool.getPicture();
        // 3. map to MovieDto object and return it
        ToolDto response = new ToolDto(
            tool.getId(),
            tool.getPicture(),
            tool.getBarcodeId(),
            tool.getToolName(),
            tool.getStatus(),
            tool.getDateTime(),
            pictureUrl
        );
        return response;}

    @Override
    public List<ToolDto> getAllTools() {
        // 1. fetch all data from DB
        List<ToolEntity> tools =toolRepository.findAll();
        
        List<ToolDto> toolDtos = new ArrayList<>();
        // 2. iterate throught the list, generate posterUrl for each movie obj, and map to MovieDto obj
        for(ToolEntity tool: tools){
            String pictureUrl = baseUrl + "/toolpictures/" + tool.getPicture();
            ToolDto toolDto = new ToolDto(
            tool.getId(),
            tool.getBarcodeId(),
            tool.getToolName(),
            tool.getPicture(),
            tool.getStatus(),
            tool.getDateTime(),
            pictureUrl
            );
        toolDtos.add(toolDto);
        }
       return toolDtos;
    }

    @SuppressWarnings({ "unused"})
    @Override
    public ToolDto updateTool(Long toolId, ToolDto toolDto, MultipartFile file) throws IOException {
        // 1. check if movie obj exists with given movie id
        ToolEntity tl = toolRepository.findById(toolId).orElseThrow(()-> new ToolNotFoundException("Tool not found with id = " + toolId));
        
        
        // 2. if file is null, do nothing if file is not null, then delete existing file associated with the record, and upload the new file
        String fileName= tl.getPicture();
        if(file != null){
            Files.deleteIfExists(Paths.get(path + File.separator + fileName));
            fileName = pictureService.uploadPicture(path, file);
        }

        // 3. set movieDto's poster value, according to step2
        toolDto.setPicture(fileName);
        // 4. map it to movie object
        ToolEntity tool = new ToolEntity(
            tl.getId(),
            toolDto.getBarcodeId(),
            toolDto.getToolName(),
            toolDto.getPicture(),
            toolDto.getStatus(),
            toolDto.getDateTime()
        );

        // 5. save the movie obj -> return saved movie object
        ToolEntity updatedTool = toolRepository.save(tool);
        // 6. generate posterUrl for it
        String pictureUrl = baseUrl + "/toolpictures/" + fileName;
        // 7. map to Movie Dto and to return it.
        ToolDto response= new ToolDto(
            tool.getId(),
            tool.getBarcodeId(),
            tool.getToolName(),
            tool.getPicture(),
            tool.getStatus(),
            tool.getDateTime(),
            pictureUrl
        );
        return response;    
    }

    @Override
    public String deleteTool(Long toolId) throws IOException {
         // 1. check if movie object exists in DB
         ToolEntity tl = toolRepository.findById(toolId).orElseThrow(()-> new ToolNotFoundException("Tool not found with id = " + toolId));
         Long id = tl.getId();
         // 2. delete the file associated with this object
         Files.deleteIfExists(Paths.get(path + File.separator + tl.getPicture()));
         // 3. detele the obj
         toolRepository.delete(tl);
         return "Tool Deleted with id = " + id;
     }

}
