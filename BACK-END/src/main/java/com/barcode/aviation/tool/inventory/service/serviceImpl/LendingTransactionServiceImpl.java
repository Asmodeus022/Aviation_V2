package com.barcode.aviation.tool.inventory.service.serviceImpl;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import java.util.Optional;
import com.barcode.aviation.tool.inventory.dto.LendingTransactionDto;
import com.barcode.aviation.tool.inventory.entities.BorrowedTool;
import com.barcode.aviation.tool.inventory.entities.LendingTransaction;
import com.barcode.aviation.tool.inventory.entities.ToolEntity;
import com.barcode.aviation.tool.inventory.mapper.LendingTransactionMapper;
import com.barcode.aviation.tool.inventory.repository.LendingTransactionRepository;
import com.barcode.aviation.tool.inventory.repository.ToolRepository;
import com.barcode.aviation.tool.inventory.repository.UserRepository;
import com.barcode.aviation.tool.inventory.service.LendingTransactionService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class LendingTransactionServiceImpl implements LendingTransactionService {

    private final LendingTransactionRepository lendingTransactionRepository;
    private final ToolRepository toolRepository;
    private final UserRepository userRepository;
    private final LendingTransactionMapper lendingTransactionMapper;

    @Override
    public LendingTransactionDto addTransaction(LendingTransactionDto lendingTransactionDto) throws IOException {
        LendingTransactionMapper lendingTransacationMapper = new LendingTransactionMapper(toolRepository, userRepository);

        LendingTransaction lendingTransaction = lendingTransacationMapper.mapToLendingTransaction(lendingTransactionDto);

        LendingTransaction savedLendingTransaction = lendingTransactionRepository.save(lendingTransaction);

        return lendingTransacationMapper.mapToLendingTransactionDto(savedLendingTransaction);
    }

    @Override
    public LendingTransactionDto getTransaction(Long lendingId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTransaction'");
    }

    @Override
    public List<LendingTransactionDto> getAllTransaction() {
        List<LendingTransaction> lendingTransactions = lendingTransactionRepository.findAll();

        System.out.println("Number of transactions retrieved: " + lendingTransactions.size());
        return lendingTransactions.stream()
                .map(lendingTransactionMapper::mapToLendingTransactionDto)
                .collect(Collectors.toList());
    }

    @Override
    public LendingTransactionDto updateTransaction(Long lendingId, LendingTransactionDto lendingTransactionDto) throws IOException {

        Optional<LendingTransaction> lendingOptional = lendingTransactionRepository.findById(lendingId);

        if (lendingOptional.isPresent()) {
            LendingTransaction existingTransaction = lendingOptional.get();

            existingTransaction.getBorrowedTools().clear();

            List<BorrowedTool> borrowedTools = lendingTransactionDto.getBorrowedTools().stream()
                    .map(borrowedToolDto -> {
                        BorrowedTool borrowedTool = new BorrowedTool();
                        borrowedTool.setToolId(borrowedToolDto.getToolId());
                        borrowedTool.setStatus(borrowedToolDto.getStatus());

                        Optional<ToolEntity> toolEntity = toolRepository.findById(borrowedToolDto.getToolId());
                        if (toolEntity.isPresent()) {
                            borrowedTool.setToolName(toolEntity.get().getToolName());
                            borrowedTool.setToolBarcodeId(toolEntity.get().getBarcodeId());
                        }
                        return borrowedTool;
                    })
                    .collect(Collectors.toList());

            existingTransaction.setBorrowedTools(borrowedTools);
            existingTransaction.setStatus(lendingOptional.get().getStatus());

            LendingTransaction updatedTransaction = lendingTransactionRepository.save(existingTransaction);
            return lendingTransactionMapper.mapToLendingTransactionDto(updatedTransaction);
        } else {
            throw new RuntimeException("Lending not found with ID: " + lendingId);
        }
    }

    @Override
    public String deleteTransaction(Long lendingId) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteTransaction'");
    }

    // @Override
    // public Boolean addTransaction(TransactionRequest request) {
    //     Optional<UserEntity> user = userRepository.findById(Long.parseLong(request.getUserId()));

    //     if (user.isEmpty()) {
    //         return false;
    //     }


    //     return false;
    // }
    
}
