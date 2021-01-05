package com.montserrat14.systemoptimizer.model.problem.generic;

import com.montserrat14.systemoptimizer.example.model.Example;
import com.montserrat14.systemoptimizer.example.model.ExampleResult;
import com.montserrat14.systemoptimizer.example.model.Vars;
import com.montserrat14.systemoptimizer.model.problem.ProblemRequest;
import com.montserrat14.systemoptimizer.model.problem.factory.Problems;
import org.springframework.web.client.RestTemplate;
import org.uma.jmetal.problem.doubleproblem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;

import java.util.ArrayList;
import java.util.List;

public class DoubleProblem extends AbstractDoubleProblem implements Problems {

    private ProblemRequest problemRequest;

    @Override
    public void createProblem(ProblemRequest problemRequest) {

        this.problemRequest = problemRequest;

        setName(problemRequest.getName());

        setNumberOfVariables(problemRequest.getListOfVariables().size());
        setNumberOfObjectives(problemRequest.getnObjectives());

        List<Double> lowerLimit = new ArrayList<>(getNumberOfVariables());
        List<Double> upperLimit = new ArrayList<>(getNumberOfVariables());

        for (int i = 0; i < getNumberOfVariables(); i++) {
            lowerLimit.add(Double.valueOf(problemRequest.getListOfVariables().get(i).getMin()));
            upperLimit.add(Double.valueOf(problemRequest.getListOfVariables().get(i).getMax()));
        }

        setVariableBounds(lowerLimit, upperLimit);

    }

    @Override
    public void evaluate(DoubleSolution doubleSolution) {

        double[] x = new double[getNumberOfVariables()];
        for (int i = 0; i < doubleSolution.getNumberOfVariables(); i++) {
            x[i] = doubleSolution.getVariable(i);
        }

        // Create the Rest Template
        RestTemplate restTemplate = new RestTemplate();
        Example newExampleDouble = new Example();

        newExampleDouble.setNumberOfObjectives(getNumberOfObjectives());

        List<Vars> vars = new ArrayList<>();
        for (int i = 0; i < doubleSolution.getNumberOfVariables(); i++) {
            vars.add(new Vars(String.valueOf(x[i])));
        }

        newExampleDouble.setVars(vars);

       ExampleResult result = restTemplate.postForObject(problemRequest.getEndpoint(), newExampleDouble, ExampleResult.class);

        // Set the Result
        for (int i = 0; i < doubleSolution.getNumberOfObjectives(); i++) {
            doubleSolution.setObjective(i, Double.parseDouble(result.getObjectives().get(i).getValue()));
        }



    }

    public ProblemRequest getProblem() {
        return this.problemRequest;
    }
}
