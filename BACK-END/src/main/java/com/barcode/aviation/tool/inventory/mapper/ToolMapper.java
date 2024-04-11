package com.barcode.aviation.tool.inventory.mapper;

import com.barcode.aviation.tool.inventory.dto.ToolDto;
import com.barcode.aviation.tool.inventory.entities.ToolEntity;

public class ToolMapper {
    
    public static ToolDto mapToToolDto(ToolEntity tool, String pictureUrl) {
        return new ToolDto(
            tool.getId(),
            tool.getPicture(),
            tool.getBarcodeId(),
            tool.getToolName(),
            tool.getStatus(),
            tool.getDateTime(),
            pictureUrl
        );
    }

    public static ToolEntity mapToToolEntity(ToolDto toolDto) {
        return new ToolEntity(
            toolDto.getToolId(),
            toolDto.getPicture(),
            toolDto.getBarcodeId(),
            toolDto.getToolName(),
            toolDto.getStatus(),
            toolDto.getDateTime()
        );
    }

    public static ToolEntity mapToToolEntityWithId(ToolDto toolDto, Long toolId) {
        return new ToolEntity(
            toolId,
            toolDto.getPicture(),
            toolDto.getBarcodeId(),
            toolDto.getToolName(),
            toolDto.getStatus(),
            toolDto.getDateTime()
        );
    }

    public static ToolEntity mapToToolEntityWithId(ToolDto toolDto, Long toolId) {
        return new ToolEntity(
            toolId,
            toolDto.getPicture(),
            toolDto.getBarcodeId(),
            toolDto.getToolName(),
            toolDto.getStatus(),
            toolDto.getDateTime()
        );
    }
}
