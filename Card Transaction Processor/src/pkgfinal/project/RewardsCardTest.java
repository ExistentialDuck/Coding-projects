package pkgfinal.project;

// Basic test harness for the RewardsCard class
import java.util.*;

public class RewardsCardTest
{
	private static Random r;

	public static void main (String [] args)
	{
		r = new Random();

		// REMINDER: Add a lowercase 'l' after a number to make it a 'long' rather than an 'int'
		RewardsCard c1 = new RewardsCard("John_Smith", 8655060645070813l, 1015, 10000);
		RewardsCard c2 = new RewardsCard("Mary Doe", 8762574107106867l, 216);
		RewardsCard c3 = new RewardsCard("Bob Williams", 8752474634030982l, 315, 25000); // technically invalid # (will only be caught by a particular extra-credit option)
		
		// Add some sample transactions to each card
		for (int i = 0; i < 5; i++)
		{
			if (i == 3)
			{
				// Redeem rewards points instead of adding a debit/credit transaction
				int pts = r.nextInt(7500) + 500;
				System.out.println("c1 redeemed " + pts + " points");
				c1.redeemPoints(pts);

				pts = r.nextInt(7500) + 500;
				System.out.println("c2 redeemed " + pts + " points");
				c2.redeemPoints(pts);

				pts = r.nextInt(7500) + 500;
				System.out.println("c3 redeemed " + pts + " points");
				c3.redeemPoints(pts);
			}
			else
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