package com.montserrat14.systemoptimizer.util;

import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.fileoutput.FileOutputContext;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;

import java.util.List;

public class ResultListOutput extends SolutionListOutput{
    private FileOutputContext varFileContext;
    private FileOutputContext funFileContext;
    private String varFileName = "VAR";
    private String funFileName = "FUN";
    private List<? extends Solution<?>> solutionList;


    public ResultListOutput(List<? extends Solution<?>> solutionList, Integer problemId) {
        super(solutionList);
        this.varFileName += problemId;
        this.funFileName += problemId;
        this.setVarFileOutputContext(new DefaultFileOutputContext(varFileName));
        this.setFunFileOutputContext(new DefaultFileOutputContext(funFileName));
    }


}
