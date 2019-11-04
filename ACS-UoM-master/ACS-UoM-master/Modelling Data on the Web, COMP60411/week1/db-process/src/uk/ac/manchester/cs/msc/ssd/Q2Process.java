package uk.ac.manchester.cs.msc.ssd;

import org.apache.commons.csv.CSVPrinter;
import uk.ac.manchester.cs.msc.ssd.core.CSVHandler;
import uk.ac.manchester.cs.msc.ssd.core.Database;
import uk.ac.manchester.cs.msc.ssd.core.DatabaseProcess;
import uk.ac.manchester.cs.msc.ssd.core.InputTable;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.text.DecimalFormat;

//
// THIS CLASS IS A TEMPLATE WHOSE IMPLEMENTATION SHOULD BE PROVIDED
// BY THE STUDENT IN ORDER TO PROVIDE A SOLUTION TO PROBLEM 1.
//
// THE "ExampleProcess" CLASS SHOULD BE USED AS A GUIDE IN CREATING
// THE IMPLEMENTATION.
// 2)  Eight columns:
// Last Name, First Name,  Post-Code, # Problems Answered,   % Problems Answered (from all problems),
// # Problems Answered Correctly,    %Problems Answered Correctly (from the problems answered),
// % Problems Answered Correctly (from all problems).
// Sorted first by 3rd attribute, then 7th, then 8th.

//
class Q2Process extends DatabaseProcess {
    static private final File ATTEMPTS_IN_FILE = getInputFile("attempts");
    static private final String ATTEMPTS_TABLE_NAME = "ATTEMPTS";

    static private final File PROBLEMS_IN_FILE = getInputFile("problems");
    static private final String PROBLEMS_TABLE_NAME = "PROBLEMS";

    static private final File PEOPLE_IN_FILE = getInputFile("people");
    static private final String PEOPLE_TABLE_NAME = "PEOPLE";

    // Names of columns for "ATTEMPTS" table
    static private final String ATTEMPTS_PERSON_ID = "PERSON_ID";
    static private final String ATTEMPTS_PROBLEM_ID = "PROBLEM_ID";
    static private final String ATTEMPTS_ANSWER = "ANSWER";
    // Names of columns for "PEOPLE" table
    static private final String PEOPLE_PERSON_ID = "ID";
    static private final String PEOPLE_FIRST_NAME = "FIRST_NAME";
    static private final String PEOPLE_LAST_NAME = "LAST_NAME";
    static private final String PEOPLE_COMPANY_NAME = "COMPANY_NAME";
    static private final String PEOPLE_ADDRESS = "ADDRESS";
    static private final String PEOPLE_CITY = "CITY";
    static private final String PEOPLE_COUNTRY = "COUNTRY";
    static private final String PEOPLE_POSTAL = "POSTAL";
    static private final String PEOPLE_PHONE1 = "PHONE1";
    static private final String PEOPLE_PHONE2 = "PHONE2";
    static private final String PEOPLE_EMAIL = "EMAIL";
    static private final String PEOPLE_WEB = "WEB";
    // Names of columns for "PROBLEM" table
    static private final String PROBLEM_ID_NAME = "ID";
    static private final String PROBLEM_OP_NAME = "OPERATOR";
    static private final String PROBLEM_ARG1_NAME = "ARG1";
    static private final String PROBLEM_ARG2_NAME = "ARG2";

    static private final String ATTEMPTS_TABLE_CREATION_ARGS
            = ATTEMPTS_PERSON_ID + " integer NOT NULL, "
            + ATTEMPTS_PROBLEM_ID + " integer NOT NULL, "
            + ATTEMPTS_ANSWER + " integer NOT NULL";

    static private final String PEOPLE_TABLE_CREATION_ARGS
            = PEOPLE_PERSON_ID + " integer NOT NULL, "
            + PEOPLE_FIRST_NAME + " char(15), "
            + PEOPLE_LAST_NAME + " char(15), "
            + PEOPLE_COMPANY_NAME + " char(50), "
            + PEOPLE_ADDRESS + " char(50), "
            + PEOPLE_CITY + " char(50), "
            + PEOPLE_COUNTRY + " char(50), "
            + PEOPLE_POSTAL + " char(10), "
            + PEOPLE_PHONE1 + " char(50), "
            + PEOPLE_PHONE2 + " char(50), "
            + PEOPLE_EMAIL + " char(50), "
            + PEOPLE_WEB + " char(50)";

