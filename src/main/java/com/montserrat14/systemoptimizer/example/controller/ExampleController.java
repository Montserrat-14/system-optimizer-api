package com.montserrat14.systemoptimizer.example.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.montserrat14.systemoptimizer.example.model.Example;
import com.montserrat14.systemoptimizer.example.model.ExampleResult;
import com.montserrat14.systemoptimizer.example.model.Vars;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

@RestController
public class ExampleController {

    ObjectMapper objectMapper = new ObjectMapper();


    @RequestMapping(
            value = "/client/example/double",
            //method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> exampleDouble(@RequestBody String exampleRequest) throws JsonProcessingException {

        if(exampleRequest.isEmpty()) {
            return new ResponseEntity<>("Empty Body", HttpStatus.NOT_ACCEPTABLE);
        }
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        Example example = objectMapper.readValue(exampleRequest, Example.class);

        ExampleResult exampleResult = getDoubleEvaluateExample(example);

        return new ResponseEntity<>(exampleResult, HttpStatus.OK);
    }

    /**
     * This code is a "replica" from Kursawe evaluate code
     * @param example
     * @return
     */
    private ExampleResult getDoubleEvaluateExample(Example example){
        double aux, xi, xj;
        double[] fx = new double[example.getNumberOfObjectives()];
        double[] x = new double[example.getVars().size()];
        for (int i = 0; i < example.getNumberOfObjectives(); i++) {
            x[i] = Double.parseDouble(example.getVars().get(i).getValue());
        }

        fx[0] = 0.0;
        for (int var = 0; var < example.getNumberOfObjectives() - 1; var++) {
            xi = x[var] * x[var];
            xj = x[var + 1] * x[var + 1];
            aux = (-0.2) * Math.sqrt(xi + xj);
            fx[0] += (-10.0) * Math.exp(aux);
        }

        fx[1] = 0.0;
        for (int var = 0; var < example.getNumberOfObjectives(); var++) {
            fx[1] += Math.pow(Math.abs(x[var]), 0.8) +
                    5.0 * Math.sin(Math.pow(x[var], 3.0));
        }

        ExampleResult exampleResult = new ExampleResult();
        List<Vars> resultVars = new ArrayList<>();
        resultVars.add(new Vars(String.valueOf(fx[0])));
        resultVars.add(new Vars(String.valueOf(fx[1])));
        exampleResult.setObjectives(resultVars);

        return exampleResult;
    }



    @RequestMapping(
            value = "/client/example/integer",
            //method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> exampleInteger(@RequestBody String exampleRequest) throws JsonProcessingException {

        if(exampleRequest.isEmpty()) {
            return new ResponseEntity<>("Empty Body", HttpStatus.NOT_ACCEPTABLE);
        }
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        Example example = objectMapper.readValue(exampleRequest, Example.class);

        ExampleResult exampleResult = getDoubleEvaluateExample(example);

        return new ResponseEntity<>(exampleResult, HttpStatus.OK);
    }


    /**
     *  This code is a "replica" from NMMin evaluate code
     * @param example
     * @return
     */
    private ExampleResult getIntegerEvaluateExample(Example example){
        int approximationToN = 0;
        int approximationToM = 0 ;
        int valueN = 100;
        int valueM = -100;

        for (int i = 0; i < example.getVars().size(); i++) {
            int value = Integer.parseInt(example.getVars().get(i).getValue());
            approximationToN += Math.abs(valueN - value) ;
            approximationToM += Math.abs(valueM - value) ;
        }
        ExampleResult exampleResult = new ExampleResult();
        List<Vars> resultVars = new ArrayList<>();
        resultVars.add(new Vars(String.valueOf(approximationToN)));
        resultVars.add(new Vars(String.valueOf(approximationToM)));
        exampleResult.setObjectives(resultVars);

        return exampleResult;
    }


    @RequestMapping(
            value = "/client/example/binary",
            //method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> exampleBinary(@RequestBody String exampleRequest) throws JsonProcessingException {

        if(exampleRequest.isEmpty()) {
            return new ResponseEntity<>("Empty Body", HttpStatus.NOT_ACCEPTABLE);
        }
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        Example example = objectMapper.readValue(exampleRequest, Example.class);

        ExampleResult exampleResult = getBinaryEvaluateExample(example);

        return new ResponseEntity<>(exampleResult, HttpStatus.OK);
    }


    /**
     *  This code is a "replica" from OneZeroMax evaluate code
     * @param example
     * @return
     */
    private ExampleResult getBinaryEvaluateExample(Example example){

        int counterOnes = 0;
        int counterZeroes = 0;

        BitSet bitset = BitSet.valueOf(example.getVars().get(0).getValue().getBytes());
        System.out.println(bitset);

        for (int i = 0; i < bitset.length(); i++) {
            if (bitset.get(i)) {
                counterOnes++;
            } else {
                counterZeroes++;
            }
        }

        ExampleResult exampleResult = new ExampleResult();
        List<Vars> resultVars = new ArrayList<>();
        // OneZeroMax is a maximization problem: multiply by -1 to minimize
        resultVars.add(new Vars(String.valueOf( -1.0 * counterOnes)));
        resultVars.add(new Vars(String.valueOf( -1.0 * counterZeroes)));
        exampleResult.setObjectives(resultVars);

        return exampleResult;
    }
}
