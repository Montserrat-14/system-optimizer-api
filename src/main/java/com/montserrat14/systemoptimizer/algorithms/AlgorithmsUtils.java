package com.montserrat14.systemoptimizer.algorithms;

import com.montserrat14.systemoptimizer.exception.AlgorithmsException;
import com.montserrat14.systemoptimizer.exception.SystemOptimizerException;
import org.jetbrains.annotations.Nullable;
import org.reflections.Reflections;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.AlgorithmBuilder;
import org.uma.jmetal.operator.crossover.CrossoverOperator;
import org.uma.jmetal.operator.crossover.impl.SBXCrossover;
import org.uma.jmetal.operator.mutation.MutationOperator;
import org.uma.jmetal.operator.mutation.impl.PolynomialMutation;
import org.uma.jmetal.operator.selection.SelectionOperator;
import org.uma.jmetal.operator.selection.impl.BinaryTournamentSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.multiobjective.Kursawe;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.comparator.DominanceComparator;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;
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

        //TODO: Change the type of Operators depending on Problem Type.


        HashMap<Class, Object> defaultParamsMap = new HashMap<>();

        defaultParamsMap.put(Problem.class, problem);
        defaultParamsMap.put(int.class, System.getenv("INTEGER_DEFAULT_PARAM"));
        defaultParamsMap.put(Integer.class, System.getenv("INTEGER_DEFAULT_PARAM"));
        defaultParamsMap.put(Double.class, System.getenv("DOUBLE_DEFAULT_PARAM"));
        defaultParamsMap.put(double.class, System.getenv("DOUBLE_DEFAULT_PARAM"));
        defaultParamsMap.put(Float.class, System.getenv("FLOAT_DEFAULT_PARAM"));
        defaultParamsMap.put(float.class, System.getenv("FLOAT_DEFAULT_PARAM"));
        defaultParamsMap.put(CrossoverOperator.class, new SBXCrossover(0.9, 20.0));
        defaultParamsMap.put(MutationOperator.class, new PolynomialMutation(1/problem.getNumberOfVariables(), 20));
        defaultParamsMap.put(SelectionOperator.class, new BinaryTournamentSelection<>(new RankingAndCrowdingDistanceComparator<>()));
        defaultParamsMap.put(SolutionListEvaluator.class, new SequentialSolutionListEvaluator<Solution<?>>());
        defaultParamsMap.put(Comparator.class, new DominanceComparator<>());

        return defaultParamsMap;
    }

    public static Method getMaxMethod(Class c) throws SystemOptimizerException {

        if (c == null) {
            throw new SystemOptimizerException("Class invalid");
        }

        Method method = null;

        try{
                method = findMethod(c,"setMaxEvaluations");
            if(method == null){
                method = findMethod(c,"setMaxIterations");
            }
        }catch (NoSuchMethodException e){
            throw new SystemOptimizerException("There is no method like setMaxEvaluations or setMaxIterations");
        }
        return method;
    }

    private static Method findMethod(Class c, String name) throws NoSuchMethodException {
        return  c.getMethod(name,int.class);
    }
}
