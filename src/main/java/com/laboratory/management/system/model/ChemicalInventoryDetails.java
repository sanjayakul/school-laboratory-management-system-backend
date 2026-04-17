<<<<<<< HEAD
package com.laboratory.management.system.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "chemical_inventory_details")
public class ChemicalInventoryDetails extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chemical_name", nullable = false)
    private String chemicalName;

    @Column(name = "chemical_formula", nullable = false, length = 100)
    private String chemicalFormula;

    @Column(name = "quantity", nullable = false)
    private BigDecimal quantity;

    @Column(name = "unit", nullable = false, length = 20)
    private String unit;

    @Column(name = "min_stock_level")
    private BigDecimal minStockLevel;

    @Column(name = "hazard_level", nullable = false, length = 20)
    private String hazardLevel;

    @Column(name = "storage_location", nullable = false, length = 100)
    private String storageLocation;

    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;

    @Column(name = "cas_number")
    private String casNumber;

    @Column(name = "supplier")
    private String supplier;

    @Column(name = "safety_notes", columnDefinition = "TEXT")
    private String safetyNotes;

    @Column(name = "status", nullable = false, length = 20)
    private String status;
}
=======
package com.laboratory.management.system.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "chemical_inventory_details")
public class ChemicalInventoryDetails extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chemical_name", nullable = false)
    private String chemicalName;

    @Column(name = "chemical_formula", nullable = false, length = 100)
    private String chemicalFormula;

    @Column(name = "quantity", nullable = false)
    private BigDecimal quantity;

    @Column(name = "unit", nullable = false, length = 20)
    private String unit;

    @Column(name = "min_stock_level")
    private BigDecimal minStockLevel;

    @Column(name = "hazard_level", nullable = false, length = 20)
    private String hazardLevel;

    @Column(name = "storage_location", nullable = false, length = 100)
    private String storageLocation;

    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;

    @Column(name = "cas_number")
    private String casNumber;

    @Column(name = "supplier")
    private String supplier;

    @Column(name = "safety_notes", columnDefinition = "TEXT")
    private String safetyNotes;

    @Column(name = "status", nullable = false, length = 20)
    private String status;
}
>>>>>>> e4e22ab (Initial commit)
