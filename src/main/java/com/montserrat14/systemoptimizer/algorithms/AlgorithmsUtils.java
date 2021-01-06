package com.montserrat14.systemoptimizer.algorithms;

import com.montserrat14.systemoptimizer.exception.SystemOptimizerException;
import org.reflections.Reflections;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.AlgorithmBuilder;
import org.uma.jmetal.operator.crossover.CrossoverOperator;
import org.uma.jmetal.operator.crossover.impl.IntegerSBXCrossover;
import org.uma.jmetal.operator.crossover.impl.SBXCrossover;
import org.uma.jmetal.operator.crossover.impl.SinglePointCrossover;
import org.uma.jmetal.operator.mutation.MutationOperator;
import org.uma.jmetal.operator.mutation.impl.BitFlipMutation;
import org.uma.jmetal.operator.mutation.impl.IntegerPolynomialMutation;
import org.uma.jmetal.operator.mutation.impl.PolynomialMutation;
import org.uma.jmetal.operator.selection.SelectionOperator;
import org.uma.jmetal.operator.selection.impl.BinaryTournamentSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.binaryproblem.impl.AbstractBinaryProblem;
import org.uma.jmetal.problem.integerproblem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.solution.binarysolution.BinarySolution;
import org.uma.jmetal.util.comparator.DominanceComparator;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;

public class AlgorithmsUtils {

    private static final String MULTIOBJECTIVE_PACKAGE_PATH =  "org.uma.jmetal.algorithm.multiobjective.";
    private static final String BUILDER_CLASS_NAME = "Builder";

    public static Class<?> getAlgorithmClass(String algName){
        Reflections reflections = new Reflections(MULTIOBJECTIVE_PACKAGE_PATH + algName);

        Set<Class<? extends AlgorithmBuilder>> allBuilderClasses = reflections.getSubTypesOf(AlgorithmBuilder.class);

        for(Class<?> iterableClass : allBuilderClasses){
            if(iterableClass.getName().equalsIgnoreCase( MULTIOBJECTIVE_PACKAGE_PATH + algName + "." + algName + BUILDER_CLASS_NAME)){
                return iterableClass;
            }
        }

        Set<Class<? extends Algorithm>> allAlgorithmClasses = reflections.getSubTypesOf(Algorithm.class);

        for(Class<?> iterableClass : allAlgorithmClasses){
            if(iterableClass.getName().equalsIgnoreCase( MULTIOBJECTIVE_PACKAGE_PATH + algName + "." + algName)){
                return iterableClass;
            }
        }

        return null;
    }

    public static HashMap<Class, Object> getDefaultParams(Problem problem) {

        HashMap<Class, Object> defaultParamsMap = new HashMap<>();

        defaultParamsMap.put(Problem.class, problem);
        defaultParamsMap.put(int.class, Integer.parseInt(System.getenv("INTEGER_DEFAULT_PARAM")));
        defaultParamsMap.put(Integer.class, Integer.parseInt(System.getenv("INTEGER_DEFAULT_PARAM")));
        defaultParamsMap.put(Double.class, Double.parseDouble(System.getenv("DOUBLE_DEFAULT_PARAM")));
        defaultParamsMap.put(double.class, Double.parseDouble(System.getenv("DOUBLE_DEFAULT_PARAM")));
        defaultParamsMap.put(Float.class, Float.parseFloat(System.getenv("FLOAT_DEFAULT_PARAM")));
        defaultParamsMap.put(float.class, Float.parseFloat(System.getenv("FLOAT_DEFAULT_PARAM")));
        defaultParamsMap.put(CrossoverOperator.class, getCrossoverOperator(problem));
        defaultParamsMap.put(MutationOperator.class, getMutationOperator(problem));
        defaultParamsMap.put(SelectionOperator.class, new BinaryTournamentSelection<BinarySolution>());
        defaultParamsMap.put(SolutionListEvaluator.class, new SequentialSolutionListEvaluator<Solution<?>>());
        defaultParamsMap.put(Comparator.class, new DominanceComparator<>());

        return defaultParamsMap;
    }

    private static MutationOperator getMutationOperator (Problem problem){

        if(problem instanceof AbstractIntegerProblem){
            return new IntegerPolynomialMutation(1/problem.getNumberOfVariables(), 20);
        } else if (problem instanceof AbstractBinaryProblem){
            return new BitFlipMutation(1/ ((AbstractBinaryProblem) problem).getTotalNumberOfBits());
        }
        return new PolynomialMutation(1/problem.getNumberOfVariables(), 20);
    }

    private static CrossoverOperator getCrossoverOperator (Problem problem){

        if(problem instanceof AbstractIntegerProblem){
            return new IntegerSBXCrossover(0.9, 20.0);
        } else if(problem instanceof AbstractBinaryProblem){
            return new SinglePointCrossover(0.9);
        }
        return new SBXCrossover(0.9, 20.0);
    }

    public static Method getMaxMethod(Class c) throws SystemOptimizerException {

        if (c == null) {
            throw new SystemOptimizerException("Class invalid");
        }

        Method method = null;

        try{
            method = findMethod(c,"setMaxEvaluations", int.class);
        }catch (NoSuchMethodException e){
            try{
                method = findMethod(c,"setMaxIterations", int.class);
            } catch(NoSuchMethodException secondException){
                throw new SystemOptimizerException("There is no method like setMaxEvaluations or setMaxIterations");
            }
        }
        return method;
    }

    public static Method getMethodOnAlgorithmBuilder(String name, Class c, Class type) throws SystemOptimizerException {

        if (c == null) {
            throw new SystemOptimizerException("Class invalid");
        }

        Method method = null;

        try{
            method = findMethod(c,name, type);
        }catch (NoSuchMethodException e){
            throw new SystemOptimizerException("There is not" + name + "method");
        }
        return method;
    }


    private static Method findMethod(Class c, String name, Class type) throws NoSuchMethodException {
        return  c.getMethod(name, type);
    }
}
