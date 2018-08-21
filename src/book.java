/*
@author : Aashish Maharjan
*/
import java.io.Serializable;


@SuppressWarnings("serial")
public class Book implements Serializable { //Class name initial is always in capitals changing book to Book
	
	private String title; //updating variable T to title
	private String author; //updating variable A to author
	private String callNo; //updating variable C to callNo
	private int id; //updating variable ID to id
	
	private enum state { AVAILABLE, ON_LOAN, DAMAGED, RESERVED }; //changing STATE to state
	private State state; //changing type STATE to State
	
	
	public book(String author, String title, String callNo, int id) {
		this.author = author; //updating variable this.T to this.title
		this.title = title; //updating variable this.A to this.author
		this.callNo = callNo; //updating variable this.C to this.callNo
		this.id = id; //updating variable this.ID to this.id
		this.state = State.AVAILABLE; //updating STATE to State
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Book: ").append(ID).append("\n")
		  .append("  Title:  ").append(T).append("\n")
		  .append("  Author: ").append(A).append("\n")
		  .append("  CallNo: ").append(C).append("\n")
		  .append("  State:  ").append(state);
		
		return sb.toString();
	}

	public Integer ID() {
		return ID;
	}

	public String title() { //updating Title to title
		return title; //changing return value T to title 
	}


	
	public boolean available() { //updating function Available to available
		return state == State.AVAILABLE; // enum STATE to State
	}

	
	public boolean onLoan() { //remaining On_loan to onLoan
		return state == State.ON_LOAN; //changes in the variable STATE to state
	}

	
	public boolean Damaged() { 
		return state == STATE.DAMAGED;
	}

	
	public void Borrow() {
		if (state.equals(STATE.AVAILABLE)) {
			state = STATE.ON_LOAN;
		}
		else {
			throw new RuntimeException(String.format("Book: cannot borrow while book is in state: %s", state));
		}
		
	}


	public void Return(boolean DAMAGED) {
		if (state.equals(STATE.ON_LOAN)) {
			if (DAMAGED) {
				state = STATE.DAMAGED;
			}
			else {
				state = STATE.AVAILABLE;
			}
		}
		else {
			throw new RuntimeException(String.format("Book: cannot Return while book is in state: %s", state));
		}		
	}

	
	public void Repair() {
		if (state.equals(STATE.DAMAGED)) {
			state = STATE.AVAILABLE;
		}
		else {
			throw new RuntimeException(String.format("Book: cannot repair while book is in state: %s", state));
		}
	}


}
