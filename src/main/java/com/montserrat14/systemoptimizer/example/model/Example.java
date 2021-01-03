package com.montserrat14.systemoptimizer.example.model;

import java.util.List;

public class Example {

    private Integer numberOfObjectives;
    private List<Vars> vars;

    public Example(){

    }

    public Example(Integer numberOfObjectives, List<Vars> vars) {
        this.numberOfObjectives = numberOfObjectives;
        this.vars = vars;
    }

    public Integer getNumberOfObjectives() {
        return numberOfObjectives;
    }

    public void setNumberOfObjectives(Integer numberOfObjectives) {
        this.numberOfObjectives = numberOfObjectives;
    }

    public List<Vars> getVars() {
        return vars;
    }

    public void setVars(List<Vars> vars) {
        this.vars = vars;
    }
}
