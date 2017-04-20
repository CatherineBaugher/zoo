import java.io.*;
import java.sql.*;
import java.util.Scanner;

class ticketSeller{
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

		q = "select distinct SSN, Salary, BDate, Department from employee where SSN like '%";
		q += readEntry("Please enter the last 5 digits of your social security number>: ");
		q += "'";
		ResultSet rset = stmt.executeQuery(q);
		int recCount = 0;
		while(rset.next()){
			recCount++;
		}
		if(recCount > 1){ //making sure the 5 digits are unique, else ask to consult supervisor or admin
			System.out.println("Unique abbreviation error, please contact an administrator");
		}else{
			String department = rset.getString(4);
			char view = 'r'; //dummy value
			int gid;
			ticket tick = new ticket();
			if(department != "IT" && department != "Administration"){ //IT and admin have a different view, they dont sell tickets.
				while(view != 'q' && view != 'Q'){
					System.out.println("=-=:=-=:=-=: Zoo Database Interfacing System :=-=:=-=:=-=");
					System.out.println("(1) Sell a ticket");
					System.out.println("(2) Lookup paid amount for reimbursal");
					System.out.println("(Q) Quit");
					System.out.printf("Select input>: ");
					view = (char)System.in.read();
					if(view == '1'){
						FileOutputStream output = new FileOutputStream("gid.txt", false);
						Scanner scan = new Scanner(new File("gid.txt"));
						if(department == "Fun and Guest Services"){ //if department of guest services, the ticket will be new. I import from a file as prototype
							view = 'f';
							gid = scan.nextInt();
							output.write(gid+1);
						}else{ //customer already has an entry ticket. load the ride onto it
							view = 'a';
							System.out.print("Enter guest ID>: ");
							gid = System.in.read();
						}
						System.out.print("Welcome to the "+ department + " ticket selling interface.\n");
						System.out.println("What is the age range of the patron?");
						int age = tick.get_age();
						System.out.println("Specify pricing");
						double price = tick.get_price(view,age);
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