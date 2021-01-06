package com.montserrat14.systemoptimizer.algorithms;

import com.montserrat14.systemoptimizer.exception.AlgorithmsException;
import com.montserrat14.systemoptimizer.exception.SystemOptimizerException;
import com.montserrat14.systemoptimizer.model.problem.factory.Problems;
import com.montserrat14.systemoptimizer.utils.TimeUtils;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.AlgorithmBuilder;
import org.uma.jmetal.operator.crossover.CrossoverOperator;
import org.uma.jmetal.operator.mutation.MutationOperator;
import org.uma.jmetal.operator.selection.SelectionOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class AlgorithmGenericBuilder {

    private Class algorithmToInstatiate;
    private List<Constructor> constructorsToTryInstatiation;
    private Problems problem;
    private Object algorithmInstance;
    private Algorithm algorithm;
    private HashMap<String, Object> results;
    private Method setMaxEvaluations;

    private Double evaluationTimeExecution;

    public AlgorithmGenericBuilder setProblem(Problems problem) {
        this.problem = problem;
        return this;
    }

    public AlgorithmGenericBuilder findSpecificAlgorithm(String algorithmName) {
        this.algorithmToInstatiate = AlgorithmsUtils.getAlgorithmClass(algorithmName);
        return this;
    }

    public AlgorithmGenericBuilder setConstructors() throws AlgorithmsException {
        if (algorithmToInstatiate == null) {
            throw new AlgorithmsException("No algorithm to instantiate");
        }

        this.constructorsToTryInstatiation = Arrays.stream(algorithmToInstatiate.getDeclaredConstructors())
                .sorted(Comparator.comparingInt(Constructor::getParameterCount))
                .collect(Collectors.toList());
        return this;
    }

    public AlgorithmGenericBuilder getMaxMethod() throws AlgorithmsException, SystemOptimizerException {

       this.setMaxEvaluations = AlgorithmsUtils.getMaxMethod(this.algorithmToInstatiate);

        return this;
    }

    public AlgorithmGenericBuilder setMaxEvals(int maxValue) throws InvocationTargetException, IllegalAccessException {

        this.setMaxEvaluations.invoke(algorithmInstance,maxValue);

        return this;
    }

    public AlgorithmGenericBuilder setInstance() throws AlgorithmsException {
        if (this.algorithmToInstatiate == null || this.constructorsToTryInstatiation == null) {
            throw new AlgorithmsException("No algorithm to instantiate");
        }

        HashMap<Class, Object> paramsByType = AlgorithmsUtils.getDefaultParams((Problem) problem);

        for (Constructor<?> iterableConstructor : this.constructorsToTryInstatiation) {
            Class<?>[] paramTypes = iterableConstructor.getParameterTypes();
            Integer nParams = iterableConstructor.getParameterCount();
            Object[] paramsToConstructor = new Object[nParams];

            for (Integer i = 0; i < nParams; i++) {
                paramsToConstructor[i] = paramsByType.get(paramTypes[i]);
            }

            try {
                Object instance = iterableConstructor.newInstance(paramsToConstructor);
                this.algorithmInstance = instance;

                if(nParams == 1){
                    return this.setParams(paramsByType);
                }

                return this;
            } catch (InstantiationException e) {
                System.err.println(e.getMessage());
            } catch (IllegalAccessException e) {
                System.err.println(e.getMessage());
            } catch (InvocationTargetException e) {
                System.err.println(e.getMessage());
            }
        }

        throw new AlgorithmsException("No algorithm instance");
    }

    private AlgorithmGenericBuilder setParams(HashMap<Class, Object>  parameterTypes) {
        try{
            Method crossoverMethod = AlgorithmsUtils.getMethodOnAlgorithmBuilder("setCrossoverOperator", this.algorithmToInstatiate, CrossoverOperator.class);
            crossoverMethod.invoke(this.algorithmInstance,parameterTypes.get(CrossoverOperator.class));

            Method mutationMethod =  AlgorithmsUtils.getMethodOnAlgorithmBuilder("setMutationOperator", this.algorithmToInstatiate, MutationOperator.class);
            mutationMethod.invoke(this.algorithmInstance, parameterTypes.get(MutationOperator.class));

            Method selectionMethod =  AlgorithmsUtils.getMethodOnAlgorithmBuilder("setSelectionOperator", this.algorithmToInstatiate, SelectionOperator.class);
            selectionMethod.invoke(this.algorithmInstance, parameterTypes.get(SelectionOperator.class));

            Method evaluatorMethod =  AlgorithmsUtils.getMethodOnAlgorithmBuilder("setSolutionListEvaluator", this.algorithmToInstatiate, SolutionListEvaluator.class);
            evaluatorMethod.invoke(this.algorithmInstance, parameterTypes.get(SolutionListEvaluator.class));

        } catch(Exception e){
            System.err.println("It was not possible to set operators");
        }


        return this;
    }

    public AlgorithmGenericBuilder runAlgorithm() throws Exception {

        if (this.algorithmInstance instanceof AlgorithmBuilder) {
            this.algorithm = ((AlgorithmBuilder<?>) this.algorithmInstance).build();
        } else if (this.algorithmInstance instanceof Algorithm) {
            this.algorithm = (Algorithm<?>) this.algorithmInstance;
        }

        TimeUtils.timeStart();
        this.algorithm.run();
        evaluationTimeExecution = TimeUtils.timeEnd();

        return this;
    }

    public AlgorithmGenericBuilder getResults() throws Exception {
        ResultListOutput resultsOutput = new ResultListOutput(this.algorithm, problem);
        results = resultsOutput.getResultsPayload();
        resultsOutput.print();
        return this;
    }

    public HashMap<String, Object> getResponse() {
        return results;
    }

    public Double getLastTimeExecution() {
        return evaluationTimeExecution;
    }

}
