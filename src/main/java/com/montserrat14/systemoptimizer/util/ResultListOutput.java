package com.montserrat14.systemoptimizer.util;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.fileoutput.FileOutputContext;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ResultListOutput extends SolutionListOutput {
    private static final String VARFILENAME = "VAR";
    private static final String FUNFILENAME = "FUN";
    private List<? extends Solution<?>> solutionList;
    private final HashMap<String, Object> resultsPayload;

    public ResultListOutput(Algorithm<List<? extends Solution<?>>> algorithm, Integer problemId) {
        super(algorithm.getResult());
        this.setVarFileOutputContext(new DefaultFileOutputContext(System.getenv("RESULTSPATH")
                + File.separator + ResultListOutput.VARFILENAME + problemId + System.getenv("RESULTSEXTENSION")));
        this.setFunFileOutputContext(new DefaultFileOutputContext(System.getenv("RESULTSPATH")
                + File.separator + ResultListOutput.FUNFILENAME + problemId+ System.getenv("RESULTSEXTENSION")));
        this.resultsPayload = new HashMap<>();
        this.resultsPayload.put("id", problemId);
    }

    public HashMap<String, Object> getResultsPayload() {
        ArrayList<HashMap<String, ArrayList<HashMap<String, Object>>>> results = new ArrayList<>();
        HashMap<String, ArrayList<HashMap<String, Object>>> currResult = new HashMap<>();

        if(solutionList.size() > 0) {
            for (int i = 0; i < solutionList.size(); i++) {
                currResult.clear();
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
        HashMap<String, Object> variableObject = new HashMap<>();

        int numberOfVariables = solutionList.get(index).getNumberOfVariables();

        for(int i = 0; i < numberOfVariables; i++) {
            variableObject.clear();
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
