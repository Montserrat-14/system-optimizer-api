package com.montserrat14.systemoptimizer.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.montserrat14.systemoptimizer.model.Problem;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
public class ProblemController {

    ObjectMapper objectMapper = new ObjectMapper();

    Map<Integer,Problem> list = new HashMap<>();

    @RequestMapping(
            value = "/problem",
            //method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public void postBody(@RequestBody String problemRequest) throws JsonProcessingException {

        Problem problem = objectMapper.readValue(problemRequest,Problem.class);
        list.put(problem.getId(),problem);

        System.out.println(problem.getName());

    }

    @PutMapping(value="/update/{id}")
    public void updateProblemName(@PathVariable("id") Integer id, @RequestParam String name){
        Problem problem = list.get(id);

        problem.setName(name);

        list.put(id,problem);
    }

    @GetMapping("/getProblem")
    public Map<Integer,Problem> getProblem(){
        return list;
    }

}
