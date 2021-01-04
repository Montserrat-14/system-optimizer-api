package com.montserrat14.systemoptimizer.model.problem;

import java.util.ArrayList;
import java.util.List;

public class ProblemRequest {

    private Integer id;
    private String name;
    private String description;

    private Integer nObjectives;
    private List<Variables> listOfVariables;

    private String endpoint;
    private String payload;
    private Integer duration;

    public ProblemRequest(){}

    public ProblemRequest(Integer id, String name, String description, Integer nObjectives, String endpoint, String payload, Integer duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.listOfVariables = new ArrayList<>();
        this.nObjectives = nObjectives;
        this.endpoint = endpoint;
        this.payload = payload;
        this.duration = duration;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Variables> getListOfVariables() {
        return listOfVariables;
    }

    public void setListOfVariables(List<Variables> listOfVariables) {
        this.listOfVariables = listOfVariables;
    }

    public Integer getnObjectives() {
        return nObjectives;
    }

    public void setnObjectives(Integer nObjectives) {
        this.nObjectives = nObjectives;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
