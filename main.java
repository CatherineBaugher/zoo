
import java.io.*;
import java.sql.*;
import java.util.Scanner;
import java.io.FileWriter;

class main{
	static Scanner in = new Scanner(System.in);
	public static void main(String args[])
		//load driver
		throws SQLException, IOException{
			try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			}catch(ClassNotFoundException e){
				System.out.println("Could not load the driver");
			}

		//Connect to machine
		String user, pass,q;
		user = readEntry("UserID>: ");
		pass = readEntry("Password>: ");
		Connection conn = DriverManager.getConnection ("jdbc:oracle:thin:@deuce.cs.ohiou.edu:1521:class", user, pass);
		Statement stmt = conn.createStatement();

		q = "select distinct SSN, Salary, EBDay, Department from employee where SSN like '%";
		q += readEntry("Please enter the last 5 digits of your social security number>: ");
		q += "'";
		ResultSet rset = stmt.executeQuery(q);
		int recCount = 0;
		String ssn = "null",department = "null";
		while(rset.next()){
			ssn = rset.getString(1).trim();
			department = rset.getString(4).trim();
			recCount++;
		}
		if(recCount > 1 || recCount == 0){ //making sure the 5 digits are unique, else ask to consult supervisor or admin
			System.out.println("Try again or contact an administrator");
		}else{
			char view = 'r'; //dummy value
			int age = 0,gid = 0;
			double price = 0;
			ticket tick = new ticket();
			admin a = new admin();
			if(!department.equals("IT") && !department.equals("Administration")){ //IT and admin have a different view, they dont sell tickets.
				while(view != 'q' && view != 'Q'){
					System.out.println("=-=:=-=:=-=: Zoo Database Interfacing System :=-=:=-=:=-=");
					System.out.println("(1) Sell a ticket");
					System.out.println("(2) Lookup paid amount for reimbursal");
					System.out.println("(Q) Quit");
					System.out.printf("Select input>: ");
					view = (char)System.in.read();
					if(view == '1'){
						FileWriter output = new FileWriter("gid.txt", true);//have to append so possible to write in-time. will fix output at end
						Scanner scan = new Scanner(new File("gid.txt"));
						if(department.substring(0,13).equals("Fun and Guest")){ //if department of guest experience, the ticket will be new. I import from a file as prototype
							age = tick.get_age();
							view = 'f';
							price = tick.get_price(view,age);
							while(scan.hasNext()){
								gid = Integer.parseInt(scan.next())+1;
							}
							output.write(" "+gid);
							output.flush();
							output.close();
						}else{ //customer already has an entry ticket. load the ride onto it
							view = 'a';
							age = tick.get_age();
							price = tick.get_price(view,age);
							System.out.print("Enter guest ID>: ");
							gid = in.nextInt();
						}
						q = ("insert into guest values (to_date('20170421','YYYYMMDD'), " + gid + ", " + price + ", " + age + ", " + ssn +")");
						try{
							int nrows = stmt.executeUpdate(q);
						}catch(SQLException e){
							System.out.println("Error Adding Catalog Entry");
							while(e != null){
								System.out.println("Message: " + e.getMessage());
								e = e.getNextException();
							}
							return;
						}
						System.out.println("Added guest entry");
					}
				}
			}else{
				System.out.println("=-=:=-=:=-=: Zoo Database Interfacing System :=-=:=-=:=-=");
				System.out.println("(1) Execute SQL queries directly");
				System.out.println("(2) Clear guest records");
				System.out.println("(3) Modify employee info");
				System.out.println("(3) Lookup guest-employee transaction");
				System.out.println("(Q) Quit");
				System.out.printf("Select input>: ");
				view = (char)System.in.read();
				if(view == '2'){
					q = a.clear_records();
					if(q != "null"){
						try{
							int nrows = stmt.executeUpdate(q);
						}catch(SQLException e){
							System.out.println(" ERROR Could not delete");
							System.out.println(q);
							while(e != null){
								System.out.println("Message: " + e.getMessage());
								e = e.getNextException();
							}
							conn.rollback();
							return;
						}
					}
				}else if(view == '3'){
					q = a.mod_emp();
					if(q != "null"){
						try{
							int nrows = stmt.executeUpdate(q);
						}catch(SQLException e){
							System.out.println(" ERROR Could not modify");
							System.out.println(q);
							while(e != null){
								System.out.println("Message: " + e.getMessage());
								e = e.getNextException();
							}
							conn.rollback();
							return;
						}
					}
				}
			}
		}

	conn.close();
	}


	//readEntry fn to read input
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
}