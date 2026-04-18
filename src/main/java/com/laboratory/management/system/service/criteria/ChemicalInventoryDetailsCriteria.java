package com.laboratory.management.system.service.criteria;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChemicalInventoryDetailsCriteria {
    private String status;
    private String chemicalName;
    private String chemicalFormula;
    private String casNumber;
}
