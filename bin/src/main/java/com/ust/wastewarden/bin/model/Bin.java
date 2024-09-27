package com.ust.wastewarden.bin.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bins")
public class Bin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String location;
    private Double latitude;
    private Double longitude;

    private int wasteAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BinStatus status;

    private int fillLevel;

    private LocalDateTime createdAt = LocalDateTime.now();
    private Date lastCollectionDate;
    private Date nextCollectionDate;
}
