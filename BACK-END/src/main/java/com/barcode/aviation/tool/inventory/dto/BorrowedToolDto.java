package com.barcode.aviation.tool.inventory.dto;
import com.barcode.aviation.tool.inventory.entities.ToolStatus;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BorrowedToolDto {
    private Long toolId;
    private String toolname;
    private String toolBarcodeId;
    private ToolStatus status;
    private LocalDateTime borrowedDate;
    private LocalDateTime returnedDate;
}
