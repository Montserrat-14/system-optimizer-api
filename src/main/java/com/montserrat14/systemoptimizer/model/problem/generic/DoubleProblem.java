package com.montserrat14.systemoptimizer.model.problem.generic;

import com.montserrat14.systemoptimizer.example.model.Example;
import com.montserrat14.systemoptimizer.example.model.ExampleResult;
import com.montserrat14.systemoptimizer.example.model.Vars;
import com.montserrat14.systemoptimizer.model.problem.Problem;
import com.montserrat14.systemoptimizer.model.problem.factory.Problems;
import org.springframework.web.client.RestTemplate;
import org.uma.jmetal.problem.doubleproblem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;

import java.util.ArrayList;
import java.util.List;

public class DoubleProblem extends AbstractDoubleProblem implements Problems {

    private Problem problem;

    @Override
    public void createProblem(Problem problem) {

        this.problem = problem;

        setName(problem.getName());

        setNumberOfVariables(problem.getListOfVariables().size());
        setNumberOfObjectives(problem.getnObjectives());

        List<Double> lowerLimit = new ArrayList<>(getNumberOfVariables()) ;
        List<Double> upperLimit = new ArrayList<>(getNumberOfVariables()) ;

        for (int i = 0; i < getNumberOfVariables(); i++) {
            lowerLimit.add(Double.valueOf(problem.getListOfVariables().get(i).getMin()));
            upperLimit.add(Double.valueOf(problem.getListOfVariables().get(i).getMax()));
        }

        setVariableBounds(lowerLimit,upperLimit);

    }

    @Override
    public void evaluate(DoubleSolution doubleSolution) {

        double[] x = new double[getNumberOfVariables()];
        for (int i = 0; i < doubleSolution.getNumberOfVariables(); i++) {
            x[i] = doubleSolution.getVariable(i) ;
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

        ExampleResult result = restTemplate.postForObject(problem.getEndpoint(), newExampleDouble, ExampleResult.class);

        // Set the Result
        for (int i = 0; i < doubleSolution.getNumberOfVariables(); i++) {
            doubleSolution.setObjective(0, Double.parseDouble(result.getObjectives().get(i).getValue()));
        }

    }

}
