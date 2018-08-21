//Author : Shyam
//Reviewer: Santosh
// Mediator : Aashis
//Facilitator: Ashmit

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")

public class Member implements Serializable {  // class member changed to Member

	private String lastName; // replace veritable LN to lastName
	private String fistName; //renamed variable name FN to firstName
	private String email; // EM is changed to email
	private int phoneNo; //PN is changed to phoneNo
	private int id; // ID is changed to ID
	private double fines; // FINES is changed to fines
	
	private Map<Integer, loan> loans;  //LNS is changed to loans

	
	public Member(String lastName, String firstName, String email, int phoneNo, int id) { //
		this.lastName = lastName; // LN to lastName
		this.firstName = firstName; // FN to firstName
		this.email= email;          // EM to email
		this.phoneNo = phoneNo;    // PN to phoneNo
		this.id = id;            // ID to id
		
		this.loan = new HashMap<>();  // LNS to loan
	}

	
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder(); // constructor sb is changed to StringBuilder
		StringBuilder.append("Member:  ").append(id).append("\n") // sb to StringBuilder
		  .append("  Name:  ").append(lastName).append(", ").append(firstName).append("\n")//LN to lastName and FN to firstName
		  .append("  Email: ").append(email).append("\n") // EM to email
		  .append("  Phone: ").append(phoneNo)// PN to phoneNo
		  .append("\n")
		  .append(String.format("  Fines Owed :  $%.2f", fines))  // change FINES to fines
		  .append("\n");
		
		for (loan loan : loan.values()) {  // added loan
			StringBuilder.append(loan).append("\n"); // sb to StringBuilder
		}		  
		return StringBuilder.toString(); sb to StringBuilder // sb to StringBuilder
	}

	
	public int getId() {
		return Id; // ID to id
	}

	
	public List<loan> getLoans() {
		return new ArrayList<loan>(loans.values()); // change to loan for LNS
	}

	
	public int getNumberOfCurrentLoans() {
		return loans.size();  // LNS to loans
	}

	
	public double getFinesOwed() {
		return fines;  // FINES to fines
	}

	
	public void takeOutLoan(loan loan) {
		if (!loans.containsKey(loan.getId())) { 
			loans.put(loan.getId(), loan); // LNS to loan
		}
		else {
			throw new RuntimeException("Duplicate loan added to member");
		}		
	}

	
	public String getLastName() {
		return loans; // LNS to loans
	}

	
	public String getFirstName() {
		return firstName; // FN to functioName
	}


	public void addFine(double fine) {
		fines += fine;// change FINES to fines
	}
	
	public double payFine(double amount) {
		if (amount < 0) {
			throw new RuntimeException("Member.payFine: amount must be positive");
		}
		double change = 0;
		if (amount > fines) {  // FINES to fines
			change = amount - fines;// FINES to fines
			fines = 0;
		}
		else {
			fines -= amount;
		}
		return change;
	}


	public void dischargeLoan(loan loan) {
		if (loans.containsKey(loan.getId())) { // LNS to loans
			loans.remove(loan.getId());
		}
		else {
			throw new RuntimeException("No such loan held by member");
		}		
	}

}
