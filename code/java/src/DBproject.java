/*
 * Template JAVA User Interface
 * =============================
 *
 * Database Management Systems
 * Department of Computer Science &amp; Engineering
 * University of California - Riverside
 *
 * Target DBMS: 'Postgres'
 *
 */


import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * This class defines a simple embedded SQL utility class that is designed to
 * work with PostgreSQL JDBC drivers.
 *
 */

public class DBproject{
	//reference to physical database connection
	private Connection _connection = null;
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	public DBproject(String dbname, String dbport, String user, String passwd) throws SQLException {
		System.out.print("Connecting to database...");
		try{
			// constructs the connection URL
			String url = "jdbc:postgresql://localhost:" + dbport + "/" + dbname;
			System.out.println ("Connection URL: " + url + "\n");
			
			// obtain a physical connection
	        this._connection = DriverManager.getConnection(url, user, passwd);
	        System.out.println("Done");
		}catch(Exception e){
			System.err.println("Error - Unable to Connect to Database: " + e.getMessage());
	        System.out.println("Make sure you started postgres on this machine");
	        System.exit(-1);
		}
	}
	
	/**
	 * Method to execute an update SQL statement.  Update SQL instructions
	 * includes CREATE, INSERT, UPDATE, DELETE, and DROP.
	 * 
	 * @param sql the input SQL string
	 * @throws java.sql.SQLException when update failed
	 * */
	public void executeUpdate (String sql) throws SQLException { 
		// creates a statement object
		Statement stmt = this._connection.createStatement ();

		// issues the update instruction
		stmt.executeUpdate (sql);

		// close the instruction
	    stmt.close ();
	}//end executeUpdate

	/**
	 * Method to execute an input query SQL instruction (i.e. SELECT).  This
	 * method issues the query to the DBMS and outputs the results to
	 * standard out.
	 * 
	 * @param query the input query string
	 * @return the number of rows returned
	 * @throws java.sql.SQLException when failed to execute the query
	 */
	public int executeQueryAndPrintResult (String query) throws SQLException {
		//creates a statement object
		Statement stmt = this._connection.createStatement ();

		//issues the query instruction
		ResultSet rs = stmt.executeQuery (query);

		/*
		 *  obtains the metadata object for the returned result set.  The metadata
		 *  contains row and column info.
		 */
		ResultSetMetaData rsmd = rs.getMetaData ();
		int numCol = rsmd.getColumnCount ();
		int rowCount = 0;
		
		//iterates through the result set and output them to standard out.
		boolean outputHeader = true;
		while (rs.next()){
			if(outputHeader){
				for(int i = 1; i <= numCol; i++){
					System.out.print(rsmd.getColumnName(i) + "\t");
			    }
			    System.out.println();
			    outputHeader = false;
			}
			for (int i=1; i<=numCol; ++i)
				System.out.print (rs.getString (i) + "\t");
			System.out.println ();
			++rowCount;
		}//end while
		stmt.close ();
		return rowCount;
	}
	
	/**
	 * Method to execute an input query SQL instruction (i.e. SELECT).  This
	 * method issues the query to the DBMS and returns the results as
	 * a list of records. Each record in turn is a list of attribute values
	 * 
	 * @param query the input query string
	 * @return the query result as a list of records
	 * @throws java.sql.SQLException when failed to execute the query
	 */
	public List<List<String>> executeQueryAndReturnResult (String query) throws SQLException { 
		//creates a statement object 
		Statement stmt = this._connection.createStatement (); 
		
		//issues the query instruction 
		ResultSet rs = stmt.executeQuery (query); 
	 
		/*
		 * obtains the metadata object for the returned result set.  The metadata 
		 * contains row and column info. 
		*/ 
		ResultSetMetaData rsmd = rs.getMetaData (); 
		int numCol = rsmd.getColumnCount (); 
		int rowCount = 0; 
	 
		//iterates through the result set and saves the data returned by the query. 
		boolean outputHeader = false;
		List<List<String>> result  = new ArrayList<List<String>>(); 
		while (rs.next()){
			List<String> record = new ArrayList<String>(); 
			for (int i=1; i<=numCol; ++i) 
				record.add(rs.getString (i)); 
			result.add(record); 
		}//end while 
		stmt.close (); 
		return result; 
	}//end executeQueryAndReturnResult
	
	/**
	 * Method to execute an input query SQL instruction (i.e. SELECT).  This
	 * method issues the query to the DBMS and returns the number of results
	 * 
	 * @param query the input query string
	 * @return the number of rows returned
	 * @throws java.sql.SQLException when failed to execute the query
	 */
	public int executeQuery (String query) throws SQLException {
		//creates a statement object
		Statement stmt = this._connection.createStatement ();

		//issues the query instruction
		ResultSet rs = stmt.executeQuery (query);

		int rowCount = 0;

		//iterates through the result set and count nuber of results.
		if(rs.next()){
			rowCount++;
		}//end while
		stmt.close ();
		return rowCount;
	}
	
