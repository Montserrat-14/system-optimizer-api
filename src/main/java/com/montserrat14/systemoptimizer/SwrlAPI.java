package com.montserrat14.systemoptimizer;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

import java.io.File;
import java.util.ArrayList;

public class SwrlAPI {

    private static ArrayList<String> output;

    public static ArrayList<String> getAlgorithms(Integer objectives)
    {
        output = new ArrayList<String>();

        try {
            // Create an OWL ontology using the OWLAPI
            OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
            OWLOntology ontology =  ontologyManager.loadOntologyFromOntologyDocument(new File("./ADS.owl"));

            // Create SQWRL query engine using the SWRLAPI
            SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

            // Create and execute a SQWRL query using the SWRLAPI
            String dealsWithHeavyProcessingEvaluationFunctions =  "dealsWithHeavyProcessingEvaluationFunctions(?alg,true) ^ ";
            String numberOfObjectives = String.valueOf(objectives);
            String query = "Algorithm(?alg) ^ " + dealsWithHeavyProcessingEvaluationFunctions
                    + "minObjectivesAlgorithmIsAbleToDealWith(?alg,?min) ^ swrlb:lessThanOrEqual(?min,"+numberOfObjectives+") ^"
                    + "maxObjectivesAlgorithmIsAbleToDealWith(?alg,?max) ^ swrlb:greaterThanOrEqual(?max,"+numberOfObjectives+")"
                    + " -> sqwrl:select(?alg)";

            SQWRLResult result = queryEngine.runSQWRLQuery("q1", query);

            // Process the SQWRL result
            System.out.println("Query: " + query);
            System.out.println("Executing query ... ");
            while (result.next()) {
                output.add(result.getNamedIndividual("alg").getShortName());
            }
            System.out.println("Query execution completed.");

        } catch (OWLOntologyCreationException e) {
            System.err.println("Error creating OWL ontology: " + e.getMessage());
            System.exit(-1);
        } catch (SWRLParseException e) {
            System.err.println("Error parsing SWRL rule or SQWRL query: " + e.getMessage());
            System.exit(-1);
        } catch (SQWRLException e) {
            System.err.println("Error running SWRL rule or SQWRL query: " + e.getMessage());
            System.exit(-1);
        } catch (RuntimeException e) {
            System.err.println("Error starting application: " + e.getMessage());
            System.exit(-1);
        }
        return output;
    }
}
