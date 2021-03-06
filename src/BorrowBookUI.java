/*
@author: Aashish Maharjan
@reviewer: Ashmit Man Sthapit
@Mediator: Shyam Kumar Shrestha
@Facilitator: Santosh Sapkota
*/

import java.util.Scanner;

public class BorrowBookUI {
	
	public static enum UiState { INITIALISED, READY, RESTRICTED, SCANNING, IDENTIFIED, FINALISING, COMPLETED, CANCELLED }; //Changing enum variable from UI_STATE to UiState with camelback protocol

	private BorrowBookControl control;
	private Scanner input;
	private UiState state; //changing UI_STATE to UiState

	
	public BorrowBookUI(BorrowBookControl control) {
		this.control = control;
		input = new Scanner(System.in);
		state = UiState.INITIALISED; //changing variable name from UI_STATE to UiState
		control.setUI(this);
	}

	
	private String input(String prompt) {
		System.out.print(prompt);
		return input.nextLine();
	}	
		
		
	private void output(Object object) {
		System.out.println(object);
	}
	
			
	public void setState(UiState state) { //updating UI_STATE to UiState
		this.state = state;
	}

	
	public void run() {
		output("Borrow Book Use Case UI\n");
		
		while (true) {
			
			switch (state) {			
			//Reviewers Note to change the line spacing to match the formating protocol
				case CANCELLED:
					output("Borrowing Cancelled");
					return;

				// renaming all the memberStr to memberString	
				case READY:
					String memberString = input("Swipe member card (press <enter> to cancel): ");
					if (memberString.length() == 0) {
						control.cancel();
						break;
					}
					try {
						int memberId = Integer.valueOf(memberStr).intValue();
						control.Swiped(memberId);
					}
					catch (NumberFormatException e) {
						output("Invalid Member Id");
					}
					break;

					
				case RESTRICTED:
					input("Press <any key> to cancel");
					control.cancel();
					break;
				
				//updating all bookStr to bookString	
				case SCANNING:
					String bookString = input("Scan Book (<enter> completes): ");
					if (bookString.length() == 0) {
						control.Complete();
						break;
					}
					try {
						int bookId = Integer.valueOf(bookString).intValue();
						control.Scanned(bookId);
						
					} catch (NumberFormatException e) {
						output("Invalid Book Id");
					} 
					break;
						
				//updating the ans to answer to make it more meaningful	
				case FINALISING:
					String answer = input("Commit loans? (Y/N): ");
					if (answer.toUpperCase().equals("N")) {
						control.cancel();
						
					} else {
						control.commitLoans();
						input("Press <any key> to complete ");
					}
					break;
					
					
				case COMPLETED:
					output("Borrowing Completed");
					return;
		
					
				default:
					output("Unhandled state");
					throw new RuntimeException("BorrowBookUI : unhandled state :" + state);			
			}
		}		
	}


	public void display(Object object) {
		output(object);		
	}


}
