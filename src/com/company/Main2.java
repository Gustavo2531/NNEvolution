package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main2 {

    public static double sigm(double x){
        return 1.0/(1.0  + Math.pow(Math.E,-x));
    }

    public static double sigmDer(double x){
        return sigm(x)*(1.0-sigm(x));
    }

    public static void main(String[] args){

        // Read the input

        List<String> lines = new ArrayList<>();

        try {
            lines = Files.readAllLines(Paths.get("train_data_ML.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        double[] x1 = new double[lines.size()];
        double[] x2 = new double[lines.size()];
        double[] y = new double[lines.size()];

        for (int i = 0; i<lines.size();i++) {
            String [] parts = lines.get(i).split(" ");
            x1[i] = Double.parseDouble(parts[0]);
            x2[i] = Double.parseDouble(parts[1]);
            y[i] = Double.parseDouble(parts[2]);
        }

        int hidden = 16;
        int samples = lines.size();

        double [] w1 = new double[hidden]; // weights between X1 and hidden neurons
        double [] w2 = new double[hidden]; // weights between X2 and hidden neurons
        double [] W = new double[hidden]; // weights between hidden and output neuron

        for (int i = 0; i < hidden ; i++) {
            W[i] = Math.random()*2-1;
            w1[i] = Math.random()*2-1;
            w2[i] = Math.random()*2-1;
        }

        double [] b = new double[hidden]; // bias of hidden neurons
        double B = Math.random()*2-1; // bias of output neuron

        for (int i = 0; i < hidden ; i++) {
            b[i] = Math.random()*2-1;
        }


        double [] p = new double[hidden]; // inner potential of hidden neurons
        double [] o = new double[hidden]; // output of hidden neurons

        double O,P; // output and hidden potential of output neuron

        double e, errorSum = 0;

        double learningRate = 0.03; // previously called step, this is more appropriate name
        int epochs = 10000;


        for (int j = 0; j < epochs ; j++) {

            for (int i = 0; i < samples; i++) {

                for (int k = 0; k < hidden; k++) {
                    p[k] = x1[i] * w1[k] + x2[i] * w2[k] + b[k];
                    o[k] = sigm(p[k]);
                }

                P = 0;

                for (int k = 0; k < hidden; k++) {
                    P += o[k] * W[k];
                }

                O = sigm(P + B);

                e  = Math.pow(y[i] - O, 2) / 2;

                errorSum += e;

                for (int k = 0; k < hidden; k++) {
                    W[k]  += learningRate * (y[i] - O) * sigmDer(P) * o[k];
                    B     += learningRate * (y[i] - O) * sigmDer(P);

                    w1[k] += learningRate * (y[i] - O) * sigmDer(P) * W[k]* sigmDer(p[k]) * x1[i] ;
                    w2[k] += learningRate * (y[i] - O) * sigmDer(P) * W[k]* sigmDer(p[k]) * x2[i] ;
                    b[k]  += learningRate * (y[i] - O) * sigmDer(P) * W[k]* sigmDer(p[k]);
                }

            }

            System.out.println(errorSum/samples);
            errorSum = 0;
        }

    }
}
