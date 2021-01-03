package com.montserrat14.systemoptimizer.algorithms;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.AlgorithmBuilder;
import org.uma.jmetal.problem.Problem;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class AlgorithmGenericBuilder {

    private Class algorithmToInstatiate;
    private List<Constructor<?>> constructorsToTryInstatiation;
    private Problem problem;
    private Object algorithmInstance;
    private Algorithm algorithm;

    public AlgorithmGenericBuilder findSpecificAlgorithm(String algorithmName){
        this.algorithmToInstatiate =  AlgorithmsUtils.getAlgorithmClass(algorithmName);
        return this;
    }

    public AlgorithmGenericBuilder setConstructors() throws Exception {
        if(algorithmToInstatiate == null){
            throw new Exception("No algorithm to instatiate");
        }

        this.constructorsToTryInstatiation = Arrays.stream(algorithmToInstatiate.getDeclaredConstructors())
                .sorted(Comparator.comparingInt(Constructor::getParameterCount))
                .collect(Collectors.toList());

        return this;
    }

    public AlgorithmGenericBuilder setInstance() throws Exception {
        if(this.algorithmToInstatiate == null || this.constructorsToTryInstatiation == null){
            throw new Exception("No algorithm to instatiate");
        }

        HashMap<Class, Object> paramsByType = AlgorithmsUtils.getDefaultParams(problem);

        for(Constructor<?> iterableConstructor :  this.constructorsToTryInstatiation){
            Class<?>[] paramTypes = iterableConstructor.getParameterTypes();
            Integer nParams = iterableConstructor.getParameterCount();
            Object[] paramsToConstructor = new Object[nParams];

            for(Integer i=0; i<nParams; i++){
                paramsToConstructor[i] = paramsByType.get(paramTypes[i]);
            }

            try {
                Object instance = iterableConstructor.newInstance(paramsToConstructor);
                this.algorithmInstance =  instance;
                return this;
            } catch (InstantiationException e) {
                System.err.println(e.getMessage());
            } catch (IllegalAccessException e) {
                System.err.println(e.getMessage());
            } catch (InvocationTargetException e) {
                System.err.println(e.getMessage());
            }
        }

        throw new Exception("No algorithm instance");
    }

    public AlgorithmGenericBuilder runAlgorithm() throws Exception {

        if(this.algorithmInstance instanceof AlgorithmBuilder){
           this.algorithm = ((AlgorithmBuilder<?>) this.algorithmInstance).build();
        } else if(this.algorithmInstance instanceof Algorithm){
            this.algorithm = (Algorithm<?>) this.algorithmInstance;
        }

        this.algorithm.run();

        return this;
    }

    public AlgorithmGenericBuilder getResults() throws Exception {
        //TODO: Call Diogo Class To get results

        return this;
    }

}
