import java.io.*;
import java.sql.*;

class ticketSeller{
	public static void main(String args[])
		throws SQLException, IOException{

		//Connect to machine
		String user, pass;
		user = readEntry("UserID>: ");
		pass = readEntry("Password>: ");
		Connection conn = DriverManager.getConnection ("jdbc:oracle:thin:@deuce.cs.ohiou.edu:1521:class", user, pass);

		do{
		}
	}
}