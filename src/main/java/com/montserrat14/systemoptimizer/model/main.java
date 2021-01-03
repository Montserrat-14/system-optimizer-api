package com.montserrat14.systemoptimizer.model;

import com.montserrat14.systemoptimizer.model.problem.Problem;
import com.montserrat14.systemoptimizer.model.problem.Variables;
import com.montserrat14.systemoptimizer.model.problem.factory.ProblemTypes;
import com.montserrat14.systemoptimizer.model.problem.factory.Problems;
import com.montserrat14.systemoptimizer.model.problem.factory.ProblemFactory;
import com.montserrat14.systemoptimizer.model.problem.generic.IntegerProblem;

public class main {

    public static void main(String args[]){

        //Factory
        ProblemFactory problemFactory = new ProblemFactory();

        //Instantiate generic problem
        Problem genericProblem = new Problem(22,"Problem Integer","Description",2,"//end point","payload",60);

        //Call factory for a specific type of Problem
        Problems problemInt =  problemFactory.getProblem(ProblemTypes.INTEGER.getType());

        //Create problem
        problemInt.createProblem(genericProblem);

        System.out.println(problemInt.getClass().toString());


    }
}
