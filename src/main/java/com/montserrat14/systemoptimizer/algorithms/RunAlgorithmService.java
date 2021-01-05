package com.montserrat14.systemoptimizer.algorithms;

import com.montserrat14.systemoptimizer.exception.AlgorithmsException;
import com.montserrat14.systemoptimizer.exception.SystemOptimizerException;
import com.montserrat14.systemoptimizer.knowledgeBase.SwrlSingleton;
import com.montserrat14.systemoptimizer.model.problem.ProblemRequest;
import com.montserrat14.systemoptimizer.model.problem.factory.ProblemFactory;
import com.montserrat14.systemoptimizer.model.problem.factory.Problems;

import java.util.ArrayList;
import java.util.HashMap;

public class RunAlgorithmService {

    private static AlgorithmGenericBuilder algBuilder = new AlgorithmGenericBuilder();

    public static HashMap<String, Object> run(ProblemRequest problemRequest) throws AlgorithmsException, SystemOptimizerException {

        ArrayList<String> listOfAlgorithms = getAlgorithms(problemRequest);

        ProblemFactory problemFactory = new ProblemFactory();
        Problems newProblem = problemFactory.getProblem(problemRequest);

        algBuilder.setProblem(newProblem);

        boolean isRun = false;

        for (String algName : listOfAlgorithms) {
            isRun = startAlgorithm(algName,newProblem.getProblem().getDuration());
            if(isRun){
                break;
            }
        }

        if(!isRun){
            throw new AlgorithmsException("We cannot run any algorithm");
        }

        return algBuilder.getResponse();
    }

    private static boolean startAlgorithm(String algName, int problemTime) {

        try {
            double evaluationTime = algBuilder.findSpecificAlgorithm(algName).setConstructors().setInstance()
                    .getMaxMethod().setMaxEvals(1)
                    .runAlgorithm().getLastTimeExecution();

            double problemTimeInSeconds = problemTime * 60;
            int iterations = (int)(evaluationTime / problemTimeInSeconds);

            System.out.println("Iterations: " + iterations);

            algBuilder.setMaxEvals(iterations).runAlgorithm().getResults();

            return true;
        } catch (Exception e) {
           return false;
        }
    }

    private static ArrayList<String> getAlgorithms(ProblemRequest problemRequest) throws AlgorithmsException {

        ArrayList<String> listOfAlgorithms;

        SwrlSingleton swrl = SwrlSingleton.getInstance();
        swrl.createOntology();
        listOfAlgorithms = swrl.getAlgorithms(problemRequest.getnObjectives());

        if (listOfAlgorithms.size() == 0) {
            throw new AlgorithmsException("No Algorithm was found");
        }

        return listOfAlgorithms;
    }
}
