package com.montserrat14.systemoptimizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@SpringBootApplication
public class SystemoptimizerApplication {

	private static ArrayList<String> listOfAlgorithms;
	private static int numberOfObjectives = 2;

	public static void main(String[] args) {
		//SpringApplication.run(SystemoptimizerApplication.class, args);
		listOfAlgorithms = new ArrayList<String>();
		listOfAlgorithms = SwrlAPI.getAlgorithms(numberOfObjectives);

		System.out.println(listOfAlgorithms.toString());

	}

}
