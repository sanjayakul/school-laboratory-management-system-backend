package com.laboratory.management.system.service.specification;

import com.laboratory.management.system.model.ChemicalInventoryDetails;
import org.springframework.data.jpa.domain.Specification;

public class ChemicalInventoryDetailsSpecification {

    public static Specification<ChemicalInventoryDetails> matchStatus(String status) {

        if (status == null || status.isBlank()) {
            return null;
        } else {
            return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
        }
    }

    public static Specification<ChemicalInventoryDetails> matchChemicalName(String chemicalName) {

        if (chemicalName == null || chemicalName.isBlank()) {
            return null;
        } else {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.upper(root.get("chemicalName")),
                            "%" + chemicalName.toUpperCase() + "%");


        }
    }

    public static Specification<ChemicalInventoryDetails> matchChemicalFormula(String chemicalFormula) {

        if (chemicalFormula == null || chemicalFormula.isBlank()) {
            return null;
        } else {
            return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("chemicalFormula"), chemicalFormula);
        }
    }

    public static Specification<ChemicalInventoryDetails> matchCasNumber(String casNumber) {

        if (casNumber == null || casNumber.isBlank()) {
            return null;
        } else {
            return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("casNumber"), casNumber);
        }
    }
}
