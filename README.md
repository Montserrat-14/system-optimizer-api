# System Optimizer API
### Backend for system optimizer

*Nowadays, one of the biggest challenges related to optimization of systems and services is to know which algorithm we should use in each of the different problems that exist in the business world. Usually, the choice of the best algorithm is a very time-consuming process and involves the experimentation of several methods by many experts in the industry. With this in mind, and with the aim of making this process more agile, we were presented with an innovative idea that consists in providing optimization services in a fully automatic and digital way, based on the knowledge that has already been acquired over the years. The core idea is to use a knowledge base that contains the expertise on which algorithms are best for a given type of problem and after its choice, run it dynamically through a provided framework. The biggest challenge here is to interconnect the various components and make this whole complex process into a product that can be used by customers in a scalable and flexible way, in an area of great innovation that is always changing.*

## Architecture Flow

//TODO


## Process Flow
![process_flow](https://github.com/Montserrat-14/system-optimizer-api/blob/main/documentation/process_flow.jpg)


## How to pull and run Docker image

### Pull
```batch
docker pull henriquepcabral/system-optimizer-api:latest
```

### Run
```batch
docker run --name backend -dit -p 3080:8080 henriquepcabral/system-optimizer-api:latest
```

### Build locally
```batch
docker build -t system-optimizer-api .
```
### Run
```batch
docker run --name backend -dit -p 3080:8080 system-optimizer-api
```


## Environment variables

Docker Enviroment variables were used to facilitate possible modifications without the need to search and alter the code and build again.

| Name |  Description  |
| ------------------- | ------------------- |
|  OWL_PATH |  path to the OWL file |
|  OWL_QUERY | query made to the OWL file |
|  MAX_TRIES |  max retries when an error occurs |
|  RESULTSPATH | folder where the result files are generated |
|  RESULTSEXTENSION | result file extension |
|  INTEGER_DEFAULT_PARAM |  default parameter to integer |
|  DOUBLE_DEFAULT_PARAM |  default parameter to double |
|  FLOAT_DEFAULT_PARAM |  default parameter to float |



## Created examples

//TODO

## Used Frameworks
- Spring Boot
- JMetal
- SwrlAPI



