//Author: Shyam
//Reviewer:Santosh
// Mediator : Aashish
// Facilitator: Ashmit

import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.lang.StringBuilder; // package is added to use StringBuilder() method


public class Main {
	
	private static Scanner scan; // variable IN  is changed to scan
	private static Library library; //renamed library to Library and variable LIB to library
	private static String menu; //replace variable MENU to menu
	private static Calendar calendar;  // avoid variable name CAL  and changed to calendar
	private static SimpleDateFormat dateFormat; //renamed variable SDF to SimplDateFormat
	
	
	private static String getMenu() {  //renamed method Get_menu to getMenu
		StringBuilder StringBuilder= new StringBuilder(); // sb changed to StringBuilder
		
		StringBuilder.append("\nLibrary Main Menu\n\n")
		  .append("  M  : add member\n")
		  .append("  LM : list members\n")
		  .append("\n")
		  .append("  B  : add book\n")
		  .append("  LB : list books\n")
		  .append("  FB : fix books\n")
		  .append("\n")
		  .append("  L  : take out a loan\n")
		  .append("  R  : return a loan\n")
		  .append("  LL : list loans\n")
		  .append("\n")
		  .append("  P  : pay fine\n")
		  .append("\n")
		  .append("  T  : increment date\n")
		  .append("  Q  : quit\n")
		  .append("\n")
		  .append("Choice : ");
		  
		return StringBuilder.toString();
	}


	public static void main(String[] args) {		
		try {			
			scan = new Scanner(System.in); //changed variable IN to scan
			library = Library.INSTANCE(); //change library to Library and variable LIB to library
			calendar = Calendar.getInstance(); //replace variable name CAL to calendar
			dateFormat = new SimpleDateFormat("dd/MM/yyyy"); //renamed variable SDF to dateFormat
	
			for (Member member : library.Members()) {  //renamed variable m to member and object declared member to Member
				output(member);
			}
			output(" ");
			for (Book book : library.Books()) {  //renamed variable b to book and object declared book to Book
				output(book);  
			}
						
			menu = getMenu();  //retitled variable MENU to menu
			
			boolean check = false; // change variable e to check
			
			while (!check) {
				
				output("\n" + dateFormat.format(calendar.Date()));  //changed variable name CAL to calendar and SDF to dateFormat
				String choice = input(menu);  //renamed variable MENU to menu and c to choice
				
				switch (choice.toUpperCase()) {
				
				case "M": 
					addMember();
					break;
					
				case "LM": 
					listMembers();
					break;
					
				case "B": 
					addBook();
					break;
					
				case "LB": 
					listBooks();
					break;
					
				case "FB": 
					fixBooks();
					break;
					
				case "L": 
					borrowBook();
					break;
					
				case "R": 
					returnBook();
					break;
					
				case "LL": 
					listCurrentLoans();
					break;
					
				case "P": 
					payFine();
					break;
					
				case "T": 
					incrementDate();
					break;
					
				case "Q": 
					e = true;
					break;
					
				default: 
					output("\nInvalid option\n");
					break;
				}
				
				Library.SAVE();  //change library to Library
			}			
		} catch (RuntimeException e) {
			output(e);
		}		
		output("\nEnded\n");
	}	

		private static void payFine() {
		new PayFineUI(new PayFineControl()).run();		
	}


	private static void listCurrentLoans() {
		output("");
		for (loan loan :library.CurrentLoans()) {
			output(loan + "\n");
		}		
	}


	private static void listBooks() {
		output("");
		for (book book : library.Books()) {  //renamed variable LIB to library
			output(book + "\n");
		}		
	}



	private static void listMembers() {
		output("");
		for (member member : library.Members()) {  //renamed variable LIB to library
			output(member + "\n");
		}		
	}


	private static void borrowBook() {
		new BorrowBookUI(new BorrowBookControl()).run();		
	}


	private static void returnBook() {
		new ReturnBookUI(new ReturnBookControl()).run();		
	}


	private static void fixBooks() {
		new FixBookUI(new FixBookControl()).run();		
	}


	private static void incrementDate() {
		try {
			int days = Integer.valueOf(input("Enter number of days: ")).intValue();
			calendar.incrementDate(days);  //changed variable name CAL to calendar
			library.checkCurrentLoans();  //renamed variable LIB to library
			output(date/fromat.format(calendar.Date()));  //renamed variable SDF to dateFormat and CAL to calendar
			
		} catch (NumberFormatException e) {
			 output("\nInvalid number of days\n");
		}
	}


	private static void addBook() {
		
		String author = input("Enter author: ");
		String title  = input("Enter title: ");
		String callNo = input("Enter call number: ");
		book book = library.Add_book(author, title, callNo);  //renamed variable LIB to library
		output("\n" + book + "\n");
		
	}

	
	private static void addMember() {
		try {
			String lastName = input("Enter last name: ");
			String firstName  = input("Enter first name: ");
			String email = input("Enter email: ");
			int phoneNo = Integer.valueOf(input("Enter phone number: ")).intValue();
			member member = library.Add_mem(lastName, firstName, email, phoneNo);  //renamed variable LIB to library
			output("\n" + member + "\n");
			
		} catch (NumberFormatException e) {
			 output("\nInvalid phone number\n");
		}
		
	}


	private static String input(String prompt) {
		System.out.print(prompt);
		return scan.nextLine(); //changed variable IN to scan
	}
		
	
	private static void output(Object object) {
		System.out.println(object);
	}

	
}




