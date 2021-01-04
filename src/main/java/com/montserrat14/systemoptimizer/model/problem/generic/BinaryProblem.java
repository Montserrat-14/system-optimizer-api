package com.montserrat14.systemoptimizer.model.problem.generic;

import com.montserrat14.systemoptimizer.example.model.Example;
import com.montserrat14.systemoptimizer.example.model.ExampleResult;
import com.montserrat14.systemoptimizer.example.model.Vars;
import com.montserrat14.systemoptimizer.model.problem.ProblemRequest;
import com.montserrat14.systemoptimizer.model.problem.factory.Problems;
import org.springframework.web.client.RestTemplate;
import org.uma.jmetal.problem.binaryproblem.impl.AbstractBinaryProblem;
import org.uma.jmetal.solution.binarysolution.BinarySolution;
import org.uma.jmetal.util.binarySet.BinarySet;

import java.util.ArrayList;
import java.util.List;

public class BinaryProblem extends AbstractBinaryProblem implements Problems {

    private ProblemRequest problemRequest;
    private int[] bitsPerVariable ;

    @Override
    public void createProblem(ProblemRequest problemRequest) {

        this.problemRequest = problemRequest;

        setName(problemRequest.getName());

        setNumberOfVariables(problemRequest.getListOfVariables().size());
        setNumberOfObjectives(problemRequest.getnObjectives());

        bitsPerVariable = new int[problemRequest.getListOfVariables().size()] ;

        bitsPerVariable[0] = 30;
        for (int var = 1; var < problemRequest.getListOfVariables().size(); var++) {
            bitsPerVariable[var] = 5;
        }

    }

    // We do not use this override method
    @Override
    public List<Integer> getListOfBitsPerVariable() {
        return null;
    }

    @Override
    public void evaluate(BinarySolution binarySolution) {

        List<BinarySet> binarySets = binarySolution.getVariables();

        // Create the Rest Template
        RestTemplate restTemplate = new RestTemplate();
        Example newExampleBinary = new Example();

        newExampleBinary.setNumberOfObjectives(getNumberOfObjectives());

        List<Vars> vars = new ArrayList<>();
        for (int i = 0; i < binarySolution.getNumberOfVariables(); i++) {
            vars.add(new Vars(String.valueOf(binarySets.get(i))));
        }

        newExampleBinary.setVars(vars);

        ExampleResult result = restTemplate.postForObject(problemRequest.getEndpoint(), newExampleBinary, ExampleResult.class);

        // Set the Result
        for (int i = 0; i < newExampleBinary.getNumberOfObjectives(); i++) {
            binarySolution.setObjective(i,Double.parseDouble(result.getObjectives().get(i).getValue()));
        }
    }

    public ProblemRequest getProblem(){
        return this.problemRequest;
    }


}
