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
		if (!state.equals(ControlState.READY)) //renaming CONTROL_STATE to ControlState 
			throw new RuntimeException("BorrowBookControl: cannot call cardSwiped except in READY state");
			
		member = library.getMember(memberId); //renaming variable L and M to library and member
		if (member == null) { //renaming M to member
			ui.display("Invalid memberId");
			return;
		}
		
		if (L.memberCanBorrow(M)) { //renaming variable L and M to library and member
			PENDING = new ArrayList<>(); //renaming PENDING to pending
			ui.setState(BorrowBookUI.UiState.SCANNING); //changing UI_STATE to UiState
			state = ControlState.SCANNING; } // changing CONTROL_STATE to ControlState
		else 
		{
			ui.display("Member cannot borrow at this time");
			ui.setState(BorrowBookUI.UiState.RESTRICTED); }} //changing UI_STATE to UiState
	
	
	public void Scanned(int bookId) {
		B = null;
		if (!state.equals(ControlState.SCANNING)) { // changing CONTROL_STATE to ControlState
			throw new RuntimeException("BorrowBookControl: cannot call bookScanned except in SCANNING state");
		}	
		B = L.Book(bookId);
		if (B == null) {
			ui.display("Invalid bookId");
			return;
		}
		if (!B.Available()) {
			ui.display("Book cannot be borrowed");
			return;
		}
		PENDING.add(B);
		for (book B : PENDING) {
			ui.display(B.toString());
		}
		if (L.loansRemainingForMember(M) - PENDING.size() == 0) {
			ui.display("Loan limit reached");
			Complete();
		}
	}
	
	
	public void Complete() {
		if (PENDING.size() == 0) {
			cancel();
		}
		else {
			ui.display("\nFinal Borrowing List");
			for (book b : PENDING) {
				ui.display(b.toString());
			}
			COMPLETED = new ArrayList<loan>();
			ui.setState(BorrowBookUI.UI_STATE.FINALISING);
			state = ControlState.FINALISING; // changing CONTROL_STATE to ControlState
		}
	}


	public void commitLoans() {
		if (!state.equals(ControlState.FINALISING)) { // changing CONTROL_STATE to ControlState
			throw new RuntimeException("BorrowBookControl: cannot call commitLoans except in FINALISING state");
		}	
		for (book b : PENDING) {
			loan loan = L.issueLoan(b, M);
			COMPLETED.add(loan);			
		}
		ui.display("Completed Loan Slip");
		for (loan loan : COMPLETED) {
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
