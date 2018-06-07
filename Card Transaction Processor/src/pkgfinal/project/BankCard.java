//Stephen Hanna 109097796
package pkgfinal.project;

import java.text.DecimalFormat;
import java.util.ArrayList;

public abstract class BankCard {

	private String cardholderName;
	protected long cardNumber;
	protected double currentBalance;
        protected boolean freezeAccount = false;
	protected ArrayList<Transaction> transactionList;
	DecimalFormat amount = new DecimalFormat("0.00");

	public BankCard() {
	}

	public BankCard(String cardholderName, long cardNumber) {

		this.cardholderName = cardholderName;
		this.cardNumber = cardNumber;
		this.currentBalance = 0;
		this.transactionList = new ArrayList<Transaction>();
	}

	public double balance() {
		return currentBalance;
	}

	public long number() {
		return cardNumber;
	}

	public String cardHolder() {
		return cardholderName;
	}	

	public String toString() {
		return  "Card Holder Name: "+cardholderName+"\nCard #: " + cardNumber + "\nBalance:$" + amount.format(currentBalance);
	}

	public abstract boolean addTransaction(Transaction t);

	public abstract void printStatement();
}
