package com.barcode.aviation.tool.inventory.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name ="borrowed_tools")
public class BorrowedTool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long borrowedToolId;

    @Column(name = "tool_id")
    private Long toolId;

    @Column(name = "tool_name")
    private String toolName;

    @Column(name = "tool_barcodeId")
    private String toolBarcodeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ToolStatus status;
}
