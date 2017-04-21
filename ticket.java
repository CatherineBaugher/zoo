//This file contains important interfaces/views for selling tickets
import java.util.Scanner;
import java.sql.*;
import java.io.*;

class ticket{
	static Scanner scan = new Scanner(System.in);
	int get_age(){
		char select = 'r';
		System.out.println("Please specify the age range of the patron.");
		while(select != '1' && select != '2' && select != '3' && select != '4'){
			System.out.println("(1) Infant - Less than 2 years old");
			System.out.println("(2) Child - Between 2 and 11 years");
			System.out.println("(3) Adult - Between 12 and 59 years");
			System.out.println("(4) Senior - 60 or older");
			System.out.printf("Select input>: ");
			select = scan.next().charAt(0);
		}
		switch(select){
			case '1':
				return 0;
			case '2':
				return 2;
			case '3':
				return 12;
			case '4':
				return 60;
			default:
				System.out.println("ERROR - Something went wrong. Please notify an administrator");
				return 0;
		}
	}
	double get_price(char view, int age){
		char select = 'r';
		double price = 0;
		if (age == 0) //note that infants (age < 2 years, signified in this DB by "0") are free no matter the ticket	
			return 0;
		if(view == 'f'){ //'f' signifies Department of Fun & Guest Experience -- this will be an entry ticket or single ride ticket
			System.out.println("=-=:=-=:=-=:  Entry Ticket  :=-=:=-=:=-=");
			System.out.println("(1) Zoo Adventure Ticket - $28 Adult, $21 Child/Sr");
			System.out.println("(2) Ride Package - $26 Adult, $20 Child/Sr");
			System.out.println("(3) Any Day Tickets - $19 Adult, $13 Child/Sr");
			System.out.println("(4) Single Carousel - $3 Adult/Child/Sr");
			System.out.println("(5) Single Train - $4 Adult/Child/Sr");
			System.out.println("(6) Theater Ticket - $9 Adult/Child/Sr");
			System.out.println("(Z) Zoo Bucks");
			System.out.println("(H) Help - View more info of what's included in tickets");
			System.out.println("(Q) Quit");
			System.out.printf("Select input>: ");
			select = scan.next().charAt(0);

			switch(select){
				case '1':
					if(age == 12)
						price = 28;
					else
						price = 21;
					break;
				case '2':
					if(age == 12)
						price = 26;
					else
						price = 20;
					break;
				case '3':
					if(age == 12)
						price = 19;
					else
						price = 13;
					break;
				case '4':
					price = 3;
					break;
				case '5':
					price = 4;
					break;
				case '6':
					price = 9;
					break;
				case 'q':
				case 'Q':
					return 0;
				case 'z':
				case 'Z':
					System.out.printf("Dollar amount>: ");
					price = scan.nextDouble();
					break;
				case 'H':
				case 'h':
					System.out.println("ZAT: Includes Zoo Admission, a short 4-D Experience, all-day rides on the express train and carousel, early entry to the zoo at 9am");
					System.out.println("Ride Package: Includes Zoo Admission, all-day rides on the express train and carousel");
					System.out.println("Any Day Tickets: Valid any one day during the regular operating season.");
					System.out.println("Single Carousel: Good for one ride on carousel");
					System.out.println("Single Train: Good for one ride on train around park");
					System.out.println("Theater Ticket: Good for one scheduled 3D viewing");
					System.out.println("Zoo Bucks: These can be spent anywhere in the zoo");
					return 0;
				default:
					System.out.println("ERROR - Invalid selection. Price not updated. Please try again");
					return 0;
			}
		}
		
		if(view == 'a'){ //signifies employee works in an animal exhibit. tickets are animal encounters/experiences.
			System.out.println("=-=:=-=:=-=:  Animal Encounters and Experiences  :=-=:=-=:=-=");
			System.out.println("(1) Feed Giraffe - $3");
			System.out.println("(2) Feed Camel - $3");
			System.out.println("(3) Petting Zoo - $1");
			System.out.println("(4) Up Close with the Cheetahs - $1");
			System.out.println("(5) Elephant Ride - $9");
			System.out.println("(Q) Quit");
			System.out.printf("Select input>: ");
			select = scan.next().charAt(0);
			System.out.println(select);

			switch(select){
				case ('1'):
				case ('2'):
					price = 3;
					break;
				case('3'):
				case('4'):
					price = 1;
					break;
				case('5'):
					price = 9;
					break;
				case('q'):
				case('Q'):
					return 0;
				default:
					System.out.println("ERROR - Invalid selection. Price not updated. Please try again");
					return 0;
			}
		}


		//calculating tax. in Ohio it is 5.75%
		price = (price*.0575)+price;
		return price;
	}
} 