//Stephen Hanna 109097796
package pkgfinal.project;

// Basic test harness for the CreditCard class
import java.util.*;

public class CreditCardTest
{
	private static Random r;

	public static void main (String [] args)
	{
		r = new Random();

		// REMINDER: Add a lowercase 'l' after a number to make it a 'long' rather than an 'int'
		CreditCard c1 = new CreditCard("John_Smith", 8525028613937723l, 1015, 10000);
		CreditCard c2 = new CreditCard("Mary Doe", 8488968564565858l, 216);
		CreditCard c3 = new CreditCard("Bob Williams", 8444730419040650l, 315, 25000); // technically invalid # (will only be caught by one of the extra-credit options)
		
		// Add some sample transactions to each card
		for (int i = 0; i < 5; i++)
		{
			Transaction t = createTransaction();
			System.out.println("Added to c1: " + t);
			c1.addTransaction(t);

			t = createTransaction();
			System.out.println("Added to c2: " + t);
			c2.addTransaction(t);

			t = createTransaction();
			System.out.println("Added to c3: " + t);
			c3.addTransaction(t);
		}

		// Check the results
		c1.printStatement();
		System.out.println("\n\n\n");

		c2.printStatement();
		System.out.println("\n\n\n");

		c3.printStatement();
	}

	private static Transaction createTransaction()
	{
		// Create and return a randomly-generated transaction
		String [] merchants = {"Amazon", "NewEgg", "Office Supplies Plus", "Pet Depot",
		                       "Cherry Computer", "Groceries R Us", "Gas N Go"};

		String from = merchants[r.nextInt(merchants.length)];

		int amountTemp = r.nextInt(50000) - 10000;
		double amount = amountTemp / 100.00;

		String type = "";

		if (amount < 0)
		{
			type = "credit";
		}
		else
		{
			type = "debit";
		}

		return new Transaction(type, from, amount);
	}

}