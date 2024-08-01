package com.storehousemgm.inventory.specification;

import com.storehousemgm.enums.MaterialType;
import com.storehousemgm.inventory.dto.InventorySearchCriteria;
import com.storehousemgm.inventory.entity.Inventory;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class InventorySpecification {

    public static Specification<Inventory> getSpecification(InventorySearchCriteria criteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getProductTitle() != null && !criteria.getProductTitle().isEmpty())
                predicates.add(criteriaBuilder.like(root.get("productTitle"), "%" + criteria.getProductTitle() + "%"));

            if (criteria.getMaterialTypes() != null && !criteria.getMaterialTypes().isEmpty())
                predicates.add(root.get("materialTypes").in(criteria.getMaterialTypes()));

            if (criteria.getDescription() != null && !criteria.getDescription().isEmpty())
                predicates.add(criteriaBuilder.like(root.get("description"), "%" + criteria.getDescription() + "%"));

            if (criteria.getMinPrice() != null && criteria.getMaxPrice() != null)
                predicates.add(criteriaBuilder.between(root.get("price"), criteria.getMinPrice(), criteria.getMaxPrice()));
            else if (criteria.getMinPrice() != null)
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), criteria.getMinPrice()));
            else if (criteria.getMaxPrice() != null)
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), criteria.getMaxPrice()));

            if ("oldToNew".equalsIgnoreCase(criteria.getSortOrder())) {
                query.orderBy(criteriaBuilder.asc(root.get("restockedAt")));
            } else if ("newToOld".equalsIgnoreCase(criteria.getSortOrder())) {
                query.orderBy(criteriaBuilder.desc(root.get("restockedAt")));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }


    public static Specification<Inventory> hasSearchCriteria(String criteria) {
        return (Root<Inventory> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            String[] parts = criteria.split(" ");
            Double minPrice = null;
            Double maxPrice = null;
            for (String part : parts) {
                try {
                    double value = Double.parseDouble(part);
                    if (minPrice == null)
                        minPrice = value;
                    else if (maxPrice == null)
                        maxPrice = value;
                    else
                        break;
                } catch (NumberFormatException e) {
                    try {
                        String[] materialTypeStrings = part.split(",");
                        List<MaterialType> materialTypes = new ArrayList<>();

                        for (String materialTypeString : materialTypeStrings) {
                            materialTypes.add(MaterialType.valueOf(materialTypeString.toUpperCase()));
                        }
                        List<Predicate> materialTypePredicates = new ArrayList<>();
                        for (MaterialType materialType : materialTypes) {
                            materialTypePredicates.add(root.get("materialTypes").in(materialType));
                        }
                        if (!materialTypePredicates.isEmpty())
                            predicates.add(builder.or(materialTypePredicates.toArray(new Predicate[0])));

                    } catch (IllegalArgumentException ex) {
                        predicates.add(builder.or(
                                builder.like(builder.lower(root.get("productTitle")), "%" + part.toLowerCase() + "%"),
                                builder.like(builder.lower(root.get("description")), "%" + part.toLowerCase() + "%")
                        ));
                    }
                }
            }
            if (minPrice != null)
                predicates.add(builder.greaterThanOrEqualTo(root.get("price"), minPrice));
            if (maxPrice != null)
                predicates.add(builder.lessThanOrEqualTo(root.get("price"), maxPrice));
            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
