package com.laboratory.management.system.service.criteria;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EquipmentInventoryCriteria {
    private String name;
    private String category;
    private String condition;
    private String location;
}

