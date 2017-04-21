//this file holds functions relevant for the admin view. it generates SQL statements.
import java.util.Scanner;
import java.sql.*;
import java.io.*;
class admin{
	static Scanner scan = new Scanner(System.in);
	String clear_records(){
		char select = 'r';
		System.out.println("Preparing to clear guest records. Which option would you like?");
		while(select != '1' && select != '2'){
			System.out.println("(1) Delete all records");
			System.out.println("(2) Delete from time point");
			System.out.println("(Q) Quit");
			System.out.printf("Select input>: ");
			select = scan.next().charAt(0);
		}
		if(select == '1'){
			return "delete from guest";
		}else if(select == '2'){
			String datebefore = readEntry("Enter in date to delete before in format YYYYMMDD>: ");
			return ("delete from guest where EntDate < to_date('" + datebefore + "','YYYYMMDD')");
		}else{
			return "null";
		}
	}
	static String readEntry(String prompt){
		try{
			StringBuffer buffer = new StringBuffer();
			System.out.print(prompt);
			System.out.flush();
			int c = System.in.read();
			while(c != '\n' && c != -1){
				buffer.append((char)c);
				c = System.in.read();
			}
			return buffer.toString().trim();
		}catch(IOException e){
			return "";
		}
	}

	String mod_emp(){
		char select = 'r';
		String ssn = "null";
		System.out.println("Preparing to modify employee records. Would you like to continue <Y:N>?");
		System.out.printf("Select input>: ");
		select = scan.next().charAt(0);
		if(select == 'y' || select == 'Y'){
			ssn = readEntry("Please enter SSN of employee you would like to modify>: ");
		}
		System.out.println("Which attribute would you like to modify?");
		while(select != '1' && select != '2' && select != '3' && select != '4'){
			System.out.println("(1) Date employed");
			System.out.println("(2) Birthday");
			System.out.println("(3) Department");
			System.out.println("(4) Salary");
			System.out.printf("Select input>: ");
			select = scan.next().charAt(0);
		}
		if(select == '1'){
			String newdemp = readEntry("Enter in new date employed in format YYYYMMDD>: ");
			return ("update employee set dateemployed = to_date('" + newdemp + "','YYYYMMDD') where SSN = '" + ssn + "'");
		}else if (select == '2'){
			String newbday = readEntry("Enter in new date of birth in format YYYYMMDD>: ");
			return ("update employee set ebday = to_date('" + newbday + "','YYYYMMDD') where SSN = '" + ssn + "'");
		}else if(select == '3'){
			String newdep = readEntry("Enter in new department>: ");
			return ("update employee set department = '" + newdep + "' where SSN = '" + ssn + "'");
		}else{
			String newsal = readEntry("Enter in new hourly rate>: ");
			return ("update employee set salary = " + newsal + " where SSN = '" + ssn + "'");
		}

	}
}