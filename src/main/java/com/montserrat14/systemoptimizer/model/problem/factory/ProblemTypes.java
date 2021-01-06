package com.montserrat14.systemoptimizer.model.problem.factory;

public enum ProblemTypes {

    INTEGER("int"),
    DOUBLE("double"),
    BINARY("binary");

    private String type;

    ProblemTypes(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
