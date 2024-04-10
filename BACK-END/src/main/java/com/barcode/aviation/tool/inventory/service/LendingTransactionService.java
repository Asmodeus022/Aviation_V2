package com.barcode.aviation.tool.inventory.service;

import java.io.IOException;
import java.util.List;
import com.barcode.aviation.tool.inventory.dto.LendingTransactionDto;

public interface LendingTransactionService {

    LendingTransactionDto addTransaction(LendingTransactionDto lendingTransactionDto) throws IOException;

    LendingTransactionDto getTransaction(Long lendingId);

    List<LendingTransactionDto> getAllTransaction();

    LendingTransactionDto updateTransaction(Long lendingId, LendingTransactionDto lendingTransactionDto) throws IOException;

    String deleteTransaction(Long lendingId) throws IOException;
}