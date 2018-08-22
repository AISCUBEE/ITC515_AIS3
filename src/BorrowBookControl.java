/*
@author: Aashish Maharjan
@reviewer: Ashmit Man Sthapit
@Mediator: Shyam Kumar Shrestha
@Facilitator: Santosh Sapkota
*/

import java.util.ArrayList;
import java.util.List;

public class BorrowBookControl {
	
	private BorrowBookUI ui;
	private Library library; //updating the variable L to library 
	private Member member; //updating the variable M to member
	
	//Changing the formating for the code for better visibility of the code
	private enum ControlState { INITIALISED, READY, RESTRICTED, SCANNING, IDENTIFIED, FINALISING, COMPLETED, CANCELLED };
	//changing enum CONTROL_STATE to ControlState
	private ControlState state; //changing enum CONTROL_STATE to ControlState
	private List<book> pending; //changing PENDING TO pending
	private List<loan> completed; // changing COMPLETED to completed
	private Book book; // changing variable B to book and also book variable in Book
	
	//changing all the instances of L to library in the following code
	public BorrowBookControl() {
		this.library = library.INSTANCE(); //renamed L to library
		state = ControlState.INITIALISED; // changing CONTROL_STATE to ControlState
	}
	

	public void setUI(BorrowBookUI ui) {
		if (!state.equals(ControlState.INITIALISED)){ //changing CONTROL_STATE to ControlState and adding braces
			throw new RuntimeException("BorrowBookControl: cannot call setUI except in INITIALISED state");
		}	
		this.ui = ui;
		ui.setState(BorrowBookUI.UiState.READY); //changing UI_STATE to UiState
		state = ControlState.READY; // changing CONTROL_STATE to ControlState		
	}

		
	public void Swiped(int memberId) {
		//adding the curly braces
		if (!state.equals(ControlState.READY)){ //renaming CONTROL_STATE to ControlState 
			throw new RuntimeException("BorrowBookControl: cannot call cardSwiped except in READY state");
		}	
		member = library.getMember(memberId); //renaming variable L and M to library and member
		// adding the scape for better formating
		if (member == null) { //renaming M to member
			ui.display("Invalid memberId");
			return;
		}
		
		if (library.memberCanBorrow(M)) { //renaming variable L and M to library and member
			pending = new ArrayList<>(); //renaming PENDING to pending
			ui.setState(BorrowBookUI.UiState.SCANNING); //changing UI_STATE to UiState
			state = ControlState.SCANNING; } // changing CONTROL_STATE to ControlState
		else 
		{
			ui.display("Member cannot borrow at this time");
			ui.setState(BorrowBookUI.UiState.RESTRICTED); }} //changing UI_STATE to UiState
	
	
	public void Scanned(int bookId) {
		book = null; // renaming variable B to book
		if (!state.equals(ControlState.SCANNING)) { // changing CONTROL_STATE to ControlState
			throw new RuntimeException("BorrowBookControl: cannot call bookScanned except in SCANNING state");
		}
		
		book = library.Book(bookId); // renaming variable B to book and L to library
		if (book == null) { // renaming variable B to book
			ui.display("Invalid bookId");
			return;
		}
		
		if (!book.Available()) { // renaming variable B to book
			ui.display("Book cannot be borrowed");
			return;
		}
		//renamed all the PENDING to pending
		pending.add(book); // renaming variable B to book
		for (Book book : pending) { // renaming variable B to book and b in book to Book
			ui.display(book.toString()); // renaming variable B to book
		}
		//renaming PENDING to pending
		if (library.loansRemainingForMember(M) - pending.size() == 0) { // renaming L to library
			ui.display("Loan limit reached");
			Complete();
		}
	}
	
	
	public void Complete() {
		if (pending.size() == 0) { //renaming PENDING to pending
			cancel();
		}
		else {
			ui.display("\nFinal Borrowing List");
			for (Book book : pending) { //renaming PENDING to pending and variable B to book
				ui.display(book.toString());// renaming variable B to book
			}
			COMPLETED = new ArrayList<loan>();
			ui.setState(BorrowBookUI.UiState.FINALISING); // changing UI_STATE to UiState
			state = ControlState.FINALISING; // changing CONTROL_STATE to ControlState
		}
	}


	public void commitLoans() {
		if (!state.equals(ControlState.FINALISING)) { // changing CONTROL_STATE to ControlState
			throw new RuntimeException("BorrowBookControl: cannot call commitLoans except in FINALISING state");
		}
		//changing b in the book to Book
		for (Book book : pending) {//renaming PENDING to pending and variable B to book
			loan loan = library.issueLoan(book, member); // renaming variable B to book and M to member
			COMPLETED.add(loan);			
		}
		ui.display("Completed Loan Slip");
		for (Loan loan : COMPLETED) { // changing l to loan
			ui.display(loan.toString());
		}
		ui.setState(BorrowBookUI.UiState.COMPLETED); //changing UI_STATE to UiState
		state = ControlState.COMPLETED;  // changing CONTROL_STATE to ControlState
	}

	
	public void cancel() {
		ui.setState(BorrowBookUI.UiState.CANCELLED); //changing UI_STATE to UiState
		state = ControlState.CANCELLED;  // changing CONTROL_STATE to ControlState
	}
	
	
}
