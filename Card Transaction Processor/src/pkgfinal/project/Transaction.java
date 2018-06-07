package pkgfinal.project;


public class Transaction
{
	private String type;
	private double amount;
	private String merchant;
	private String notes;
	
	public Transaction(String type, String from, double amt)
	{
		this.type = type.toLowerCase(); // accept "Debit" as well as "debit", for example
		
		// Note: Since the majority of BankCard subtypes accumulate debit
		// amounts each billing cycle, debits are always positive values,
		// while credits are listed as negative values. We enforce that here.
		amount = roundToTwoPlaces(amt);
		
		if (type.equals("debit") && amount < 0)
		{
			amount = -1.0 * amount; // make the debit a positive value
		}
		
		if (type.equals("credit") && amount > 0)
		{
			amount = -1.0 * amount; // make the credit a negative value
		}
		
		if (type.equals("redemption") && amount > 0)
		{
			amount = -1.0 * amount; // redemptions are credits, and therefore negative
		}
		
		merchant = from;
		notes = "";
	}
	
	public String type() { return type; }
	
	public double amount() { return amount; }
	
	public String merchant() { return merchant; }
	
	public String notes() { return notes; }
	
	// Append date to the existing notes field
	public void addNotes(String newData)
	{
		if (notes.length() > 0)
		{
			notes += "\n"; // start a new line for the new information
		}
		
		notes += newData;
	}
	
	// Replace the existing notes for this Transaction
	public void setNotes (String newData)
	{
		notes = newData;
	}
	
	public String toString()
	{
		String result = type + "\t" + merchant + "\t";
		
		String transAmount = "" + amount;
		
		// If the transaction amount ends with a single digit after the
		// decimal point, add a trailing 0 to force it to two decimal places
		if (transAmount.charAt(transAmount.length()-2) == '.')
		{
			// Add on a second trailing 0
			transAmount += "0";
		}
		
		result = result + transAmount;
		 
		if (notes.length() > 0) // add notes on a new line, if present
		{
			result = result + "\n\t" + notes;
		}
		
		return result;
	}
	
	// Make sure that the Transaction's value has exactly two decimal places
	private double roundToTwoPlaces (double value)
	{
		value = value * 100;
		
		double truncatedValue = Math.floor(value);
		
		return truncatedValue / 100.0;
	}
}