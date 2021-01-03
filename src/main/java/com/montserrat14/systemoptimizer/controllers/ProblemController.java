package com.montserrat14.systemoptimizer.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.montserrat14.systemoptimizer.model.Problem;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000", "http://localhost:80"})
@RestController
public class ProblemController {

    ObjectMapper objectMapper = new ObjectMapper();
    Random r = new Random();

    Map<Integer,Problem> problems = new HashMap<>();

    @RequestMapping(
            value = "/problem",
            //method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> postBody(@RequestBody String problemRequest) throws JsonProcessingException {

        if(problemRequest.isEmpty()) {
            return new ResponseEntity<>("Body Vazio", HttpStatus.NOT_ACCEPTABLE);
        }
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        Problem problem = objectMapper.readValue(problemRequest,Problem.class);

        problem.setId(r.nextInt(1000) + 1);
        problems.put(problem.getId(),problem);

        ArrayList<String> listOfAlgorithms = new ArrayList<String>();
        if (problem.getnObjectives() == null) {
            return new ResponseEntity<>("Erro Interno", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //listOfAlgorithms = SwrlAPI.getAlgorithms(problem.getnObjectives());

        System.out.println("New Problem Added: " + problem.getName());
        System.out.println(listOfAlgorithms.toString());

        return new ResponseEntity<>(problem, HttpStatus.OK);
    }

    @PutMapping(value="/problem/{id}")
    public void updateProblemName(@PathVariable("id") Integer id, @RequestParam String name){
        Problem problem = problems.get(id);

        problem.setName(name);

        problems.put(id,problem);
    }

    @GetMapping("/problem")
    public ResponseEntity<Map<Integer,Problem>> getProblem(){
        return new ResponseEntity<>(problems, HttpStatus.OK);
    }

}