	/**
	 * Method to fetch the last value from sequence. This
	 * method issues the query to the DBMS and returns the current 
	 * value of sequence used for autogenerated keys
	 * 
	 * @param sequence name of the DB sequence
	 * @return current value of a sequence
	 * @throws java.sql.SQLException when failed to execute the query
	 */
	
	public int getCurrSeqVal(String sequence) throws SQLException {
		Statement stmt = this._connection.createStatement ();
		
		ResultSet rs = stmt.executeQuery (String.format("Select currval('%s')", sequence));
		if (rs.next()) return rs.getInt(1);
		return -1;
	}

	/**
	 * Method to close the physical connection if it is open.
	 */
	public void cleanup(){
		try{
			if (this._connection != null){
				this._connection.close ();
			}//end if
		}catch (SQLException e){
	         // ignored.
		}//end try
	}//end cleanup

	/**
	 * The main execution method
	 * 
	 * @param args the command line arguments this inclues the <mysql|pgsql> <login file>
	 */
	public static void main (String[] args) {
		if (args.length != 3) {
			System.err.println (
				"Usage: " + "java [-classpath <classpath>] " + DBproject.class.getName () +
		            " <dbname> <port> <user>");
			return;
		}//end if
		
		DBproject esql = null;
		
		try{
			System.out.println("(1)");
			
			try {
				Class.forName("org.postgresql.Driver");
			}catch(Exception e){

				System.out.println("Where is your PostgreSQL JDBC Driver? " + "Include in your library path!");
				e.printStackTrace();
				return;
			}
			
			System.out.println("(2)");
			String dbname = args[0];
			String dbport = args[1];
			String user = args[2];
			
			esql = new DBproject (dbname, dbport, user, "");
		
			boolean keepon = true;
			while(keepon){
				System.out.println("MAIN MENU");
				System.out.println("---------");
				System.out.println("1. Add Plane");
				System.out.println("2. Add Pilot");
				System.out.println("3. Add Flight");
				System.out.println("4. Add Technician");
				System.out.println("5. Book Flight");
				System.out.println("6. List number of available seats for a given flight.");
				System.out.println("7. List total number of repairs per plane in descending order");
				System.out.println("8. List total number of repairs per year in ascending order");
				System.out.println("9. Find total number of passengers with a given status");
				System.out.println("10. < EXIT");
				
				switch (readChoice()){
					case 1: AddPlane(esql); break;
					case 2: AddPilot(esql); break;
					case 3: AddFlight(esql); break;
					case 4: AddTechnician(esql); break;
					case 5: BookFlight(esql); break;
					case 6: ListNumberOfAvailableSeats(esql); break;
					case 7: ListsTotalNumberOfRepairsPerPlane(esql); break;
					case 8: ListTotalNumberOfRepairsPerYear(esql); break;
					case 9: FindPassengersCountWithStatus(esql); break;
					case 10: keepon = false; break;
				}
			}
		}catch(Exception e){
			System.err.println (e.getMessage ());
		}finally{
			try{
				if(esql != null) {
					System.out.print("Disconnecting from database...");
					esql.cleanup ();
					System.out.println("Done\n\nBye !");
				}//end if				
			}catch(Exception e){
				// ignored.
			}
		}
	}

	public static int readChoice() {
		int input;
		// returns only if a correct value is given.
		do {
			System.out.print("Please make your choice: ");
			try { // read the integer, parse it and break.
				input = Integer.parseInt(in.readLine());
				break;
			}catch (Exception e) {
				System.out.println("Your input is invalid!");
				continue;
			}//end try
		}while (true);
		return input;
	}//end readChoice

