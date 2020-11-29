package com.montserrat14.systemoptimizer.controllers;

import com.montserrat14.systemoptimizer.model.Solution;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SolutionController {

    @GetMapping("/solution")
    public Solution solution() {
        return new Solution("Montserrat");
    }

}
