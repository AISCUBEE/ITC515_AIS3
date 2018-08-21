//Author: Ashmit Sthapit
//Reviewer: Shyam Shrestha
//Mediator: Santosh Sapkota
//Facilitator: Aashish Maharjan

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class Library implements Serializable {// class name was started with small letter, library changed into Library
	
	private static final String LIBRARY_FILE = "library.obj";
	private static final int LOAN_LIMIT = 2;
	private static final int LOAN_PERIOD = 2;
	private static final double FINE_PER_DAY = 1.0;
	private static final double MAX_FINES_OWED = 5.0;
	private static final double DAMAGE_FEE = 2.0;
	
	private static Library self;// class name should start from capital letter
	private int bookId;//changed the variable into meaning full word, BID to bookId
	private int memberId;//changed the variable into meaning full word, MID to memberId
	private int loanId;//changed the variable into meaning full word, LID to loanId
	private Date loadDate;
	
	private Map<Integer, Book> catalog;
	private Map<Integer, Member> members;
	private Map<Integer, Loan> loans;
	private Map<Integer, Loan> currentLoans;
	private Map<Integer, Book> damagedBooks;
	

	private Library() {// constructor name should be same as class name
		catalog = new HashMap<>();
		members = new HashMap<>();
		loans = new HashMap<>();
		currentLoans = new HashMap<>();
		damagedBooks = new HashMap<>();
		bookId = 1;//changed the variable into meaning full word
		MemberId = 1;//changed the variable into meaning full word		
		loanId = 1;	//changed the variable into meaning full word	
	}

	
	public static synchronized Library INSTANCE() {	//	
		if (self == null) {
			Path path = Paths.get(LIBRARY_FILE);			
			if (Files.exists(path)) {	
				try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(LIBRARY_FILE));) {// changing object of ObjectInputStream to meaning full name
			    
					self = (library) inputStream.readObject();// changed lof into inputStream
					Calendar.getInstance().setDate(self.loadDate);
					inputStream.close();//changed lof into inputStream
				}
				catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
			else self = new library();
		}
		return self;
	}

	
	public static synchronized void SAVE() {
		if (self != null) {
			self.loadDate = Calendar.getInstance().Date();
			try (ObjectOutputStream outputstream = new ObjectOutputStream(new FileOutputStream(LIBRARY_FILE));) { //changed outputstream into outputstream
				outputstream.writeObject(self);
				outputstream.flush();
				outputstream.close();	
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	
	public int bookId() {//changed BookID to bookId
		return bookId;//change BID to bookId
	}
	
	
	public int memberId() {//changed MemberId to memberId
		return memberId;// changed MID to memberId
	}
	
	
	private int nextBookId() {//changed nextBID to nextBookId
		return BID++;
	}

	
	private int nextMemberId() {
		return MID++;
	}

	
	private int nextLoanId() {//changed nextLTD to nextLoanId
		return loanId++;//changed LTD to loanId
	}

	
	public List<member> Members() {		
		return new ArrayList<member>(members.values()); 
	}


	public List<book> Books() {		
		return new ArrayList<book>(catalog.values()); 
	}


	public List<loan> CurrentLoans() {
		return new ArrayList<loan>(currentLoans.values());
	}


	public member Add_mem(String lastName, String firstName, String email, int phoneNo) {		
		member member = new member(lastName, firstName, email, phoneNo, nextMemberId());
		members.put(member.getId(), member);		
		return member;
	}

	
	public book Add_book(String a, String t, String c) {//		
		book b = new book(a, t, c, nextBookId());//changed nextBID to nextBookId
		catalog.put(b.ID(), b);		
		return b;
	}

	
	public member getMember(int memberId) {
		if (members.containsKey(memberId)) 
			return members.get(memberId);
		return null;
	}

	
	public book Book(int bookId) {
		if (catalog.containsKey(bookId)) 
			return catalog.get(bookId);		
		return null;
	}

	
	public int loanLimit() {
		return LOAN_LIMIT;
	}

	
	public boolean memberCanBorrow(member member) {		
		if (member.getNumberOfCurrentLoans() == LOAN_LIMIT ) 
			return false;
				
		if (member.getFinesOwed() >= MAX_FINES_OWED) 
			return false;
				
		for (loan loan : member.getLoans()) 
			if (loan.isOverDue()) 
				return false;
			
		return true;
	}

	
	public int loansRemainingForMember(member member) {		
		return LOAN_LIMIT - member.getNumberOfCurrentLoans();
	}

	
	public loan issueLoan(book book, member member) {
		Date dueDate = Calendar.getInstance().getDueDate(LOAN_PERIOD);
		loan loan = new loan(nextLoanId(), book, member, dueDate);
		member.takeOutLoan(loan);
		book.Borrow();
		loans.put(loan.getId(), loan);
		currentLoans.put(book.ID(), loan);
		return loan;
	}
	
	
	public loan getLoanByBookId(int bookId) {
		if (currentLoans.containsKey(bookId)) {
			return currentLoans.get(bookId);
		}
		return null;
	}

	
	public double calculateOverDueFine(loan loan) {
		if (loan.isOverDue()) {
			long daysOverDue = Calendar.getInstance().getDaysDifference(loan.getDueDate());
			double fine = daysOverDue * FINE_PER_DAY;
			return fine;
		}
		return 0.0;		
	}


	public void dischargeLoan(loan currentLoan, boolean isDamaged) {
		member member = currentLoan.Member();
		book book  = currentLoan.Book();
		
		double overDueFine = calculateOverDueFine(currentLoan);
		member.addFine(overDueFine);	
		
		member.dischargeLoan(currentLoan);
		book.Return(isDamaged);
		if (isDamaged) {
			member.addFine(DAMAGE_FEE);
			damagedBooks.put(book.ID(), book);
		}
		currentLoan.Loan();
		currentLoans.remove(book.ID());
	}


	public void checkCurrentLoans() {
		for (loan loan : currentLoans.values()) {
			loan.checkOverDue();
		}		
	}


	public void repairBook(book currentBook) {
		if (damagedBooks.containsKey(currentBook.ID())) {
			currentBook.Repair();
			damagedBooks.remove(currentBook.ID());
		}
		else {
			throw new RuntimeException("Library: repairBook: book is not damaged");
		}
		
	}
	
	
}
