//Author: Ashmit Man Sthapit
//Reviewer: Shyam Shrestha
//Mediator: Santosh Sapkota
//Facilitator: Aashish Maharjan
import java.util.Scanner;


public class FixBookUI {

	public static enum UiState { INITIALISED, READY, FIXING, COMPLETED };//change UI_STATE to UiState

	private FixBookControl control;
	private Scanner input;
	private UiState state;//change UI_STATE to UiState

	
	public FixBookUI(FixBookControl control) {
		this.control = control;
		input = new Scanner(System.in);
		state = UiState.INITIALISED;//change UI_STATE to UiState
		control.setUI(this);
	}


	public void setState(UiState state) {//change UI_STATE to UiState
		this.state = state;
	}

	
	public void run() {
		output("Fix Book Use Case UI\n");
		
		while (true) {
			
			switch (state) {
			
			case READY:
				String bookString = input("Scan Book (<enter> completes): ");// bookStr changed to bookString
				if (bookString.length() == 0) {
					control.scanningComplete();
				}
				else {
					try {
						int bookId = Integer.valueOf(bookString).intValue();// bookStr changed to bookString
						control.bookScanned(bookId);
					}
					catch (NumberFormatException e) {
						output("Invalid bookId");
					}
				}
				break;	
				
			case FIXING:
				String option = input("Fix Book? (Y/N) : ");//change ans to option
				boolean fix = false;
				if (option.toUpperCase().equals("Y")) {// changed ans to option
					fix = true;
				}
				control.fixBook(fix);
				break;
								
			case COMPLETED:
				output("Fixing process complete");
				return;
			
			default:
				output("Unhandled state");
				throw new RuntimeException("FixBookUI : unhandled state :" + state);			
			
			}		
		}
		
	}

	
	private String input(String prompt) {
		System.out.print(prompt);
		return input.nextLine();
	}	
		
		
	private void output(Object object) {
		System.out.println(object);
	}
	

	public void display(Object object) {
		output(object);
	}
	
	
}
