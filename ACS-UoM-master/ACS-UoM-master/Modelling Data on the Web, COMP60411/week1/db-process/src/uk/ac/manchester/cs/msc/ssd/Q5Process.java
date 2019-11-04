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

//
// THIS CLASS IS A TEMPLATE WHOSE IMPLEMENTATION SHOULD BE PROVIDED
// BY THE STUDENT IN ORDER TO PROVIDE A SOLUTION TO PROBLEM 1.
//
// THE "ExampleProcess" CLASS SHOULD BE USED AS A GUIDE IN CREATING
// THE IMPLEMENTATION.
// 5) Six columns:
// A's Last Name, A's First Name, B's Last Name, B's First Name,  m,  # Problems Answered by both in same way.
// This report should contain the list of all pairs of students A, B such that, for m the minimum number of questions attempted by A or by B,
// half of m questions have been both attempted by A and by B and they have both given the same answers to these.
//
class Q5Process extends DatabaseProcess {
	static private final File ATTEMPTS_IN_FILE = getInputFile("attempts");
	static private final String ATTEMPTS_TABLE_NAME = "ATTEMPTS";

	static private final File PROBLEMS_IN_FILE = getInputFile("problems");
	static private final String PROBLEMS_TABLE_NAME = "PROBLEMS";

	static private final File PEOPLE_IN_FILE = getInputFile("people");
	static private final String PEOPLE_TABLE_NAME = "PEOPLE";
	static private final String COUNT = "count";

	// Names of columns for "ATTEMPTS" table
	static private final String ATTEMPTS_PERSON_ID = "PERSON_ID";
	static private final String ATTEMPTS_PROBLEM_ID = "PROBLEM_ID";
	static private final String ATTEMPTS_ANSWER = "ANSWER";
	// Names of columns for "PEOPLE" table
	static private final String PEOPLE_PERSON_ID = "PERSON_ID";
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
			+ PEOPLE_FIRST_NAME + " char(20), "
			+ PEOPLE_LAST_NAME + " char(20), "
			+ PEOPLE_COMPANY_NAME + " char(50), "
			+ PEOPLE_ADDRESS + " char(50), "
			+ PEOPLE_CITY + " char(50), "
			+ PEOPLE_COUNTRY + " char(50), "
			+ PEOPLE_POSTAL + " char(50), "
			+ PEOPLE_PHONE1 + " char(50), "
			+ PEOPLE_PHONE2 + " char(50), "
			+ PEOPLE_EMAIL + " char(50), "
			+ PEOPLE_WEB + " char(50)";

	static private final String PROBLEMS_TABLE_CREATION_ARGS
			= PROBLEM_ID_NAME + " integer NOT NULL, "
			+ PROBLEM_ARG1_NAME + " integer NOT NULL, "
			+ PROBLEM_OP_NAME + " char(1), "
			+ PROBLEM_ARG2_NAME + " integer NOT NULL";

	static private final String SELECT_ATTEMPTS_QUERY
			= " SELECT * FROM ATTEMPTS ORDER BY PROBLEM_ID ";

	private Database database;
	private CSVHandler csvHandler;

	private InputTable attemptsInputTable = new InputTable();
	private InputTable peopleInputTable = new InputTable();
	private InputTable problemsInputTable = new InputTable();

	private List<Result> result = new ArrayList<Result>();
	private List<Attempt> attempts = new ArrayList<Attempt>();
	private Map<problemAnswer,List<Integer>> attemptPeople = new HashMap<problemAnswer,List<Integer>>();
	private Map<Integer,List<problemAnswer>> person = new HashMap<Integer, List<problemAnswer>>();
	private Map<twoPeople,List<Integer>> peopleResult = new HashMap<twoPeople, List<Integer>>();

	public class twoPeople {
		public int personID1;
		public int personID2;

		public twoPeople (int p1, int p2) {
			this.personID1 = p1;
			this.personID2 = p2;
		}

		public  String toString() {
			return "PeopleID: "+ personID1 + " ,PeopleID: " + personID2;
		}

		@Override
		public int hashCode() {
			final int prime = 33;
			int result = 1;
			result = prime * result + personID1;
			result = prime * result + personID2;
			return result;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			twoPeople tp = (twoPeople) o;

			if (tp.personID1 != personID1 && tp.personID2 != personID2) {
				return false;
			}
			return true;
		}
	}

	public class problemAnswer {
		public int problemID;
		public int answer;

		public String toString() {
			return "problemID: " + problemID + " ,answer: " + answer;
		}

		public problemAnswer(int p, int a) {
			this.problemID = p;
			this.answer = a;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + answer;
			result = prime * result + problemID;
			return result;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			problemAnswer p = (problemAnswer) o;

			if (p.problemID != problemID && p.answer != answer) {
				return false;
			}
			return true;
		}
	}

	private class Attempt {
		private int personID;
		private int problemID;
		private int answer;


		public String toString() {
			return "problemID: " + problemID + " ,answer: " + answer + " ,personID: " + personID;
		}

