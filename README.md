# System Optimizer API
### Backend for system optimizer

*Nowadays, one of the biggest challenges related to optimization of systems and services is to know which algorithm we should use in each of the different problems that exist in the business world. Usually, the choice of the best algorithm is a very time-consuming process and involves the experimentation of several methods by many experts in the industry. With this in mind, and with the aim of making this process more agile, we were presented with an innovative idea that consists in providing optimization services in a fully automatic and digital way, based on the knowledge that has already been acquired over the years. The core idea is to use a knowledge base that contains the expertise on which algorithms are best for a given type of problem and after its choice, run it dynamically through a provided framework. The biggest challenge here is to interconnect the various components and make this whole complex process into a product that can be used by customers in a scalable and flexible way, in an area of great innovation that is always changing.*

## Architecture Flow

***ProblemController*** receives the POST request with the problem invoking the ***AlgorithmService***.
***SwrlSingleton***, using SWRL API, is instantiated in order to make the query to the OWL knowledge base. We chose the Singleton architecture to make sure we can only have one instance at a time. This query return a list of the algorithms best suited to the number of the objecitves provided.

We then try to find these algorithms in the jMetal Framework using refletion. When found, a single iteration is ran in order to determine the process time so we can calculate the number of iterations for the user's given time in the problem request. 
We run the algortithm the number of iterations calculated and return the output - solutions and qualities.

//factory, builder



## Created examples

### Four examples were created:
- Double Problem (Kursawe)
- Integer Problem (NMMin)
- Binary Problem (OneZeroMax)
- Double Problem (DTLZ1)


## Process Flow
![process_flow](https://github.com/Montserrat-14/system-optimizer-api/blob/main/documentation/process_flow.jpg)


## How to pull and run Docker image

### Pull
```batch
docker-compose pull
```

### Run
```batch
docker-compose up
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



## Used Frameworks
- Spring Boot
- JMetal
- SwrlAPI



