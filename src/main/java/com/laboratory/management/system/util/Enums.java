package com.laboratory.management.system.util;

public enum Enums {

    ACTIVE("A");

    private final String status;

    Enums(String status) {
        this.status = status;
    }

    public String value() {
        return status;
    }

    public String getName() {
        return this.name();
    }
}
