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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//
// THIS CLASS IS A TEMPLATE WHOSE IMPLEMENTATION SHOULD BE PROVIDED
// BY THE STUDENT IN ORDER TO PROVIDE A SOLUTION TO PROBLEM 1.
//
// THE "ExampleProcess" CLASS SHOULD BE USED AS A GUIDE IN CREATING
// THE IMPLEMENTATION.
// 3) Three columns:
// Problem,    # People who answered this Problem,
// % People who answered this Problem correctly (from those who answered it).
//
class Q3Process extends DatabaseProcess {

    static private final File ATTEMPTS_IN_FILE = getInputFile("attempts");
    static private final String ATTEMPTS_TABLE_NAME = "ATTEMPTS";

    static private final File PROBLEMS_IN_FILE = getInputFile("problems");
    static private final String PROBLEMS_TABLE_NAME = "PROBLEMS";

    static private final String COUNT = "COUNT";
    static private final String CORRECT = "CORRECT";

    // Names of columns for "ATTEMPTS" table
    static private final String ATTEMPTS_PERSON_ID = "PERSON_ID";
    static private final String ATTEMPTS_PROBLEM_ID = "PROBLEM_ID";
    static private final String ATTEMPTS_ANSWER = "ANSWER";
    // Names of columns for "PROBLEM" table
    static private final String PROBLEM_ID_NAME = "ID";
    static private final String PROBLEM_OP_NAME = "OPERATOR";
    static private final String PROBLEM_ARG1_NAME = "ARG1";
    static private final String PROBLEM_ARG2_NAME = "ARG2";

    static private final String ATTEMPTS_TABLE_CREATION_ARGS
            = ATTEMPTS_PERSON_ID + " integer NOT NULL, "
            + ATTEMPTS_PROBLEM_ID + " integer NOT NULL, "
            + ATTEMPTS_ANSWER + " integer NOT NULL";

    static private final String PROBLEMS_TABLE_CREATION_ARGS
            = PROBLEM_ID_NAME + " integer NOT NULL, "
            + PROBLEM_ARG1_NAME + " integer NOT NULL, "
            + PROBLEM_OP_NAME + " char(1), "
            + PROBLEM_ARG2_NAME + " integer NOT NULL";

    static private  String SELECT_ALL_PROBLEMS_QUERY
            = " SELECT * FROM "
            + PROBLEMS_TABLE_NAME;

    static private final String SOLUTION_QUERY
            = " SELECT PROBLEM_ID, COUNT( PROBLEM_ID ) AS " + COUNT
            + " FROM " + ATTEMPTS_TABLE_NAME
            + " GROUP BY PROBLEM_ID";

    private Database database;
    private CSVHandler csvHandler;

    private InputTable attemptsInputTable = new InputTable();
    private InputTable problemsInputTable = new InputTable();

    private Map<Integer, Integer> answers = new HashMap<>();
    private Map<Integer, Integer> correct = new HashMap<>();
    private List<Result> result = new ArrayList<>();

    private class Correct{
        private int id;
        private int count;
        Correct(ResultSet res) throws SQLException{
            count = res.getInt(CORRECT);
            id = res.getInt(ATTEMPTS_PROBLEM_ID);
            correct.put(id, count);
            //System.out.println("Correct: " + correct);
        }
    }

    private class Answer {
        private int id;
        private String op;
        private int arg1;
        private int arg2;
        private int ans;

        Answer(ResultSet res) throws SQLException {
            id = res.getInt(PROBLEM_ID_NAME);
            op = res.getString(PROBLEM_OP_NAME);
            arg1 = res.getInt(PROBLEM_ARG1_NAME);
            arg2 = res.getInt(PROBLEM_ARG2_NAME);
            ans = getAnswer(arg1, op, arg2);
            answers.put(id, ans);
        }
    }

    private class Result {
        private int id;
        private int attempts;
        private double percentage;
        DecimalFormat df = new DecimalFormat("0%");

        public String toString() {
            return "Problem:" + id + " #Attempts:" + attempts + " %Percentage:" + df.format(percentage);
        }

        Result(ResultSet res) throws SQLException {
            id = res.getInt(ATTEMPTS_PROBLEM_ID);
            attempts = res.getInt(COUNT);
            percentage = getPercentage(correct.get(id), attempts);
            result.add(this);
            System.out.println("RETRIEVED: " + this);
        }

        void print(CSVPrinter printer) throws IOException {
            printer.printRecord(id, attempts, df.format(percentage));
        }
    }

    // Implementation of the "readInput" method as specified by the base-class.
    protected void readInput() throws IOException {
        problemsInputTable.readFromFile(csvHandler, PROBLEMS_IN_FILE);
        attemptsInputTable.readFromFile(csvHandler, ATTEMPTS_IN_FILE);
    }

    // Implementation of the "runCoreProcess" method as specified by the base-class.
    protected void runCoreProcess() throws SQLException {
        // Create "ATTEMPTS" table in database
        database.createTable(ATTEMPTS_TABLE_NAME, ATTEMPTS_TABLE_CREATION_ARGS);
        attemptsInputTable.writeToDatabase(database, ATTEMPTS_TABLE_NAME);
        // Create "PROBLEMS" table in database
        database.createTable(PROBLEMS_TABLE_NAME, PROBLEMS_TABLE_CREATION_ARGS);
        problemsInputTable.writeToDatabase(database, PROBLEMS_TABLE_NAME);

        ResultSet res = database.executeQuery(SELECT_ALL_PROBLEMS_QUERY);
        while (res.next()) {
            new Answer(res);
        }

        for (Integer key : answers.keySet()) {
            String query
                    = " SELECT PROBLEM_ID , COUNT(*) AS " + CORRECT +
                    " FROM " + ATTEMPTS_TABLE_NAME +
                    " WHERE PROBLEM_ID = " + key + " AND " + "ANSWER = " + answers.get(key) +
                    " GROUP BY PROBLEM_ID";
            ResultSet correct = database.executeQuery(query);
            while (correct.next()) {
                new Correct(correct);
            }
        }
        ResultSet temp = database.executeQuery(SOLUTION_QUERY);
        while (temp.next()) {
            new Result(temp);
        }

    }

    private int getAnswer(int arg1, String op, int arg2) {
        if (op.equals("+")) {
            return arg1 + arg2;
        } else if (op.equals("-")) {
            return arg1 - arg2;
        } else if (op.equals("*")) {
            return arg1 * arg2;
        } else if (op.equals("/")) {
            return arg1 / arg2;
        } else {
            return 0;
        }
    }

    private double getPercentage(Integer arg1, Integer arg2) {
        if (arg1 == null) {
            return 0;
        } else {
            return (double)arg1 / (double)arg2;
        }
    }

    protected void writeOutput() throws IOException {

        File outCSVFile = getOutputFile();
        CSVPrinter printer = csvHandler.createPrinter(outCSVFile);
        for (Result r : result) {
            r.print(printer);
        }
    }

    // Constructor.
    Q3Process(Database database, CSVHandler csvHandler) {

        this.database = database;
        this.csvHandler = csvHandler;
    }
}
