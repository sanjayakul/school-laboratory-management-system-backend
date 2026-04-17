package com.laboratory.management.system.service.specification;

import com.laboratory.management.system.model.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    public static Specification<User> matchStatus(String status) {

        if (status == null || status.isBlank()) {
            return null;
        } else {
            return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
        }
    }

    public static Specification<User> matchUserName(String userName) {

        if (userName == null || userName.isBlank()) {
            return null;
        } else {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.upper(root.get("username")),
                            "%" + userName.toUpperCase() + "%");


        }
    }

    public static Specification<User> matchFullName(String fullName) {

        if (fullName == null || fullName.isBlank()) {
            return null;
        } else {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.upper(root.get("fullName")),
                            "%" + fullName.toUpperCase() + "%");


        }
    }

    public static Specification<User> matchRole(String role) {

        if (role == null || role.isBlank()) {
            return null;
        } else {
            return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("role"), role);
        }
    }

    public static Specification<User> matchEmail(String email) {

        if (email == null || email.isBlank()) {
            return null;
        } else {
            return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("email"), email);
        }
    }

}