    static private final String PROBLEMS_TABLE_CREATION_ARGS
            = PROBLEM_ID_NAME + " integer NOT NULL, "
            + PROBLEM_ARG1_NAME + " integer NOT NULL, "
            + PROBLEM_OP_NAME + " char(1), "
            + PROBLEM_ARG2_NAME + " integer NOT NULL";

    static private final String SELECT_PEOPLE_QUERY
            = " SELECT ID, LAST_NAME, FIRST_NAME, POSTAL " +
            " FROM PEOPLE, ATTEMPTS " +
            " WHERE ID = PERSON_ID " +
            " GROUP BY ID, LAST_NAME, FIRST_NAME, POSTAL ";

    static private final String SELECT_PROBLEMS_QUERY
            = "SELECT * FROM PROBLEMS";

    static private final String SELECT_ATTEMPT_QUERY
            = "SELECT * FROM ATTEMPTS ORDER BY PERSON_ID";


    private Database database;
    private CSVHandler csvHandler;

    private InputTable attemptsInputTable = new InputTable();
    private InputTable peopleInputTable = new InputTable();
    private InputTable problemsInputTable = new InputTable();

    private List<Result> result = new ArrayList<>();
    private List<Integer> problemAnswers = new ArrayList<>();
    private Map<Integer, problemAnswered> attempts = new HashMap<>();

    private class Problem {
        private String op;
        private int arg1;
        private int arg2;

        public int getAnswer(String op, int arg1, int arg2) {
            if (op.equals("+")) {
                return arg1 + arg2;
            } else if (op.equals("-")) {
                return arg1 - arg2;
            } else if (op.equals("*")) {
                return arg1 * arg2;
            } else {
                return arg1 / arg2;
            }
        }

        Problem(ResultSet results) throws SQLException {
            op = results.getString(PROBLEM_OP_NAME);
            arg1 = results.getInt(PROBLEM_ARG1_NAME);
            arg2 = results.getInt(PROBLEM_ARG2_NAME);
            problemAnswers.add(getAnswer(op, arg1, arg2));
        }
    }

    public class problemAnswered {
        private int numAnswer;
        private double percentAnswer;
        private int correctAnswer;
        private double percentCorrectAnswer;
        private double getPercentCorrectAnswers;

        public problemAnswered(int num, double num2, int num3, double num4, double num5) {
            this.numAnswer = num;
            this.percentAnswer = num2;
            this.correctAnswer = num3;
            this.percentCorrectAnswer = num4;
            this.getPercentCorrectAnswers = num5;
        }
    }

    private class Attempt {
        private int personID;
        private int problemID;
        private int answer;

        Attempt(ResultSet results) throws SQLException {
            personID = results.getInt(ATTEMPTS_PERSON_ID);
            problemID = results.getInt(ATTEMPTS_PROBLEM_ID);
            answer = results.getInt(ATTEMPTS_ANSWER);

            if (!attempts.containsKey(personID)) {
                if (answer == problemAnswers.get(problemID - 1)) {
                    attempts.put(personID, new problemAnswered(1, 1.00, 1, 1.00, (double) 1 / problemAnswers.size()));
                } else {
                    attempts.put(personID, new problemAnswered(1, 1.00, 0, 0.00, 0.00));
                }
            } else {
                problemAnswered oldRecord = attempts.get(personID);
                if (answer == problemAnswers.get(problemID - 1)) {
                    attempts.put(personID, new problemAnswered(oldRecord.numAnswer + 1,
                            (double) (oldRecord.numAnswer + 1) / problemAnswers.size(),
                            oldRecord.correctAnswer + 1, (double) (oldRecord.correctAnswer + 1) / (oldRecord.numAnswer + 1),
                            (double) (oldRecord.correctAnswer + 1) / problemAnswers.size()));
                } else {
                    attempts.put(personID, new problemAnswered(oldRecord.numAnswer + 1,
                            (double) (oldRecord.numAnswer + 1) / problemAnswers.size(),
                            oldRecord.correctAnswer, (double) oldRecord.correctAnswer / (oldRecord.numAnswer + 1),
                            (double) oldRecord.correctAnswer / problemAnswers.size()));
                }
            }
        }
    }

    private class Result {
        private int id;
        private String last_name;
        private String first_name;
        private String postcode;
        private int numAnswer;
        private double percentAnswer;
        private int correctAnswer;
        private double percentCorrectAnswer;
        private double getPercentCorrectAnswers;
        DecimalFormat df = new DecimalFormat("0%");

