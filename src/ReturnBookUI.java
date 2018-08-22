import java.util.Scanner;


public class ReturnBookUI {

	public static enum uiState { INITIALISED, READY, INSPECTING, COMPLETED };//Changed UI_STATE to uiState

	private ReturnBookControl control;
	private Scanner input;
	private uiState state;

	
	public returnBookUI(ReturnBookControl control) {
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
				String bookString = input("Scan Book (<enter> completes): "); //Proper variable name for bookString to bookString
				if (bookString.length() == 0) {//Proper variable name name for bookString to bookString
					control.scanningComplete();
				}
				else {
					try {
						int bookId = Integer.valueOf(bookString).intValue();//Proper variable name for bookString to bookString
						control.bookScanned(bookId);
					}
					catch (NumberFormatException e) {
						output("Invalid bookId");
					}					
				}
				break;				
				
			case INSPECTING:
				String ans = input("Is book damaged? (Y/N): ");
				boolean isDamaged = false;
				if (ans.toUpperCase().equals("Y")) {				
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
