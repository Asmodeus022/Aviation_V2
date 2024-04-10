package com.barcode.aviation.tool.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.barcode.aviation.tool.inventory.entities.LendingTransaction;

public interface LendingTransactionRepository extends JpaRepository<LendingTransaction, Long> {
    
}
