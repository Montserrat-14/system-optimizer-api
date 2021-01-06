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
    private List<Integer> bitsPerVariable;

    public void createProblem(ProblemRequest problemRequest) {

        this.problemRequest = problemRequest;

        setName(problemRequest.getName());
        setNumberOfVariables(problemRequest.getListOfVariables().size());
        setNumberOfObjectives(problemRequest.getnObjectives());

        this.bitsPerVariable = new ArrayList<>();

        for (int var = 0; var < problemRequest.getListOfVariables().size(); var++) {
            this.bitsPerVariable.add(problemRequest.getListOfVariables().get(var).getBits());
        }

    }

    public List<Integer> getListOfBitsPerVariable() {
        return this.bitsPerVariable;
    }

    public void evaluate(BinarySolution binarySolution) {

        List<Vars> vars = new ArrayList<>();
        for (int i = 0; i < binarySolution.getNumberOfVariables(); i++) {
            vars.add(new Vars(binarySolution.getVariable(i).toString()));
        }

        // Create the Rest Template
        RestTemplate restTemplate = new RestTemplate();
        Example newExampleBinary = new Example();

        newExampleBinary.setNumberOfObjectives(getNumberOfObjectives());
        newExampleBinary.setVars(vars);

        ExampleResult result = restTemplate.postForObject(problemRequest.getEndpoint(), newExampleBinary, ExampleResult.class);

        // Set the Result
        for (int i = 0; i < binarySolution.getNumberOfObjectives(); i++) {
            binarySolution.setObjective(i, Double.parseDouble(result.getObjectives().get(i).getValue()));
        }
    }

    public ProblemRequest getProblem(){
        return this.problemRequest;
    }


}
