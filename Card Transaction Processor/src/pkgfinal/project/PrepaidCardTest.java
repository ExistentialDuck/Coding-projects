package pkgfinal.project;

// Basic test harness for the PrepaidCard class
import java.util.*;

public class PrepaidCardTest
{
	private static Random r;

	public static void main (String [] args)
	{
		r = new Random();

		// REMINDER: Add a lowercase 'l' after a number to make it a 'long' rather than an 'int'
		PrepaidCard c1 = new PrepaidCard("John Smith", 8844408184802583l, 1000);
		PrepaidCard c2 = new PrepaidCard("Mary Doe", 8955060645070813l);
		PrepaidCard c3 = new PrepaidCard("Bob Williams", 8948293193669982l, 2500); // technically invalid # (will only be caught by one of the extra credit options)

		c2.addFunds(1200.38);

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