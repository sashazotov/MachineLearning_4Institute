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
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Main {

    String trainingFileName;
    String testingFileName;
    boolean testing;
    final double PI = 3.14;

    // throws Exception for purpose of re-throwing uncaught exception. Bad practice in this case, but done to save time.
    public static void main(String[] args) throws Exception {

        Main dataClassifier = new Main();

        // check if arguments - data filenames were provided
        System.out.println("Total Arguments: " + args.length);
        if(args.length >= 1){
            dataClassifier.setTrainingFileName(args[0]);
        }else{
            System.out.println("No filename provided with training data. \nQUITTING\n"); return;
        }

//        File trainingFile = new File(dataClassifier.getTrainingFileName());

        // load training data file
        ArffLoader trainingLoader = new ArffLoader();
        try {
            trainingLoader = loadData(dataClassifier.getTrainingFileName());
        }
        catch (ArrayIndexOutOfBoundsException exc){
            //this will not ever happen because function quits if no arguments were passed
            System.err.println(exc.getMessage() + "\nQUITTING"); return;
        }
        catch (FileNotFoundException exc){
            System.err.println(exc.getMessage() + "\nQUITTING"); return;
        }

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


        //TESTING
        //if second argument provided to the function, assuming it was a data file for testing
        //set filename to the 2nd argument value, and testing variable to 'true'
        if(args.length >= 2){
            dataClassifier.setTestingFileName(args[1]);
            dataClassifier.setTesting(1);
        }

        // if datafile was provided, try to read data and run testing
        if(dataClassifier.isTesting()){
            // load datafile for testing
            ArffLoader testingLoader;
            try{
                testingLoader = loadData(dataClassifier.getTestingFileName());
            }
            catch (ArrayIndexOutOfBoundsException | FileNotFoundException exc){
                System.err.println("Will not test. Test data filename was not provided or invalid. \n QUTTING\n");
                return;
            }

            Instances testingStructure = testingLoader.getStructure();
            Instances testingData = testingLoader.getDataSet();
            testingStructure.setClassIndex(testingStructure.numAttributes() - 1);
            testingData.setClassIndex(testingStructure.numAttributes() - 1);

            Evaluation test = new Evaluation(testingData);
            test.evaluateModel(classifier, testingData);

            String testSummary = test.toSummaryString();
            System.out.println(testSummary);

            // display bar-chart with python
            //System.out.println(System.getProperty("user.dir"));
            callPython("C:\\Users\\a\\Anaconda2\\python src\\display.py", (int)test.numInstances(), (int)test.correct(), (int)test.incorrect());
            //Runtime runtime = Runtime.getRuntime();
            //String pythonArg = test.numInstances() + " " + test.correct() + " " + test.incorrect();
            //Process process = runtime.exec("C:\\Users\\a\\Anaconda2\\python src\\display.py" + " " + pythonArg);
            //System.out.println("C:\\Users\\a\\Anaconda2\\python src\\display.py" + " " + pythonArg);
        }

    }

    public static void callPython(String file, int totalInstances, int correctlyClassified, int incorrectlyClassified) throws Exception{
        Runtime runtime = Runtime.getRuntime();
        String arguments = totalInstances + " " + correctlyClassified + " " + incorrectlyClassified;
        Process process = runtime.exec(file + " " + arguments);
    }

    // If datafile exists, create data loader
    public static ArffLoader loadData(String filename) throws Exception{

        if(!isArff(filename)){
            throw new FileNotFoundException("Wrong File Type");
        }

        File dataFile = new File(filename);
        if(!dataFile.exists()){
            throw new FileNotFoundException("File Does Not Exist");
        }

        ArffLoader loader = new ArffLoader();
        loader.setFile(dataFile);

        return loader;
    }

    // Check if file is ARFF by checking if the file's extension is .arff,
    // or in other words, check if filename ends with '.arff' using regular expression
    final public static boolean isArff(String str){
        //System.out.println("isARFF? " + str);
        Pattern pattern = Pattern.compile(".+\\.arff$");
        Matcher match = pattern.matcher(str);
        //System.out.println(match.matches());
        return match.matches();
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
    public void setTesting(int testing) { this.testing = testing!=0; }
}
