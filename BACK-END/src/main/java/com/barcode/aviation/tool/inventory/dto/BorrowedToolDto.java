package com.barcode.aviation.tool.inventory.dto;
import com.barcode.aviation.tool.inventory.entities.ToolStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BorrowedToolDto {
    private Long borrowedToolId;
    private Long toolId;
    private String toolName;
    private String toolBarcodeId;
    private ToolStatus status;
}
