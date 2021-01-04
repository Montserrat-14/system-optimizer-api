package com.montserrat14.systemoptimizer.model.problem.factory;

import com.montserrat14.systemoptimizer.model.problem.ProblemRequest;
import com.montserrat14.systemoptimizer.model.problem.generic.BinaryProblem;
import com.montserrat14.systemoptimizer.model.problem.generic.DoubleProblem;
import com.montserrat14.systemoptimizer.model.problem.generic.IntegerProblem;

public class ProblemFactory {

    public Problems getProblem(ProblemRequest request){

        String problemType =  request.getListOfVariables().get(0).getType();

        if(problemType == null){
                return null;
        }
        if(problemType.equalsIgnoreCase(ProblemTypes.BINARY.getType())){
            BinaryProblem newProblem =  new BinaryProblem();
            newProblem.createProblem(request);
            return newProblem;

        } else if(problemType.equalsIgnoreCase(ProblemTypes.INTEGER.getType())){
            IntegerProblem newProblem =  new IntegerProblem();
            newProblem.createProblem(request);
            return newProblem;

        } else if(problemType.equalsIgnoreCase(ProblemTypes.DOUBLE.getType())){
            DoubleProblem newProblem = new DoubleProblem();
            newProblem.createProblem(request);
            return newProblem;
        }

        return null;
    }
}
