package com.montserrat14.systemoptimizer.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.montserrat14.systemoptimizer.algorithms.AlgorithmGenericBuilder;
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
            //method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> postBody(@RequestBody String problemRequest) throws JsonProcessingException {

        if(problemRequest.isEmpty()) {
            return new ResponseEntity<>("Body Vazio", HttpStatus.NOT_ACCEPTABLE);
        }
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        ProblemRequest problem = objectMapper.readValue(problemRequest, ProblemRequest.class);

        problem.setId(r.nextInt(1000) + 1);
        problems.put(problem.getId(),problem);

        ArrayList<String> listOfAlgorithms = new ArrayList<String>();
        if (problem.getnObjectives() == null) {
            return new ResponseEntity<>("Erro Interno", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        SwrlSingleton swrl = SwrlSingleton.getInstance();
        swrl.createOntology();
        listOfAlgorithms = swrl.getAlgorithms(problem.getnObjectives());

        if(listOfAlgorithms.size() == 0 ) {
        //TODO: Some error
        }

        ProblemFactory problemFactory = new ProblemFactory();
        Problems newProblem = problemFactory.getProblem(problem);

        AlgorithmGenericBuilder algBuilder = new AlgorithmGenericBuilder();
        algBuilder.setProblem(newProblem);

        for(String algName :  listOfAlgorithms){
            try {
                algBuilder.findSpecificAlgorithm(algName).setConstructors().setInstance().runAlgorithm().getResults();
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return new ResponseEntity<>(algBuilder.getResponse(), HttpStatus.OK);
    }

    @PutMapping(value="/problem/{id}")
    public void updateProblemName(@PathVariable("id") Integer id, @RequestParam String name){
        ProblemRequest problemRequest = problems.get(id);

        problemRequest.setName(name);

        problems.put(id, problemRequest);
    }

    @GetMapping("/problem")
    public ResponseEntity<Map<Integer, ProblemRequest>> getProblem(){
        return new ResponseEntity<>(problems, HttpStatus.OK);
    }

}
