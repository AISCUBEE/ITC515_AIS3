/**
Author:Santosh Sapkota
Reviewer: Ashish Maharjan
Mediator: Shyam Shrestha
Facilitator: Ashmit Man Sthapit
*/




public class PayFineControl {
	
	private PayFineUI ui;
	private enum controlState { INITIALISED, READY, PAYING, COMPLETED, CANCELLED }; //changed CONTROL_STATE to controlState
	private controlState state;
	
	private library library; ?// mistake 1 attribute library to Library
	private member member;;// mistake 2 attribute member to Member


	public PayFineControl() {
		this.library = library.instance();//Changed INSTANCE to instance
		state = controlState.INITIALISED; //Changed CONTROL_STATE to controlState
	}
	
	
	public void setUI(PayFineUI ui) {
		if (!state.equals(controlState.INITIALISED)) { //Changed CONTROL_STATE to controlState
			throw new RuntimeException("PayFineControl: cannot call setUI except in INITIALISED state");
		}	
		this.ui = ui;
		ui.setState(PayFineUI.uiState.READY);//Changed UI_STATE to uiState
		state = controlState.READY;		
	}


	public void cardSwiped(int memberId) {
		if (!state.equals(controlState.READY)) { //Changed CONTROL_STATE to controlState
			throw new RuntimeException("PayFineControl: cannot call cardSwiped except in READY state");
		}	
		member = library.getMember(memberId);
		
		if (member == null) {
			ui.display("Invalid Member Id");
			return;
		}
		ui.display(member.toString());
		ui.setState(PayFineUI.uiState.PAYING);//Changed UI_STATE to uiState
		state = controlState.PAYING;
	}
	
	
	public void cancel() {
		ui.setState(PayFineUI.uiState.CANCELLED);//Changed UI_STATE to uiState
		state = controlState.CANCELLED;
	}


	public double payFine(double amount) {
		if (!state.equals(controlState.PAYING)) {//Changed CONTROL_STATE to controlState
			throw new RuntimeException("PayFineControl: cannot call payFine except in PAYING state");
		}	
		double change = member.payFine(amount);
		if (change > 0) {
			ui.display(String.format("Change: $%.2f", change));
		}
		ui.display(member.toString());
		ui.setState(PayFineUI.uiState.COMPLETED);//Changed UI_STATE to uiState
		state = controlState.COMPLETED;//Changed CONTROL_STATE to controlState
		return change;
	}
	


}
