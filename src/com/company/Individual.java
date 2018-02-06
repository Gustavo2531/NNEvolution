package com.company;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import java.util.Random;


/**
 * Created by cejkis on 11.6.17.
 */
public class Individual implements Comparable<Individual> {

    static List<String> lines;
    static double[] x1;
    static double[] x2;
    static double[] y;

    static int hidden = 32;
    public static int randInt(int min, int max) {

      
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
    public static void init(){

        try {
            lines = Files.readAllLines(Paths.get("train_data_ML.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        x1 = new double[lines.size()];
        x2 = new double[lines.size()];
        y = new double[lines.size()];

        for (int i = 0; i<lines.size();i++) {
            String [] parts = lines.get(i).split(" ");
            x1[i] = Double.parseDouble(parts[0]);
            x2[i] = Double.parseDouble(parts[1]);
            y[i] = Double.parseDouble(parts[2]);
        }

    }

    // ---------------------

    double[] genotype;

    public double fitness;

    public static double sigm(double x) {
        return 1.0 / (1.0 + Math.pow(Math.E, -x));
    }

    public Individual() {

        genotype = new double[2 * hidden + hidden];

        for (int i = 0; i < genotype.length ; i++) {
            genotype[i] = Math.random()*2-1;
        }

        fitness = countFitness();

    }

    // slightly change an individual
    public void mutate(){
    	int[] randoms=new int[3];
    	for(int i=0;i<randoms.length; i++){
    		
    		randoms[i]=(int)(Math.random()*genotype.length);
    		
    	}
    	genotype[randoms[0]]+=(Math.random()*0.5)-0.3;
    	genotype[randoms[1]]+=(Math.random()*0.5)-0.3;
    	genotype[randoms[2]]+=(Math.random()*0.5)-0.3;


        // Todo


    }

    public double countFitness() {

        double[] w1 = new double[hidden]; // weights between X1 and hidden neurons
        double[] w2 = new double[hidden]; // weights between X2 and hidden neurons
        double[] W = new double[hidden]; // weights between hidden and output neuron

        int genIt = 0;

        for (int i = 0; i < hidden; i++) {
            w1[i] = genotype[genIt++];
        }

        for (int i = 0; i < hidden; i++) {
            w2[i] = genotype[genIt++];
        }

        for (int i = 0; i < hidden; i++) {
            W[i] = genotype[genIt++];
        }


        double[] p = new double[hidden]; // inner potential of hidden neurons
        double[] o = new double[hidden]; // output of hidden neurons

        double O, P; // output and hidden potential of output neuron

        double e, errorSum = 0;


        for (int i = 0; i < y.length; i++) {

            for (int k = 0; k < hidden; k++) {
                p[k] = x1[i] * w1[k] + x2[i] * w2[k];
                o[k] = sigm(p[k]);
            }

            P = 0;

            for (int k = 0; k < hidden; k++) {
                P += o[k] * W[k];
            }

            O = sigm(P);

            e = Math.pow(y[i] - O, 2) / 2;

            errorSum += e;

        }


        return y.length/errorSum ;


    }

    @Override
    public int compareTo(Individual o) {
        return (int)Math.signum(o.fitness - fitness );
    }





}
