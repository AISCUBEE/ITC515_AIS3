/**
Author:Santosh Sapkota
Reviewer: Ashish Maharjan
Mediator: Shyam Shrestha
Facilitator: Ashmit Man Sthapit
*/




import java.util.Scanner;


public class ReturnBookUI {

	public static enum uiState { INITIALISED, READY, INSPECTING, COMPLETED };//Changed UI_STATE to uiState

	private ReturnBookControl control;
	private Scanner input;
	private uiState state;

	
	public ReturnBookUI(ReturnBookControl control) { 
		this.control = control;
		input = new Scanner(System.in);
		state = uiState.INITIALISED;										//Changed UI_STATE to uiState
		control.setUI(this);
	}


	public void run() {		
		output("Return Book Use Case UI\n");
		
		while (true) {
			
			switch (state) {
			
			case INITIALISED:
				break;
				
			case READY:
				String bookString = input("Scan Book (<enter> completes): "); //Changed variable name for bookStr to bookString((Reviewed by Ashsih)
				if (bookString.length() == 0) {//Changed variable name name for bookStr to bookString
					control.scanningComplete();
				}
				else {
					try {
						int bookId = Integer.valueOf(bookString).intValue();//Changed variable name for bookstr to bookString(Reviewed by Ashsih)
						control.bookScanned(bookId);
					}
					catch (NumberFormatException e) {
						output("Invalid bookId");
					}					
				}
				break;				
				
			case INSPECTING:
				String answer = input("Is book damaged? (Y/N): ");//Changed variable name ans with answer
				boolean isDamaged = false;
				if (answer.toUpperCase().equals("Y")) {				
					isDamaged = true;
				}
				control.dischargeLoan(isDamaged);
			
			case COMPLETED:
				output("Return processing complete");
				return;
			
			default:
				output("Unhandled state");
				throw new RuntimeException("ReturnBookUI : unhandled state :" + state);			
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
	
	public void setState(uiState state) {		//Changed UI_STATE to uiState
		this.state = state;
	}

	
}
