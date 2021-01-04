package com.montserrat14.systemoptimizer.model.problem.factory;

public enum ProblemTypes {

    INTEGER("int"),
    DOUBLE("double"),
    BINARY("bool");

    private String type;

    ProblemTypes(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
