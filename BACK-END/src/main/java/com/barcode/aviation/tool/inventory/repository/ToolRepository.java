package com.barcode.aviation.tool.inventory.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.barcode.aviation.tool.inventory.entities.ToolEntity;

public interface ToolRepository extends JpaRepository <ToolEntity,Long> {
    Optional<ToolEntity> findByBarcodeId(String barcodeId);
}
