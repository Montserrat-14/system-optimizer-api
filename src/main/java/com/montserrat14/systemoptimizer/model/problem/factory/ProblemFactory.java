package com.montserrat14.systemoptimizer.model.problem.factory;

import com.montserrat14.systemoptimizer.model.problem.generic.BinaryProblem;
import com.montserrat14.systemoptimizer.model.problem.generic.DoubleProblem;
import com.montserrat14.systemoptimizer.model.problem.generic.IntegerProblem;

public class ProblemFactory {

    public Problems getProblem(String problemName){

        if(problemName == null){
                return null;
        }
        if(problemName.equalsIgnoreCase(ProblemTypes.BINARY.getType())){
            return new BinaryProblem();

        } else if(problemName.equalsIgnoreCase(ProblemTypes.INTEGER.getType())){
            return new IntegerProblem();

        } else if(problemName.equalsIgnoreCase(ProblemTypes.DOUBLE.getType())){
            return new DoubleProblem();
        }

        return null;
    }
}
