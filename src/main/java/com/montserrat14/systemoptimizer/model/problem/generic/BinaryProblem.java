package com.montserrat14.systemoptimizer.model.problem.generic;

import com.montserrat14.systemoptimizer.model.problem.Problem;
import com.montserrat14.systemoptimizer.model.problem.factory.Problems;
import org.uma.jmetal.problem.binaryproblem.impl.AbstractBinaryProblem;
import org.uma.jmetal.solution.binarysolution.BinarySolution;

import java.util.BitSet;
import java.util.List;

public class BinaryProblem extends AbstractBinaryProblem implements Problems {

    private int[] bitsPerVariable ;

    @Override
    public void createProblem(Problem problem) {

        setName(problem.getName());

        setNumberOfVariables(problem.getListOfVariables().size());
        setNumberOfObjectives(problem.getnObjectives());

        bitsPerVariable = new int[problem.getListOfVariables().size()] ;

        bitsPerVariable[0] = 30;
        for (int var = 1; var < problem.getListOfVariables().size(); var++) {
            bitsPerVariable[var] = 5;
        }

    }

    // We do not use this override method
    @Override
    public List<Integer> getListOfBitsPerVariable() {
        return null;
    }

    @Override
    public void evaluate(BinarySolution binarySolution) {
        int counterOnes;
        int counterZeroes;

        counterOnes = 0;
        counterZeroes = 0;

        BitSet bitset = binarySolution.getVariable(0) ;

        for (int i = 0; i < bitset.length(); i++) {
            if (bitset.get(i)) {
                counterOnes++;
            } else {
                counterZeroes++;
            }
        }

        // OneZeroMax is a maximization problem: multiply by -1 to minimize
        binarySolution.setObjective(0, -1.0 * counterOnes);
        binarySolution.setObjective(1, -1.0 * counterZeroes);
    }


}
