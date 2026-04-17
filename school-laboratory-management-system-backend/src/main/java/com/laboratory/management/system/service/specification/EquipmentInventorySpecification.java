package com.laboratory.management.system.service.specification;

import com.laboratory.management.system.model.EquipmentInventory;
import com.laboratory.management.system.service.criteria.EquipmentInventoryCriteria;
import org.springframework.data.jpa.domain.Specification;

public class EquipmentInventorySpecification {
    public static Specification<EquipmentInventory> matchName(String name) {
        return (root, query, cb) -> name == null ? null : cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }
    public static Specification<EquipmentInventory> matchCategory(String category) {
        return (root, query, cb) -> category == null ? null : cb.equal(cb.lower(root.get("category")), category.toLowerCase());
    }
    public static Specification<EquipmentInventory> matchCondition(String condition) {
        return (root, query, cb) -> condition == null ? null : cb.equal(cb.lower(root.get("condition")), condition.toLowerCase());
    }
    public static Specification<EquipmentInventory> matchLocation(String location) {
        return (root, query, cb) -> location == null ? null : cb.like(cb.lower(root.get("location")), "%" + location.toLowerCase() + "%");
    }
}

