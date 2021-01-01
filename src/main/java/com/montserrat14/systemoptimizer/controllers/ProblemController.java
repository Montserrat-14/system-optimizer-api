package com.montserrat14.systemoptimizer.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.montserrat14.systemoptimizer.SwrlAPI;
import com.montserrat14.systemoptimizer.model.Problem;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.*;

@CrossOrigin(origins = "http://localhost:4200")
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

        listOfAlgorithms = SwrlAPI.getAlgorithms(problem.getnObjectives());

        System.out.println("New Problem Added: " + problem.getName());
        System.out.println(listOfAlgorithms.toString());


        ArrayList<HashMap<String,ArrayList<HashMap<String, Object>>>> results = new ArrayList<>();
        results.add(generateResult(2));
        results.add(generateResult(2));

        HashMap<String, Object> resp = new HashMap<>();
        resp.put("id", r.nextInt(1000) + 1);
        resp.put("results", results);

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    private HashMap<String,ArrayList<HashMap<String, Object>>> generateResult(int nObjectives) {
        HashMap<String,ArrayList<HashMap<String, Object>>> result = new HashMap<>();
        result.put("solution", generateSolutionList(nObjectives));
        result.put("objective", generateObjectiveList(nObjectives));

        return result;
    }

    private ArrayList<HashMap<String, Object>> generateSolutionList(int n) {
        ArrayList<HashMap<String, Object>> result = new ArrayList<>();
        HashMap<String, Object> variable;
        Random r = new Random();
        double randomValue;

        for(int i = 0; i < 2; i++) {
            randomValue = -100 + (100 - (-100)) * r.nextDouble();
            variable = new HashMap<>();

            variable.put("name", "var" + i);
            variable.put("value", randomValue);
            result.add(variable);
        }

        return result;
    }

    private ArrayList<HashMap<String, Object>> generateObjectiveList(int n) {
        ArrayList<HashMap<String, Object>> result = new ArrayList<>();
        HashMap<String, Object> variable;
        Random r = new Random();
        double randomValue;

        for(int i = 0; i < 2; i++) {
            randomValue = -100 + (100 - (-100)) * r.nextDouble();
            variable = new HashMap<>();

            variable.put("value", randomValue);
            result.add(variable);
        }

        return result;
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
