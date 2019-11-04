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
import java.util.ArrayList;
import java.util.List;

//
// THIS CLASS IS A TEMPLATE WHOSE IMPLEMENTATION SHOULD BE PROVIDED
// BY THE STUDENT IN ORDER TO PROVIDE A SOLUTION TO PROBLEM 1.
//
// THE "ExampleProcess" CLASS SHOULD BE USED AS A GUIDE IN CREATING
// THE IMPLEMENTATION.
//
class Q1Process extends DatabaseProcess {

    static private final File ATTEMPTS_IN_FILE = getInputFile("attempts");
    static private final String ATTEMPTS_TABLE_NAME = "ATTEMPTS";

    static private final File PEOPLE_IN_FILE = getInputFile("people");
    static private final String PEOPLE_TABLE_NAME = "PEOPLE";
    static private final String COUNT = "COUNT";

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

    static private final String SELECT_ANSWERED_PROBLEMS_QUERY
            = " SELECT p.LAST_NAME, p.FIRST_NAME, COUNT( a.PERSON_ID ) AS COUNT " +
            " FROM ATTEMPTS a, PEOPLE p " +
            " WHERE a.PERSON_ID = p.ID " +
            " GROUP BY p.LAST_NAME, p.FIRST_NAME ";

    private Database database;
    private CSVHandler csvHandler;

    private InputTable attemptsInputTable = new InputTable();
    private InputTable peopleInputTable = new InputTable();

    private List<Result> result = new ArrayList<Result>();


    private class Result {
        String last_name;
        String first_name;
        int answer;

        public String toString() {
            return "Last Name:" + last_name + "First Name:" + first_name + "#Answered:" + answer;
        }

        Result(ResultSet results) throws SQLException {
            last_name = results.getString(PEOPLE_LAST_NAME);
            first_name = results.getString(PEOPLE_FIRST_NAME);
            answer = results.getInt(COUNT);
            result.add(this);
            System.out.println("RETRIEVED: " + this);
        }

        // Render problem as a line in the output CSV file
        void print(CSVPrinter printer) throws IOException {
            printer.printRecord(last_name, first_name, answer);
        }
    }

    protected void readInput() throws IOException {
        attemptsInputTable.readFromFile(csvHandler, ATTEMPTS_IN_FILE);
        peopleInputTable.readFromFile(csvHandler, PEOPLE_IN_FILE);
    }

    protected void runCoreProcess() throws SQLException {
        // Create "ATTEMPTS" table in database
        database.createTable(ATTEMPTS_TABLE_NAME, ATTEMPTS_TABLE_CREATION_ARGS);
        attemptsInputTable.writeToDatabase(database, ATTEMPTS_TABLE_NAME);
        // Create "PEOPLE" table in database
        database.createTable(PEOPLE_TABLE_NAME, PEOPLE_TABLE_CREATION_ARGS);
        peopleInputTable.writeToDatabase(database, PEOPLE_TABLE_NAME);
        ResultSet results = database.executeQuery(SELECT_ANSWERED_PROBLEMS_QUERY);
        // Save query results as array of Problems objects
        while (results.next()) {
            new Result(results);
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
    Q1Process(Database database, CSVHandler csvHandler) {
        this.database = database;
        this.csvHandler = csvHandler;
    }
}