	public static void AddPlane(DBproject esql) {//1
		// Used to grab data from awt
		InputVessel make_vessel = new InputVessel();
		InputVessel model_vessel = new InputVessel();
		InputVessel age_vessel = new InputVessel();
		InputVessel seats_vessel = new InputVessel();

		InputVessel[] vessels = {make_vessel, model_vessel, age_vessel, seats_vessel};

		// Spawns window for user interface
		QueryUI qui = new AddPlaneUI(vessels); 
		
		// Starts UI thread
		qui.pollInput();

		// Can grab values from the UI now
	   	String make = make_vessel.getValue();
	   	String model = model_vessel.getValue();
		String age = age_vessel.getValue();
		String seats = seats_vessel.getValue();
		
		if (qui.getDoQuery()) {	
			try {
				String query = "INSERT INTO Plane (make, model, age, seats) VALUES (\'"
					+ make + "\', \'" + model + "\', " + age + ", " + seats + ")";
			
				esql.executeUpdate(query);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("Query cancelled");
		}
	}

	public static void AddPilot(DBproject esql) {//2
	}

	public static void AddFlight(DBproject esql) {//3
		// Given a pilot, plane and flight, adds a flight in the DB
		//InputVessel fnum_vessel = new InputVessel();
		InputVessel cost_vessel = new InputVessel();
		InputVessel sold_vessel = new InputVessel();
		InputVessel stops_vessel = new InputVessel();
		InputVessel departure_date_vessel = new InputVessel();
		InputVessel arrival_date_vessel = new InputVessel();
		InputVessel arrival_vessel = new InputVessel();
		InputVessel departure_vessel = new InputVessel();

		// Doesn't include fnum (Generate it from a sequence)
		InputVessel[] vessels = {cost_vessel, sold_vessel, stops_vessel, departure_date_vessel, arrival_date_vessel, arrival_vessel, departure_vessel};

		QueryUI qui = new AddFlightUI(vessels);

		qui.pollInput();

		//String fnum = fnum_vessel.getValue();
		String cost = cost_vessel.getValue();
		String sold = sold_vessel.getValue();
		String stops = stops_vessel.getValue();
		String departure_date = departure_date_vessel.getValue();
		String arrival_date = arrival_date_vessel.getValue();
		String arrival = arrival_vessel.getValue();
		String departure = departure_vessel.getValue();

		if (qui.getDoQuery()) {
			try {
				String q = "INSERT INTO Flight (cost, num_sold, num_stops, actual_departure_date, actual_arrival_date, arrival_airport, departure_airport) VALUES ("
					+ cost + ", " + sold + ", " + stops 
					+ ", \'" + departure_date + "\', "
			 		+ "\'" + arrival_date + "\', "
					+ "\'" + arrival + "\', "
					+ "\'"	+ departure + "\'"
					+ ")";

				esql.executeUpdate(q);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("Query cancelled");
		}
	}

	public static void AddTechnician(DBproject esql) {//4
		InputVessel name_vessel = new InputVessel();
		
		// Doesn't include fnum (Generate it from a sequence)
		InputVessel[] vessels = { name_vessel };

		QueryUI qui = new AddTechUI(vessels);

		qui.pollInput();

		String name = name_vessel.getValue();
		
		if (qui.getDoQuery()) {
			try {
				String q = "INSERT INTO Technician (full_name) VALUES (\'" + name +"\')";
				esql.executeUpdate(q);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("Query cancelled");
		}		
	}

	public static void BookFlight(DBproject esql) {//5
		// Given a customer and a flight that he/she wants to book, add a reservation to the DB
		
		InputVessel cid_vessel = new InputVessel();
		InputVessel fid_vessel = new InputVessel();

		InputVessel[] vessels = {cid_vessel, fid_vessel};
		
		QueryUI qui = new MakeReservation(vessels);

		qui.pollInput();

		String cid = cid_vessel.getValue();
		String fid = fid_vessel.getValue();
		
		// Get info about flight pointed to by fid	
		try {
			// This should return only one row. From here, we will get plane info
			String q = "SELECT * FROM FlightInfo WHERE flight_id = " + fid;
			List<List<String>> flightInfo = esql.executeQueryAndReturnResult(q);	
			
			q = "SELECT num_sold FROM Flight WHERE fnum = " + fid;
			List<List<String>> flights = esql.executeQueryAndReturnResult(q);
			String sold = flights.get(0).get(0);

			String plane_id = flightInfo.get(0).get(3);
			q = "SELECT * FROM Plane WHERE id = " + plane_id;
			List<List<String>> plane = esql.executeQueryAndReturnResult(q);

			// Calculate if a person can actually get a seat on this flight
			String seats = plane.get(0).get(4);

			int int_sold = Integer.parseInt(sold);
			int int_seats = Integer.parseInt(seats);

			if (int_sold < int_seats) {
				qui.displayMessage("Congratulations! Seat Reserved!");
				q = "INSERT INTO Reservation (cid, fid, status) VALUES ("+cid+", "+fid+", 'R')";
				esql.executeUpdate(q);
			} else {
				qui.displayMessage("You are on the Waiting List.");
				q = "INSERT INTO Reservation (cid, fid, status) VALUES ("+cid+", "+fid+", 'W')";
				esql.executeUpdate(q);

			}
		
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void ListNumberOfAvailableSeats(DBproject esql) {//6
		// For flight number and date, find the number of availalbe seats (i.e. total plane capacity minus booked seats )
		InputVessel fnum_vessel = new InputVessel();
		//InputVessel date_vessel = new InputVessel();

		InputVessel[] vessels = {fnum_vessel};//, date_vessel};

		QueryUI qui = new GetSeatsUI(vessels);

		qui.pollInput();

		String fnum = fnum_vessel.getValue();
		//String date = date_vessel.getValue();
		
		try {
			String q = "SELECT DISTINCT seats - num_sold FROM Flight, Reservation, Plane WHERE fnum=" + fnum + " AND fid = fnum";

			List<List<String>> rows = esql.executeQueryAndReturnResult(q);

			ResultsUI rui = new ResultsUI("Available Seats on Flight " + fnum, rows);
			rui.createUI();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void ListsTotalNumberOfRepairsPerPlane(DBproject esql) {//7
		// Count number of repairs per planes and list them in descending order
	}

	public static void ListTotalNumberOfRepairsPerYear(DBproject esql) {//8
		// Count repairs per year and list them in ascending order
	}
	
	public static void FindPassengersCountWithStatus(DBproject esql) {//9
		// Find how many passengers there are with a status (i.e. W,C,R) and list that number.
	}
}
