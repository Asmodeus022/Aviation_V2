package com.barcode.aviation.tool.inventory.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.barcode.aviation.tool.inventory.dto.LendingTransactionDto;
import com.barcode.aviation.tool.inventory.service.LendingTransactionService;

import lombok.AllArgsConstructor;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/transaction")
public class LendingTransactionController {
    private final LendingTransactionService lendingTransactionService;

    @PostMapping("/add-transaction")
    public ResponseEntity<LendingTransactionDto> addTransaction(@RequestBody LendingTransactionDto lendingTransactionDto) throws IOException{
        LendingTransactionDto addLendingTransactionDto = lendingTransactionService.addTransaction(lendingTransactionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(addLendingTransactionDto);
    }
}
