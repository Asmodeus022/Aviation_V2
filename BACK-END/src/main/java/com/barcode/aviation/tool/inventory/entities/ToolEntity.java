package com.barcode.aviation.tool.inventory.entities;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="tools")
public class ToolEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Please provide a pictureURL")
    private String picture;

    @Column(nullable = false)
    @NotBlank(message = "Please provide a barcodeID!")
    private String barcodeId;

    @Column(nullable = false)
    @NotBlank(message = "Please provide a name!")
    private String toolName;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tool_status", nullable = false)
    private ToolStatus status;

    @CreationTimestamp
    private LocalDateTime dateTime;
}