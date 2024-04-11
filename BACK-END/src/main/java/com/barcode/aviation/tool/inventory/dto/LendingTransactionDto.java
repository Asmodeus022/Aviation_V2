package com.barcode.aviation.tool.inventory.dto;

import com.barcode.aviation.tool.inventory.entities.TransactionStatus;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LendingTransactionDto {
    private Long id;
    private UserDto user;
    private List<BorrowedToolDto> borrowedTools;
    private TransactionStatus status;
    private String dueDate;
    private LocalDateTime borrowedDate;
}
