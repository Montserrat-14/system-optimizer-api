package com.montserrat14.systemoptimizer.model.problem;

import java.util.ArrayList;
import java.util.List;

public class Variables {

    private String name;
    private String type;
    private Integer min;
    private Integer max;
    private Integer bits;
    private String description;

    public Variables(){}
    public Variables(String name, String type, Integer min, Integer max, Integer bits, String description) {
        this.name = name;
        this.type = type;
        this.min = min;
        this.max = max;
        this.bits =  bits;
        this.description = description;
    }
    public Variables(String name, String type, String description) {
        this.name = name;
        this.type = type;
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

    public Integer getBits() {
        return bits;
    }

    public void setBits(Integer bits) {
        this.bits = bits;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
