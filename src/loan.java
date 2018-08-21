//author: Shyam
// Reviewer : Santosh
//Mediater : Aashish
//Facilitator : Ashmit

/*************************************************************
Create a class named Loan, defining parameters and methods 
that describe a loan application for borrowing a book from 
a library in real life.

*************************************************************/

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
//editing in loan section now

@SuppressWarnings("serial")

public class Loan implements Serializable {  // Public class loan is changed to Loan, class name should start with capital letter

	public static enum loanState { CURRENT, OVER_DUE, DISCHARGED }; // changed the name LOAN_STATE to loanState
	private int id;         // ID is changed to id
	private Book book;	    // book t
	private Member member; //changing variable m to member
	private Date date; 
	private loanState state;


	public loan(int loanId, book book, member member, Date dueDate) {
		this.ID = loanId;
		this.B = book;
		this.M = member;
		this.D = dueDate;
		this.state = LOAN_STATE.CURRENT;
	}


	public void checkOverDue() {
		if (state == LOAN_STATE.CURRENT &&
			Calendar.getInstance().Date().after(D)) {
			this.state = LOAN_STATE.OVER_DUE;
		}
	}


	public boolean isOverDue() {
		return state == LOAN_STATE.OVER_DUE;
	}//testing


	public Integer getId() {
		return ID;
	}


	public Date getDueDate() {
		return D;
	}


	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		StringBuilder sb = new StringBuilder();
		sb.append("Loan:  ").append(ID).append("\n")
		  .append("  Borrower ").append(M.getId()).append(" : ")
		  .append(M.getLastName()).append(", ").append(M.getFirstName()).append("\n")
		  .append("  Book ").append(B.ID()).append(" : " )
		  .append(B.Title()).append("\n")
		  .append("  DueDate: ").append(sdf.format(D)).append("\n")
		  .append("  State: ").append(state);
		return sb.toString();
	}


	public member Member() {
		return M;
	}


	public book Book() {
		return B;
	}


	public void Loan() {
		state = LOAN_STATE.DISCHARGED;
	}

}
