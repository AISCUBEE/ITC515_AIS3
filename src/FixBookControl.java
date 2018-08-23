/*Author: Ashmit Man Sthapit
  Reviewer: Shyam Shrestha
  Mediator: Santosh Sapkota
  Facilitator: Aashish Maharjan
*/
public class FixBookControl {
	
	private FixBookUI ui;
	private enum CONTROL_STATE { INITIALISED, READY, FIXING };
	private CONTROL_STATE state;
	
	private Library library;//changed class name library to Library
	private Book currentBook;// changed class name book to Book


	public FixBookControl() {
		this.library = library.INSTANCE();//
		state = CONTROL_STATE.INITIALISED;
	}
	
	
	public void setUi(FixBookUI ui) {// changed setUI to setUi
		if (!state.equals(CONTROL_STATE.INITIALISED)) {
			throw new RuntimeException("FixBookControl: cannot call setUi except in INITIALISED state");
		}	
		this.ui = ui;
		ui.setState(FixBookUI.UiState.READY);//change UI_STATE to UiState
		state = CONTROL_STATE.READY;		
	}


	public void bookScanned(int bookId) {
		if (!state.equals(CONTROL_STATE.READY)) {
			throw new RuntimeException("FixBookControl: cannot call bookScanned except in READY state");
		}	
		currentBook = library.Book(bookId);
		
		if (currentBook == null) {
			ui.display("Invalid bookId");
			return;
		}
		if (!currentBook.Damaged()) {
			ui.display("\"Book has not been damaged");
			return;
		}
		ui.display(currentBook.toString());
		ui.setState(FixBookUI.UiState.FIXING);//change UI_STATE to UiState
		state = CONTROL_STATE.FIXING;		
	}


	public void fixBook(boolean fix) {
		if (!state.equals(CONTROL_STATE.FIXING)) {
			throw new RuntimeException("FixBookControl: cannot call fixBook except in FIXING state");
		}	
		if (fix) {
			library.repairBook(currentBook);
		}
		currentBook = null;
		ui.setState(FixBookUI.UiState.READY);//change UI_STATE to UiState
		state = CONTROL_STATE.READY;		
	}

	
	public void scanningComplete() {
		if (!state.equals(CONTROL_STATE.READY)) {
			throw new RuntimeException("FixBookControl: cannot call scanningComplete except in READY state");
		}	
		ui.setState(FixBookUI.UiState.COMPLETED);	//change UI_STATE to UiState	
	}






}
