package com.montserrat14.systemoptimizer.model.problem.generic;


import com.montserrat14.systemoptimizer.model.problem.Problem;
import com.montserrat14.systemoptimizer.model.problem.factory.Problems;
import org.uma.jmetal.problem.integerproblem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.integersolution.IntegerSolution;

import java.util.ArrayList;
import java.util.List;

public class IntegerProblem extends AbstractIntegerProblem implements Problems {

    @Override
    public void createProblem(Problem problem) {

        setName(problem.getName());

        setNumberOfVariables(problem.getListOfVariables().size());
        setNumberOfObjectives(problem.getnObjectives());

        List<Integer> lowerLimit = new ArrayList<>(getNumberOfVariables()) ;
        List<Integer> upperLimit = new ArrayList<>(getNumberOfVariables()) ;

        for (int i = 0; i < getNumberOfVariables(); i++) {
            lowerLimit.add(problem.getListOfVariables().get(i).getMin());
            upperLimit.add(problem.getListOfVariables().get(i).getMax());
        }

        setVariableBounds(lowerLimit,upperLimit);

    }

    @Override
    public void evaluate(IntegerSolution integerSolution) {
        int approximationToN = 0;
        int approximationToM = 0 ;

        /*
        for (int i = 0; i < integerSolution.getNumberOfVariables(); i++) {
            int value = integerSolution.getVariable(i) ;
            approximationToN += Math.abs(valueN - value) ;
            approximationToM += Math.abs(valueM - value) ;
        }
         */

        integerSolution.setObjective(0, approximationToN);
        integerSolution.setObjective(1, approximationToM);
    }


}
