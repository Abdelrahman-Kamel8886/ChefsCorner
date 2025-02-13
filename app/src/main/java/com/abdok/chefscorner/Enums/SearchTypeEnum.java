package com.abdok.chefscorner.Enums;

public enum SearchTypeEnum {


    INGREDIENT("Ingredient"),
    CATEGORY("Category"),
    AREA("Area");

    private final String value;

    SearchTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