        public String toString() {
            return "Last Name: " + last_name + " ,First Name: " + first_name + " ,Postcode: " + postcode
                    + " ,#Answered: " + numAnswer + " ,%Answered: " + df.format(percentAnswer)
                    + " ,#Answered Correctly: " + correctAnswer + " ,%Answered Correctly: "
                    + df.format(percentCorrectAnswer) + " ,%Answered Correctly(All): "
                    + df.format(getPercentCorrectAnswers);
        }

        Result(ResultSet results) throws SQLException {
            id = results.getInt(PEOPLE_PERSON_ID);
            last_name = results.getString(PEOPLE_LAST_NAME);
            first_name = results.getString(PEOPLE_FIRST_NAME);
            postcode = results.getString(PEOPLE_POSTAL);
            numAnswer = attempts.get(id).numAnswer;
            percentAnswer = attempts.get(id).percentAnswer;
            correctAnswer = attempts.get(id).correctAnswer;
            percentCorrectAnswer = attempts.get(id).percentCorrectAnswer;
            getPercentCorrectAnswers = attempts.get(id).getPercentCorrectAnswers;
            result.add(this);
        }

        // Render problem as a line in the output CSV file
        void print(CSVPrinter printer) throws IOException {
            printer.printRecord(last_name, first_name, postcode, numAnswer, df.format(percentAnswer), correctAnswer,
                    df.format(percentCorrectAnswer), df.format(getPercentCorrectAnswers));
        }
    }

    // Implementation of the "readInput" method as specified by the base-class.
    protected void readInput() throws IOException {
        problemsInputTable.readFromFile(csvHandler, PROBLEMS_IN_FILE);
        attemptsInputTable.readFromFile(csvHandler, ATTEMPTS_IN_FILE);
        peopleInputTable.readFromFile(csvHandler, PEOPLE_IN_FILE);
    }

    // Implementation of the "runCoreProcess" method as specified by the base-class.
    protected void runCoreProcess() throws SQLException {
        // Create "ATTEMPTS" table in database
        database.createTable(ATTEMPTS_TABLE_NAME, ATTEMPTS_TABLE_CREATION_ARGS);
        attemptsInputTable.writeToDatabase(database, ATTEMPTS_TABLE_NAME);
        // Create "PEOPLE" table in database
        database.createTable(PEOPLE_TABLE_NAME, PEOPLE_TABLE_CREATION_ARGS);
        peopleInputTable.writeToDatabase(database, PEOPLE_TABLE_NAME);
        // Create "PROBLEMS" table in database
        database.createTable(PROBLEMS_TABLE_NAME, PROBLEMS_TABLE_CREATION_ARGS);
        problemsInputTable.writeToDatabase(database, PROBLEMS_TABLE_NAME);


        ResultSet problemResults = database.executeQuery(SELECT_PROBLEMS_QUERY);
        while (problemResults.next()) {
            new Problem(problemResults);
        }

        ResultSet attemptResults = database.executeQuery(SELECT_ATTEMPT_QUERY);
        while (attemptResults.next()) {
            new Attempt(attemptResults);
        }

        ResultSet results = database.executeQuery(SELECT_PEOPLE_QUERY);
        while (results.next()) {
            new Result(results);
        }
        Collections.sort(result, (r1, r2) -> {
            if (r1.postcode.compareTo(r2.postcode) == 0) {
                if (r1.percentCorrectAnswer < r2.percentCorrectAnswer) {
                    return 1;
                } else if (r1.percentCorrectAnswer == r2.percentCorrectAnswer) {
                    if (r1.getPercentCorrectAnswers < r2.getPercentCorrectAnswers) {
                        return 1;
                    } else if (r1.getPercentCorrectAnswers == r2.getPercentCorrectAnswers) {
                        return 0;
                    } else return -1;
                } else return -1;
            } else return r1.postcode.compareTo(r2.postcode);
        });
        for (Result r : result) {
            System.out.println(r.toString());
        }
    }

    // Implementation of the "writeOutput" method as specified by the base-class.
    protected void writeOutput() throws IOException {
        File outCSVFile = getOutputFile();
        CSVPrinter printer = csvHandler.createPrinter(outCSVFile);
        for (Result r : result) {
            r.print(printer);
        }
    }

    // Constructor.
    Q2Process(Database database, CSVHandler csvHandler) {
        this.database = database;
        this.csvHandler = csvHandler;
    }
}
