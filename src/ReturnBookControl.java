public class ReturnBookControl {

	private ReturnBookUI ui;
	private enum ControlState { INITIALISED, READY, INSPECTING }; //Changed CONTROL_STATE to ControlState
	private ControlState state;//Changed CONTROL_STATE to ControlState
	
	private library library;
	private loan currentLoan;
	

	public ReturnBookControl() {
		this.library = library.INSTANCE();
		state = ControlState.INITIALISED;//Changed CONTROL_STATE to ControlState
	}
	
	
	public void setUI(ReturnBookUI ui) {
		if (!state.equals(ControlState.INITIALISED)) {//Changed CONTROL_STATE to ControlState
			throw new RuntimeException("ReturnBookControl: cannot call setUI except in INITIALISED state");
		}	
		this.ui = ui;
		ui.setState(ReturnBookUI.uiState.READY);//Changed UI_STATE to uiState
		state = ControlState.READY;		//Changed CONTROL_STATE to ControlState
	}


	public void bookScanned(int bookId) {
		if (!state.equals(ControlState.READY)) {//Changed CONTROL_STATE to ControlState
			throw new RuntimeException("ReturnBookControl: cannot call bookScanned except in READY state");
		}	
		book currentBook = library.Book(bookId);
		
		if (currentBook == null) {
			ui.display("Invalid Book Id");
			return;
		}
		if (!currentBook.On_loan()) {
			ui.display("Book has not been borrowed");
			return;
		}		
		currentLoan = library.getLoanByBookId(bookId);	
		double overDueFine = 0.0;
		if (currentLoan.isOverDue()) {
			overDueFine = library.calculateOverDueFine(currentLoan);
		}
		ui.display("Inspecting");
		ui.display(currentBook.toString());
		ui.display(currentLoan.toString());
		
		if (currentLoan.isOverDue()) {
			ui.display(String.format("\nOverdue fine : $%.2f", overDueFine));
		}
		ui.setState(ReturnBookUI.uiState.INSPECTING);//Changed UI_STATE to uiState
		state = ControlState.INSPECTING;		//Changed CONTROL_STATE to ControlState
	}


	public void scanningComplete() {
		if (!state.equals(ControlState.READY)) {//Changed CONTROL_STATE to ControlState
			throw new RuntimeException("ReturnBookControl: cannot call scanningComplete except in READY state");
		}	
		ui.setState(ReturnBookUI.uiState.COMPLETED);	//Changed UI_STATE to uiState	
	}


	public void dischargeLoan(boolean isDamaged) {
		if (!state.equals(ControlState.INSPECTING)) {//Changed CONTROL_STATE to ControlState
			throw new RuntimeException("ReturnBookControl: cannot call dischargeLoan except in INSPECTING state");
		}	
		library.dischargeLoan(currentLoan, isDamaged);
		currentLoan = null;
		ui.setState(ReturnBookUI.uiState.READY);//Changed UI_STATE to uiState
		state = ControlState.READY;			//Changed CONTROL_STATE to ControlState	
	}


}
