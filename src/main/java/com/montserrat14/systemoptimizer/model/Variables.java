package com.montserrat14.systemoptimizer.model;

import java.util.ArrayList;
import java.util.List;

public class Variables {

    private String name;
    private List<Integer> listVariablesInput;

    public Variables(){}
    public Variables(String name) {
        this.name = name;
        this.listVariablesInput = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getListVariablesInput() {
        return listVariablesInput;
    }

    public void setListVariablesInput(List<Integer> listVariablesInput) {
        this.listVariablesInput = listVariablesInput;
    }
}
