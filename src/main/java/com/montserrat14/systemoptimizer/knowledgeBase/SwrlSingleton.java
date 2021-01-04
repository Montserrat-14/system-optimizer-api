package com.montserrat14.systemoptimizer.knowledgeBase;

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
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class SwrlSingleton {

    private final static String OWL_PATH = System.getenv("OWL_PATH");
    private final static int MAX_RETRIES = Integer.parseInt(System.getenv("MAX_TRIES"));
    private final static String OWL_QUERY = System.getenv("OWL_QUERY");

    private static SwrlSingleton instance;
    private static SQWRLQueryEngine queryEngine;

    private SwrlSingleton() {
    }

    public static SwrlSingleton getInstance() {
        if (instance == null) {
            instance = new SwrlSingleton();
        }
        return instance;
    }

    public OWLOntology createOntology() {
        int attempts = 0;
        OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
        OWLOntology ontology = null;


        while (attempts++ < MAX_RETRIES) {
            try {
                File f = new File(OWL_PATH);
                if (!f.exists() || f.isDirectory()) throw new FileNotFoundException("Could not find OWL file."+OWL_PATH);

                ontology = ontologyManager.loadOntologyFromOntologyDocument(f);

                queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);
                break;

            } catch (OWLOntologyCreationException e) {
                System.err.println("Error running SWRL rule or SQWRL query: " + e.getMessage());
            } catch (NullPointerException e) {
                System.err.println("Could not find OWL file. (wrong path)");
            } catch (FileNotFoundException e) {
                System.err.println(e.getMessage());
            }
        }
        return ontology;
    }

    public ArrayList<String> getAlgorithms(Integer objectives) {
        ArrayList<String> output = new ArrayList<String>();
        int attempts = 0;

        while (attempts++ < MAX_RETRIES) {
            try {

                String numberOfObjectives = String.valueOf(objectives);
                String query = OWL_QUERY;
                query = query.replace("%%NOBJECTIVES%%", numberOfObjectives);

                SQWRLResult result = queryEngine.runSQWRLQuery("getAlgorithms", query);

                while (result.next()) {
                    output.add(result.getNamedIndividual("alg").getShortName().substring(1));
                }
                break;

            } catch (SWRLParseException e) {
                System.err.println("Error parsing SWRL rule or SQWRL query: " + e.getMessage());
            } catch (SQWRLException e) {
                System.err.println("Error running SWRL rule or SQWRL query: " + e.getMessage());
            } catch (RuntimeException e) {
                System.err.println("Error starting application: " + e.getMessage());
            }
        }
        return output;
    }
}
