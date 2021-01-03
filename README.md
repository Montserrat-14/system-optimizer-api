# System Optimizer API
### Backend for system optimizer

*Nowadays, one of the biggest challenges related to optimization of systems and services is to know which algorithm we should use in each of the different problems that exist in the business world. Usually, the choice of the best algorithm is a very time-consuming process and involves the experimentation of several methods by many experts in the industry. With this in mind, and with the aim of making this process more agile, we were presented with an innovative idea that consists in providing optimization services in a fully automatic and digital way, based on the knowledge that has already been acquired over the years. The core idea is to use a knowledge base that contains the expertise on which algorithms are best for a given type of problem and after its choice, run it dynamically through a provided framework. The biggest challenge here is to interconnect the various components and make this whole complex process into a product that can be used by customers in a scalable and flexible way, in an area of great innovation that is always changing.*



## Process Flow
![process_flow](https://github.com/Montserrat-14/system-optimizer-api/blob/main/documentation/process_flow.jpg)

1. The user starts by describing the problem he wants to
optimize, and this description from the optimization point

2. After the description, the user identifies the decision
variables, that is, the aspects of his problem that are
changeable. The client indicates, for each variable, its
name (merely illustrative), its type and a value range it
can take (if it is a number). (For example, in a problem the
customer has to manage machines and their temperature
directly affects production, in this situation the user can
indicate a variable that represents the temperature and
refer to the minimum and maximum temperature at which
a machine can operate). These variables are the key
point for optimization, because it will be the number of
variables and the type of these that will be used to obtain
the correct algorithm for a better result.

3. It will also be necessary that the user indicates
his evaluation method and the quality criteria of each
solution, this method will allow that in each iteration of
the algorithm (for each solution produced) the results are
evaluated according to criteria that only the client knows.
This allows qualifying each solution during the execution
of the algorithm. We will consider that the client will
indicate an endpoint for a web service.
Our application will also ask the client how much time
he has to present the results, since the execution of some
algorithms can represent a lengthy process. It is still
under discussion if we will ask the user to indicate a
right measure (minutes) or if an open measure (little,
some, a long time). The first hypothesis is better, from
the point of view of usability, because the user has a
certain visual return of what he is choosing, however,
the scale of values can be considerably variable from
algorithm to algorithm. In the second hypothesis, it passes
some control to our implementation which allows a better
choice of the number of iterations.

4. After the indication of all the information from the
user, he submits the process. This submission will be a
call to a web service exposed by our server.

5. In the context of the server, using the number of vari-
ables and the type, a query will be made to the ontology to obtain a list of possible algorithms to optimize the
problem.

7. After obtaining the list, we will create a method to
reduce the incompatibility of algorithm names between
the ontology and the Framework.

8. With this, we will look for the various algorithms in
the Framework.

9. After discovering the chosen algorithm, we will
dynamically instantiate it.

10. Once the algorithm has been instantiated, we will
execute a single iteration of it, to determine the runtime
of that iteration, this will allow us to know, through the
indication of the user’s time, how many iterations we can
perform.

11. We will then run the various iterations of the algo-
rithm, and in each iteration a solution will be generated
composed of as many values as variables have been
indicated by the user.

12. In the same iteration using the generated solution, the
client’s method will be called to evaluate it. The result of
this call will be the quality of the solution. The solution
and quality will be recorded in a file at the end of each
iteration.

13. At the end and after the execution of the process all
the solutions and their respective quality will be presented
to the user in the application. As an alternative and to
allow the user to take several actions (as the principle of
usability indicates), it will be possible to download the
.csv documents with the values.


## How to pull and run Docker image

### Pull
```batch
docker pull henriquepcabral/system-optimizer-api:latest
```

### Run
```batch
docker run --name backend -dit -p 3080:8080 henriquepcabral/system-optimizer-api:latest
```


## Used Frameworks
- Spring Boot
- JMetal
- SwrlAPI



