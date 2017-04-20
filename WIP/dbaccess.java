//This program prints everything associated to an employee, use to test connection and database access


//importing JDBC library
import java.sql.*;
import java.io.*;
import java.util.Date;
class DBAccess{
	public static void main(String args[]) throws SQLException, IOException{
		//loading JDBC driver
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(ClassNotFoundException e){
			System.out.println("Could not load the driver");
		}
	}

	//defining appropriate variables
	String dbuser, dbpass;
	dbuser = readEntry("Enter your username: ");
	dbpass = readEntry("Enter your password: ");
	
	//creating connect object
	//NOTE check: do we use oci8 or oci7?
	Connection conn = DriverManager.getConnection("jdbc:oracle:oci8:"+dbuser+"/"+dbpass);

	//creating statement object
	Statement s = conn.createStatement();

	//indentifying statement parameters
	String q = "select DateEmployed, Salary, EBday, Department from EMPLOYEE where SSN = " + readentry("Enter SSN: ");
	ResultSet r = s.executeQuery(q);

	//binding parameters to program variables
	String department;
	Date ebday,dateemployed; //NOTE check this
	Double salary;
	while(r.next()){
		dateemployed = r.getDate(1);
		salary = r.getDouble(2);
		ebday = r.getDate(3);
		department = r.getString(4);
		system.out.printline(dateemployed+salary+ebday+department);
	}
	conn.close();

};