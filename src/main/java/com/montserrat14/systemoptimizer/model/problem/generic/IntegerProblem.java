package com.montserrat14.systemoptimizer.model.problem.generic;


import com.montserrat14.systemoptimizer.example.model.Example;
import com.montserrat14.systemoptimizer.example.model.ExampleResult;
import com.montserrat14.systemoptimizer.example.model.Vars;
import com.montserrat14.systemoptimizer.model.problem.ProblemRequest;
import com.montserrat14.systemoptimizer.model.problem.factory.Problems;
import org.springframework.web.client.RestTemplate;
import org.uma.jmetal.problem.integerproblem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.integersolution.IntegerSolution;

import java.util.ArrayList;
import java.util.List;

public class IntegerProblem extends AbstractIntegerProblem implements Problems {

    private ProblemRequest problemRequest;

    @Override
    public void createProblem(ProblemRequest problemRequest) {

        this.problemRequest = problemRequest;

        setName(problemRequest.getName());

        setNumberOfVariables(problemRequest.getListOfVariables().size());
        setNumberOfObjectives(problemRequest.getnObjectives());

        List<Integer> lowerLimit = new ArrayList<>(getNumberOfVariables()) ;
        List<Integer> upperLimit = new ArrayList<>(getNumberOfVariables()) ;

        for (int i = 0; i < getNumberOfVariables(); i++) {
            lowerLimit.add(problemRequest.getListOfVariables().get(i).getMin());
            upperLimit.add(problemRequest.getListOfVariables().get(i).getMax());
        }

        setVariableBounds(lowerLimit,upperLimit);

    }

    @Override
    public void evaluate(IntegerSolution integerSolution) {

        int approximationToN = 0;
        int approximationToM = 0 ;
        int valueN = 100;
        int valueM = -100 ;

        for (int i = 0; i < integerSolution.getNumberOfVariables(); i++) {
            int value = integerSolution.getVariable(i) ;
            approximationToN += Math.abs(valueN - value) ;
            approximationToM += Math.abs(valueM - value) ;
        }
        // Create the Rest Template
        RestTemplate restTemplate = new RestTemplate();
        Example newExampleInteger = new Example();

        newExampleInteger.setNumberOfObjectives(getNumberOfObjectives());

        List<Vars> vars = new ArrayList<>();
        vars.add(new Vars(String.valueOf(approximationToN)));
        vars.add(new Vars(String.valueOf(approximationToM)));

        newExampleInteger.setVars(vars);

        ExampleResult result = restTemplate.postForObject(problemRequest.getEndpoint(), newExampleInteger, ExampleResult.class);

        // Set the Result
        integerSolution.setObjective(0, Integer.parseInt(result.getObjectives().get(0).getValue()));
        integerSolution.setObjective(1, Integer.parseInt(result.getObjectives().get(1).getValue()));
    }

    public ProblemRequest getProblem() {
        return this.problemRequest;
    }

}
