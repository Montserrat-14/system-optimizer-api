package com.montserrat14.systemoptimizer.model;

import java.util.ArrayList;
import java.util.List;

public class Variables {

    private String name;
    private String type;
    private Integer min;
    private Integer max;
    private Boolean boolvalue;
    private String description;

    public Variables(){}
    public Variables(String name, String type, Integer min, Integer max, String description) {
        this.name = name;
        this.type = type;
        this.min = min;
        this.max = max;
        this.description = description;
    }
    public Variables(String name, String type, Boolean boolvalue, String description) {
        this.name = name;
        this.type = type;
        this.boolvalue = boolvalue;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Boolean getBoolvalue() {
        return boolvalue;
    }

    public void setBoolvalue(Boolean boolvalue) {
        this.boolvalue = boolvalue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
