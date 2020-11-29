package com.montserrat14.systemoptimizer.model;

import java.util.ArrayList;
import java.util.List;

public class Problem {

    private Integer id;
    private String name;
    private String description;

    private List<Variables> listOfVariables;

    public Problem(){}

    public Problem(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.listOfVariables = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Variables> getListOfVariables() {
        return listOfVariables;
    }

    public void setListOfVariables(List<Variables> listOfVariables) {
        this.listOfVariables = listOfVariables;
    }
}
