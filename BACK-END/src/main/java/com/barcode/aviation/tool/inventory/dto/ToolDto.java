package com.barcode.aviation.tool.inventory.dto;
import com.barcode.aviation.tool.inventory.entities.ToolStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToolDto {
    private Long toolId;
    private String picture;
    private String barcodeId;
    private String toolName;
    private ToolStatus status;
    private LocalDateTime dateTime;
    private String pictureUrl;
}
