package com.barcode.aviation.tool.inventory.dto.request;

import java.util.List;

import lombok.Data;

@Data
public class TransactionRequest {
    private String userId;
    
    private List<BorrowedToolsRequest> borrowedTools;
}
