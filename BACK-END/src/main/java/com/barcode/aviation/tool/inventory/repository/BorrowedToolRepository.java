package com.barcode.aviation.tool.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.barcode.aviation.tool.inventory.entities.BorrowedTool;

public interface BorrowedToolRepository extends JpaRepository<BorrowedTool, Long> {
}
