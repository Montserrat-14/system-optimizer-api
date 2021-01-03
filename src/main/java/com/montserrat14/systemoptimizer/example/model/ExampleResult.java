package com.montserrat14.systemoptimizer.example.model;

import java.util.List;

public class ExampleResult {

    private List<Vars> objectives;

    public ExampleResult(){

    }

    public ExampleResult(List<Vars> objectives) {

        this.objectives = objectives;
    }

    public List<Vars> getObjectives() {
        return objectives;
    }

    public void setObjectives(List<Vars> objectives) {
        this.objectives = objectives;
    }
}
