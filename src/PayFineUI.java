/**
Author:Santosh Sapkota
Reviewer: Ashish Maharjan
Mediator: Shyam Shrestha
Facilitator: Ashmit Man Sthapit
*/






import java.util.Scanner;


public class PayFineUI {


	public static enum uiState { INITIALISED, READY, PAYING, COMPLETED, CANCELLED };

	private payFineControl control;//mistake 1 PayFineControl class name small letter
	private Scanner input;
	private uiState state;			//Changed UI_STATE to uiState

	
	public PayFineUI(PayFineControl control) {
		this.control = control;
		input = new Scanner(System.in);
		state = uiState.INITIALISED;	//Changed UI_STATE to uiState
		control.setUI(this);
	}
	
	
	public void setState(uiState state) {//Changed UI_STATE to uiState
		this.state = state;
	}


	public void run() {
		output("Pay Fine Use Case UI\n");
		
		while (true) {
			
			switch (state) {
			
			case READY:
				String memStr = input("Swipe member card (press <enter> to cancel): ");//mistake 2 memStr improper name (memberString)
				if (memStr.length() == 0) {)
					control.cancel();
					break;
				}
				try {
					int memberId = Integer.valueOf(memStr).intValue();//mistake 2 memStr improper name (memberString)
					control.cardSwiped(memberId);
				}
				catch (NumberFormatException e) {
					output("Invalid memberId");
				}
				break;
				
			case PAYING:
				double amount = 0;
				String amtStr = input("Enter amount (<Enter> cancels) : ");//mistake 3 amtStr improper name amountString
				if (amtStr.length() == 0) {
					control.cancel();
					break;
				}
				try {
					amount = Double.valueOf(amtStr).doubleValue();//mistake 3 amtStr improper name amountString
				}
				catch (NumberFormatException e) {}
				if (amount <= 0) {
					output("Amount must be positive");
					break;
				}
				control.payFine(amount);
				break;
								
			case CANCELLED:
				output("Pay Fine process cancelled");
				return;
			
			case COMPLETED:
				output("Pay Fine process complete");
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
