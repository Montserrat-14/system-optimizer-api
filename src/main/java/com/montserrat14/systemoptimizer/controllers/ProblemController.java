package com.montserrat14.systemoptimizer.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.montserrat14.systemoptimizer.algorithms.AlgorithmGenericBuilder;
import com.montserrat14.systemoptimizer.algorithms.RunAlgorithmService;
import com.montserrat14.systemoptimizer.exception.AlgorithmsException;
import com.montserrat14.systemoptimizer.exception.SystemOptimizerException;
import com.montserrat14.systemoptimizer.knowledgeBase.SwrlSingleton;
import com.montserrat14.systemoptimizer.model.problem.ProblemRequest;
import com.montserrat14.systemoptimizer.model.problem.factory.ProblemFactory;
import com.montserrat14.systemoptimizer.model.problem.factory.Problems;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000", "http://localhost:80"})
@RestController
public class ProblemController {

    ObjectMapper objectMapper = new ObjectMapper();
    Random r = new Random();

    Map<Integer, ProblemRequest> problems = new HashMap<>();

    @RequestMapping(
            value = "/problem",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> postBody(@RequestBody String request) throws JsonProcessingException, AlgorithmsException, SystemOptimizerException {

        if (request.isEmpty()) {
            return new ResponseEntity<>("Empty Body", HttpStatus.NOT_ACCEPTABLE);
        }

        try {
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            ProblemRequest problem = objectMapper.readValue(request, ProblemRequest.class);
            problem.setId(r.nextInt(10000) + 1);
            problems.put(problem.getId(), problem);

            if (problem.getnObjectives() == null) {
                throw new SystemOptimizerException("There are no Objectives");
            }

            return new ResponseEntity<>(RunAlgorithmService.run(problem), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
