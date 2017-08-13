// Created on Sunday, August 13, 2017 by Alex Zotov to show off on Monday, August 14, 2017
// Not intended to be of production quality and reliability, purely because time is limited

// Using WEKA libraries to create, train, and test the classifier.
// Using .ARFF data files

import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.*;

public class Main {

    String trainingFileName;

    // throws Exception for purpose of re-throwing uncaught exception. Bad practice in this case, but done to save time.
    public static void main(String[] args) throws Exception {

        System.out.println("Total Arguments: " + args.length);
        File trainingFile = new File(args[0]);

        ArffLoader trainingLoader = new ArffLoader();
        trainingLoader.setFile(trainingFile);

        Instances trainingStructure = trainingLoader.getStructure();
        trainingStructure.setClassIndex(trainingStructure.numAttributes() - 1);

        System.out.println("Structure of the training data file: \n" + trainingStructure);

        NaiveBayesUpdateable classifier = new NaiveBayesUpdateable();
        classifier.buildClassifier(trainingStructure);

        Instance currentInstance;
        while ((currentInstance = trainingLoader.getNextInstance(trainingStructure)) != null){
            classifier.updateClassifier(currentInstance);
        }

        System.out.println("Results of the classifier training: \n" + classifier);

    }
}
