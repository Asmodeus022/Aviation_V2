package com.barcode.aviation.tool.inventory.mapper;

import org.springframework.stereotype.Component;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import com.barcode.aviation.tool.inventory.dto.BorrowedToolDto;
import com.barcode.aviation.tool.inventory.dto.LendingTransactionDto;
import com.barcode.aviation.tool.inventory.dto.UserDto;
import com.barcode.aviation.tool.inventory.entities.BorrowedTool;
import com.barcode.aviation.tool.inventory.entities.LendingTransaction;
import com.barcode.aviation.tool.inventory.entities.ToolEntity;
import com.barcode.aviation.tool.inventory.entities.TransactionStatus;
import com.barcode.aviation.tool.inventory.entities.User;
import com.barcode.aviation.tool.inventory.exception.ToolNotFoundException;
import com.barcode.aviation.tool.inventory.exception.UserNotFoundException;
import com.barcode.aviation.tool.inventory.repository.ToolRepository;
import com.barcode.aviation.tool.inventory.repository.UserRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class LendingTransactionMapper {
    
    private final ToolRepository toolRepository;
    private final UserRepository userRepository;

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public LendingTransactionDto mapToLendingTransactionDto(LendingTransaction lendingTransaction) {
        List<BorrowedToolDto> borrowedToolDtos = lendingTransaction.getBorrowedTools().stream()
            .map(borrowedTool -> {
                BorrowedToolDto borrowedToolDto = new BorrowedToolDto();
                borrowedToolDto.setBorrowedToolId(borrowedTool.getBorrowedToolId());
                borrowedToolDto.setToolId(borrowedTool.getToolId());
                borrowedToolDto.setToolBarcodeId(borrowedTool.getToolBarcodeId());
                borrowedToolDto.setToolName(borrowedTool.getToolName());
                borrowedToolDto.setStatus(borrowedTool.getStatus());
                return borrowedToolDto;
            })
            .collect(Collectors.toList());

        LocalDateTime currentDate = LocalDateTime.now();
        long daysUntilDue = ChronoUnit.DAYS.between(currentDate, lendingTransaction.getDueDate());
        String dueDate = String.valueOf(daysUntilDue);
    
        Optional<User> userOptional = userRepository.findByUserId(lendingTransaction.getUser().getUserId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserDto userDto = new UserDto(user.getUserId(), user.getEmail(), user.getFullname(), user.getUserType());
    
            return new LendingTransactionDto(
                lendingTransaction.getId(),
                userDto,
                borrowedToolDtos,
                lendingTransaction.getStatus(),
                dueDate,
                lendingTransaction.getBorrowedDate()
            );
        } else {
            throw new UserNotFoundException("User not found with ID: " + lendingTransaction.getUser().getUserId());
        }
    }
    
    

    public LendingTransaction mapToLendingTransaction(LendingTransactionDto lendingTransactionDto) {
        List<BorrowedToolDto> borrowedToolDtos = lendingTransactionDto.getBorrowedTools();
        List<BorrowedTool> borrowedTools = new ArrayList<>();
    
        LocalDateTime currentDate = LocalDateTime.now();

        UserDto userDto = lendingTransactionDto.getUser();
        User user = new User(userDto.getUserId(), userDto.getEmail(), userDto.getFullname(), userDto.getUserType());

        for (BorrowedToolDto borrowedToolDto : borrowedToolDtos) {
            Optional<ToolEntity> toolOptional = toolRepository.findById(borrowedToolDto.getToolId());
            if (toolOptional.isPresent()) {
                ToolEntity toolEntity = toolOptional.get();
                BorrowedTool borrowedTool = new BorrowedTool();
                borrowedTool.setBorrowedToolId(borrowedToolDto.getBorrowedToolId());
                borrowedTool.setToolId(borrowedToolDto.getToolId());
                borrowedTool.setToolBarcodeId(toolEntity.getBarcodeId());
                borrowedTool.setToolName(toolEntity.getToolName());
                borrowedTool.setStatus(borrowedToolDto.getStatus());
                borrowedTools.add(borrowedTool);
            } else {
                throw new ToolNotFoundException("Tool not found with Id: " + borrowedToolDto.getToolId());
            }
        }

        int daysUntilDue = lendingTransactionDto.getDueDate() != null ? Integer.parseInt(lendingTransactionDto.getDueDate()) : 0;
        LocalDateTime dueDate = LocalDateTime.now().plusDays(daysUntilDue);
    
        return new LendingTransaction(
            null,
            user,
            borrowedTools,
            TransactionStatus.ON_GOING,
            dueDate,
            LocalDateTime.parse(currentDate.format(formatter), formatter)
        );
    }
}