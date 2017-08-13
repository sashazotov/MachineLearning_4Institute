// Created on Sunday, August 13, 2017 by Alex Zotov to show off on Monday, August 14, 2017
// Not intended to be of production quality and reliability, purely because time is limited

// Using WEKA libraries to create, train, and test the classifier.
// Using .ARFF data files

import weka.Run;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.*;

public class Main {

    String trainingFileName;
    String testingFileName;
    boolean testing;

    // throws Exception for purpose of re-throwing uncaught exception. Bad practice in this case, but done to save time.
    public static void main(String[] args) throws Exception {

        Main dataClassifier = new Main();
        System.out.println("Total Arguments: " + args.length);
        if(args.length >= 1){
            dataClassifier.setTrainingFileName(args[0]);
        }else{
            System.out.println("No filename provided for testing data. QUIT\n");
        }

        File trainingFile = new File(dataClassifier.getTrainingFileName());

        ArffLoader trainingLoader = loadData(dataClassifier.getTrainingFileName());
        Instances trainingStructure = trainingLoader.getStructure();
        trainingStructure.setClassIndex(trainingStructure.numAttributes() - 1);

        System.out.println("Structure of the training data file: \n" + trainingStructure);
        System.out.println("Training data sample size is: " + trainingStructure.numInstances() + " elements, and " + trainingStructure.numAttributes() + " attributes");

        NaiveBayesUpdateable classifier = new NaiveBayesUpdateable();
        classifier.buildClassifier(trainingStructure);

        Instance currentInstance;
        while ((currentInstance = trainingLoader.getNextInstance(trainingStructure)) != null){
            classifier.updateClassifier(currentInstance);
        }

        System.out.println("Results of the classifier training: \n" + classifier);

        //Testing
        if(args.length >= 2){
            dataClassifier.setTestingFileName(args[1]);
            dataClassifier.setTesting(true);
        }

        if(dataClassifier.isTesting()){
            System.out.println("About to test");
            ArffLoader testingLoader = loadData(dataClassifier.getTestingFileName());
            Instances testingStructure = testingLoader.getStructure();
            Instances testingData = testingLoader.getDataSet();
            testingStructure.setClassIndex(testingStructure.numAttributes() - 1);
            testingData.setClassIndex(testingStructure.numAttributes() - 1);

            Evaluation test = new Evaluation(testingData);
            test.evaluateModel(classifier, testingData);

            String testSummary = test.toSummaryString();
            System.out.println(testSummary);

            System.out.println(System.getProperty("user.dir"));
            Runtime runtime = Runtime.getRuntime();
            String pythonArg = test.numInstances() + " " + test.correct() + " " + test.incorrect();
            Process process = runtime.exec("C:\\Users\\a\\Anaconda2\\python src\\display.py" + " " + pythonArg);

            System.out.println("C:\\Users\\a\\Anaconda2\\python src\\display.py" + " " + pythonArg);
        }



    }


    // If datafile exists, create data loader
    public static ArffLoader loadData(String filename) throws Exception{

        File dataFile = new File(filename);
        if(!dataFile.exists()){
            throw new FileNotFoundException("File Does Not Exist");
        }

        ArffLoader loader = new ArffLoader();
        loader.setFile(dataFile);

        return loader;
    }



    // All Getters and Setters
    public String getTrainingFileName() {
        return trainingFileName;
    }

    public String getTestingFileName() {
        return testingFileName;
    }

    public boolean isTesting() {
        return testing;
    }

    public void setTrainingFileName(String trainingFileName) {
        this.trainingFileName = trainingFileName;
    }

    public void setTestingFileName(String testingFileName) {
        this.testingFileName = testingFileName;
    }

    public void setTesting(boolean testing) {
        this.testing = testing;
    }
}
