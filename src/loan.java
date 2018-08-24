//author: Shyam
// Reviewer : Santosh
//Mediater : Aashish
//Facilitator : Ashmit


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
//editing in loan section now

@SuppressWarnings("serial")

public class Loan implements Serializable {  // Public class loan is changed to Loan, class name should start with capital letter

	public static enum loanState { CURRENT, OVER_DUE, DISCHARGED }; // changed the name LOAN_STATE to loanState
	private int id;         // ID is changed to id
	private Book book;	    // //changed type book to Book and and attribute name B to book 
	private Member member; //changing variable m to member
	private Date date;     //change attribute name D to date
	private loanState state;// changed LOAN_STATE to loanState


	public Loan(int loanId, Book book, Member member, Date dueDate) {  // constructor loan is changed to Loan and argument type book and member us are changed to Book and Member
		this.id = loanId;  // ID is changed to id
		this.book = book; //this.B changed to this.book
		this.member = member;//this.M to this member
		this.date = dueDate;  // this.D to this.date
		this.state = losnState.CURRENT; // LOAN_STATE.CURRENT changed to loanState.CURRENT
		
	public void checkOverDue() {
		if (state == loanState.CURRENT &&
			Calendar.getInstance().Date().after(date)) {		// D is changed  to date
			this.state = loanState.OVER_DUE;					//change LOAN_STATE to loanState		
		}
	}

	
	public boolean isOverDue() {
		return state == loanState.OVER_DUE;				//LOAN_STATE is to loanState
	}

	
	public int getId() {					//change return type Integer to int
		return id;							//ID is changed to id
	}


	public Date getDueDate() {
		return date;						//change D to date
	}
	
	
	public String toString() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");		//variable name sdf is changed to simpleDateFormat

		StringBuilder stringBuilder = new StringBuilder();				//change sb to stringBuilder
		stringBuilder.append("Loan:  ").append(id).append("\n")			//sb is changed to stringBuilder and ID is to id
		  .append("  Borrower ").append(member.getId()).append(" : ")	//change M to member
		  .append(member.getLastName()).append(", ").append(member.getFirstName()).append("\n")
		  .append("  Book ").append(book.ID()).append(" : " )			//change B to book
		  .append(book.Title()).append("\n")
		  .append("  DueDate: ").append(simpleDateFormat.format(date)).append("\n")		//sdf is changed to simpleDateFormat and D to date
		  .append("  State: ").append(state);		
		return stringBuilder.toString();
	}


	public Member member() {	// return type member is changed to Member and method name Member is changed to member
		return member;			//change M to member
	}


	public Book book() {			//change return type book to Book and method name Book to book
		return book;				// B to book
	}


	public void loan() { 						//method name Loan  is changed to loan
		state = loanState.DISCHARGED;			//LOAN_STATE is changed to loanState
	}

}

