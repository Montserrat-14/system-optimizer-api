package com.montserrat14.systemoptimizer.model.problem.factory;

import com.montserrat14.systemoptimizer.model.problem.ProblemRequest;

public interface Problems {

    void createProblem(ProblemRequest problemRequest);
    ProblemRequest getProblem();
}
