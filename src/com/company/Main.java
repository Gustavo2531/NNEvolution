package com.company;

import java.io.IOException;
import java.util.Random;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    static ArrayList<Individual> population = new ArrayList<>();

    static int popSize = 100;

    // creates a child from two parents
 
    public static Individual cross(Individual a, Individual b){

        Individual c = new Individual();
        
        int p1=(int)(Math.random()*a.genotype.length);//parent 1
        int p2 = b.genotype.length - p1;
        for(int i=0; i<p1; i++){
        	c.genotype[i]=a.genotype[i];
        	
        }
        
        for(int i=p1; i<p2; i++){
        	c.genotype[i]=b.genotype[i];
        	
        }
        // TODO
        
        
        return c;

    }

    // picks one parent from population
    public static Individual selectParent(){
    	int i =(int)(Math.random()*population.size());
    	Individual a1=population.get(i);
    	int i2 =(int)(Math.random()*population.size());
    	Individual a2 = population.get(i2);
    	
    	if(a1.fitness>a2.fitness){
    		return a1;
    	}else{
    		return a2;
    	}
        // TODO
    }

    public static void main(String[] args){

        Individual.init();

        for (int i = 0; i < popSize ; i++) {
            population.add(new Individual());
        }

        int generations = 5000;
        int children = 50;


        double bestFitness = population.get(0).fitness;


        for (int i = 0; i < generations; i++) {

            // select parents

            for (int j = 0; j < children ; j++) {

                Individual p1 = selectParent();
                Individual p2 = selectParent();

                Individual c = cross(p1,p2);

                c.mutate();

                c.countFitness();

                population.add(c);

            }

            Collections.sort(population);

            if(population.get(0).fitness > bestFitness){

                bestFitness = population.get(0).fitness;
                System.out.println(bestFitness + " " + 1/bestFitness);
            }

            population = new ArrayList<>(population.subList(0,popSize)) ;

        }

        System.out.println();
        System.out.println("Evolution ended. The best individual's genotype is:");

        for (int i = 0; i < population.get(0).genotype.length; i++) {
            System.out.print(population.get(0).genotype[i] + " ");
        }

    }
}
