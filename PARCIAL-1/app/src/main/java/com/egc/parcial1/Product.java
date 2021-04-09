package com.egc.parcial1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Product implements Serializable {
    String code;
    String name;
    Float value;
    boolean hasIva;
    String description;
    String category;

    public Product(String code, String name, Float value, boolean hasIva, String description, String category) {
        this.code = code;
        this.name = name;
        this.value = value;
        this.hasIva = hasIva;
        this.description = description;
        this.category = category;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public boolean isHasIva() {
        return hasIva;
    }

    public void setHasIva(boolean hasIva) {
        this.hasIva = hasIva;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
