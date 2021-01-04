package com.montserrat14.systemoptimizer.algorithms;

import com.montserrat14.systemoptimizer.model.problem.factory.Problems;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ResultListOutput extends SolutionListOutput {
    private Problems problem;
    private static final String VARFILENAME = "VAR";
    private static final String FUNFILENAME = "FUN";
    private List<? extends Solution<?>> solutionList;
    private final HashMap<String, Object> resultsPayload;

    public ResultListOutput(Algorithm<List<? extends Solution<?>>> algorithm, Problems problem) {
        super(algorithm.getResult());
        this.solutionList = algorithm.getResult();
        this.problem = problem;
        this.setVarFileOutputContext(new DefaultFileOutputContext(System.getenv("RESULTSPATH")
                + File.separator + ResultListOutput.VARFILENAME + this.problem.getProblem().getId() + System.getenv("RESULTSEXTENSION")));
        this.setFunFileOutputContext(new DefaultFileOutputContext(System.getenv("RESULTSPATH")
                + File.separator + ResultListOutput.FUNFILENAME + this.problem.getProblem().getId()+ System.getenv("RESULTSEXTENSION")));
        this.resultsPayload = new HashMap<>();
        this.resultsPayload.put("id", this.problem.getProblem().getId());
    }

    public HashMap<String, Object> getResultsPayload() {
        ArrayList<HashMap<String, ArrayList<HashMap<String, Object>>>> results = new ArrayList<>();


        if(solutionList.size() > 0) {
            for (int i = 0; i < solutionList.size(); i++) {
                HashMap<String, ArrayList<HashMap<String, Object>>> currResult = new HashMap<>();
                currResult.put("solution", getVariablesList(solutionList, i));
                currResult.put("objective", getObjectivesList(solutionList, i));
                results.add(currResult);
            }
        }

        resultsPayload.put("results", results);

        return resultsPayload;
    }

    private ArrayList<HashMap<String, Object>> getVariablesList(List<? extends Solution<?>> solutionList, Integer index) {
        ArrayList<HashMap<String, Object>> variables = new ArrayList<>();


        int numberOfVariables = solutionList.get(index).getNumberOfVariables();

        for(int i = 0; i < numberOfVariables; i++) {
            HashMap<String, Object> variableObject = new HashMap<>();
            variableObject.put("name", this.problem.getProblem().getListOfVariables().get(i).getName());
            variableObject.put("value", solutionList.get(index).getVariable(i));
            variables.add(variableObject);
        }

        return variables;
    }

    private ArrayList<HashMap<String, Object>> getObjectivesList(List<? extends Solution<?>> solutionList, Integer index) {
        ArrayList<HashMap<String, Object>> objectives = new ArrayList<>();
        HashMap<String, Object> objectiveObject = new HashMap<>();

        int numberOfObjectives = solutionList.get(0).getNumberOfObjectives();

        for(int i = 0; i < numberOfObjectives; i++) {
            objectiveObject.clear();
            objectiveObject.put("value", solutionList.get(index).getObjective(i));
            objectives.add(objectiveObject);
        }

        return objectives;
    }


}