		Attempt(ResultSet results) throws SQLException {
			personID = results.getInt(ATTEMPTS_PERSON_ID);
			problemID = results.getInt(ATTEMPTS_PROBLEM_ID);
			answer = results.getInt(ATTEMPTS_ANSWER);
			if(person.containsKey(personID)) {
				List<problemAnswer> list = person.get(personID);
				list.add(new problemAnswer(problemID,answer));
				person.put(personID,list);
			} else {
				List<problemAnswer> list = new ArrayList<problemAnswer>();
				list.add(new problemAnswer(problemID,answer));
				person.put(personID,list);
			}

			if (!attemptPeople.containsKey(new problemAnswer(problemID, answer))) {
				List<Integer> list = new ArrayList<Integer>();
				list.add(personID);
				attemptPeople.put(new problemAnswer(problemID, answer),list);
			} else {
				attemptPeople.get(new problemAnswer(problemID, answer)).add(personID);
			}


//			attempts.add(this);
//			System.out.println("RETRIEVED: " + this);
		}
	}

	private class Result {
		String p1LName;
		String p1FName;
		String p2LName;
		String p2FName;
		int m;
		int problemAnswered;

		public Result(String s1, String s2, String s3, String s4, int m1, int n) {
			this.p1LName = s1;
			this.p1FName = s2;
			this.p2LName = s3;
			this.p2FName = s4;
			this.m = m1;
			this.problemAnswered = n;
		}

		public String toString() {
			return "People1's Last Name: " + p1LName + ", People1's First Name: " + p1FName +
					" ,People2's Last Name: " + p2LName + ", People2's First Name: " + p2FName +
					" ,M: " + m + " ,#Problem Answered:" + problemAnswered;
		}

		void print(CSVPrinter printer) throws IOException {
			printer.printRecord(p1LName, p1FName, p2LName, p1FName, m, problemAnswered);
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

		ResultSet results = database.executeQuery(SELECT_ATTEMPTS_QUERY);
		while (results.next()) {
			new Attempt(results);
		}

//		for(Map.Entry<Integer,List<problemAnswer>> e : person.entrySet()) {
//			System.out.println("Key: " + e.getKey() + ", value:" + e.getValue());
//		}

//		System.out.println();

		for(Map.Entry<problemAnswer,List<Integer>> e : attemptPeople.entrySet()) {
//			System.out.println("Key: " + e.getKey() + ", value:" + e.getValue());
			if (e.getValue().size() >= 2) {
				for(int i=0;i<=e.getValue().size()-1;i++) {
					for(int j=i+1;j<e.getValue().size();j++) {
//						System.out.println(e.getValue().get(i)+" , "+e.getValue().get(j));
						twoPeople tp = new twoPeople(e.getValue().get(i),e.getValue().get(j));
						twoPeople tp2 = new twoPeople(e.getValue().get(j),e.getValue().get(i));
						if(!peopleResult.containsKey(tp) && !peopleResult.containsKey(tp2)) {
							List<Integer> list = new ArrayList<Integer>();
							list.add(e.getKey().problemID);
							peopleResult.put(tp,list);
						} else if (peopleResult.containsKey(tp)) {
							List<Integer> list = peopleResult.get(tp);
							list.add(e.getKey().problemID);
							peopleResult.put(tp,list);
						} else if (peopleResult.containsKey(tp2)) {
							List<Integer> list = peopleResult.get(tp2);
							list.add(e.getKey().problemID);
							peopleResult.put(tp2,list);
						}
					}
				}
			}
		}

		for (Map.Entry<twoPeople,List<Integer>> e : peopleResult.entrySet()) {
//			System.out.println("Key: " + e.getKey() + ", value:" + e.getValue());
			int p1Id = e.getKey().personID1;
			int p2Id = e.getKey().personID2;
			int m1 = person.get(p1Id).size();
			int m2 = person.get(p2Id).size();
			int m = m1<m2? m1:m2;
			if (e.getValue().size() >= 1/m) {
				String query = " SELECT LAST_NAME, FIRST_NAME FROM PEOPLE WHERE PERSON_ID = " + p1Id +" ";
				String query2 = " SELECT LAST_NAME, FIRST_NAME FROM PEOPLE WHERE PERSON_ID = " + p2Id +" ";
				ResultSet people1 = database.executeQuery(query);
				ResultSet people2 = database.executeQuery(query2);
				while (people1.next() && people2.next()) {
					Result r = new Result(people1.getString(PEOPLE_LAST_NAME),people1.getString(PEOPLE_FIRST_NAME),
							people2.getString(PEOPLE_LAST_NAME),people2.getString(PEOPLE_FIRST_NAME),m,e.getValue().size());
					result.add(r);
					System.out.println("RETRIEVED: " + r);
				}
			}
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
	Q5Process(Database database, CSVHandler csvHandler) {
		this.database = database;
		this.csvHandler = csvHandler;
	}
}
