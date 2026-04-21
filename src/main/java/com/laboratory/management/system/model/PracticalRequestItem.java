package com.laboratory.management.system.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "practical_request_item")
@Data
public class PracticalRequestItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "practical_request_id", nullable = false)
    private PracticalRequest practicalRequest;

    @Column(name = "item_type", nullable = false, length = 50)
    private String itemType;

    @Column(name = "item_name", nullable = false, length = 255)
    private String itemName;

    @Column(name = "required_volume", precision = 10, scale = 2)
    private BigDecimal requiredVolume;

    @Column(name = "unit", length = 20)
    private String unit;
}
