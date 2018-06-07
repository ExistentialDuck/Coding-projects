package pkgfinal.project;

import java.util.*;
import java.io.*;

public class TransactionProcessor {

	private static String getCardType(long number) {
		// Return a String indicating whether 'number' belongs to a
		// CreditCard, RewardsCard, or a PrepaidCard (or null if it's none
		// of the three)

		String result;

		int firstTwo = Integer.parseInt(("" + number).substring(0, 2));

		switch (firstTwo) {
		case 84:
		case 85:
			result = "CreditCard";
			break;
		case 86:
		case 87:
			result = "RewardsCard";
			break;
		case 88:
		case 89:
			result = "PrepaidCard";
			break;
		default:
			result = null; // invalid card number
		}

		return result;
	}

	public static BankCard convertToCard(String data) {
		Scanner input = new Scanner(data);
		long number = input.nextLong();
		String type = getCardType(number);
		String name = input.next();
		int date;
		double balance;
                if(name.contains("_")){
                    name = name.replaceAll("_"," ");
                }
		BankCard card;
		if (type.equals("CreditCard")) {

			date = input.nextInt();
			balance = input.nextDouble();
			card = new CreditCard(name, number, date, balance);
		}

		else if (type.equals("RewardsCard")) {

			date = input.nextInt();
			balance = input.nextDouble();
			card = new RewardsCard(name, number, date, balance);
		}

		else if (type.equals("PrepaidCard")) {

			card = new PrepaidCard(name, number);
		} else
			card = null;
		input.close();
		return card;
	}

	public static CardList loadCardData(String fName) {
		int count =0;
		File file = new File(fName);
		Scanner in;
		try {
			in = new Scanner(file);
		} catch (FileNotFoundException e) {

			return null;
		}
		CardList list = new CardList();
		while (in.hasNext()) {
			String line = in.nextLine();
			BankCard card = convertToCard(line);
			list.add(card);
			count++;
		}
		System.out.println("Read data for "+count+" account(s)\n");
		in.close();
		return list;
	}

	public static void processTransactions(String filename, CardList c) {

		File file = new File(filename);
		Scanner in;
		try {
			in = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
			return;
		}
		while (in.hasNext()) {
			String line = in.nextLine();
			String[] arraySt = line.split(" ");
			long cardNumber = Long.parseLong(arraySt[0]);
			long index = c.indexOf(cardNumber);

			if (arraySt[1].equals("redeem")) {
				int points = Integer.parseInt(arraySt[2]);
				RewardsCard card = (RewardsCard) (c.get((int) (index)));
                                if (card != null) {
					card.redeemPoints(points);
				}
					
			}
			else if (arraySt[1].equals("top-up")) {
				double funds = Double.parseDouble(arraySt[2]);
				PrepaidCard card = (PrepaidCard) (c.get((int) (index)));
				if (card != null) {
					card.addFunds(funds);
				}
			}
                        else if (arraySt[1].equals("advance")) {
                            	double amount = Double.parseDouble(arraySt[2]);
                                if(arraySt[3].contains("_")){
                                    arraySt[3] = arraySt[3].replaceAll("_", " ");
                                }
				Transaction t = new Transaction(arraySt[1],arraySt[3],amount);
				BankCard card = c.get((int)index);
				if (card!=null)
				card.addTransaction(t);
                        }
                        else if (arraySt[1].equals("fee")) {
                            	double amount = Double.parseDouble(arraySt[2]);
                                if(arraySt[3].contains("_")){
                                    arraySt[3] = arraySt[3].replaceAll("_", " ");
                                }
				Transaction t = new Transaction(arraySt[1],arraySt[3],amount);
				BankCard card = c.get((int)index);
				if (card!=null)
				card.addTransaction(t);
                        }
			else {
				double amount = Double.parseDouble(arraySt[2]);
                                if(arraySt[3].contains("_")){
                                    arraySt[3] = arraySt[3].replaceAll("_", " ");
                                }
				Transaction t = new Transaction(arraySt[1],arraySt[3],amount);
				BankCard card = c.get((int)index);
				if (card!=null)
				card.addTransaction(t);
			}
			
		}
		in.close();
		System.out.println("Transaction processing results");
		System.out.println("------------------------------\r\n\n");
				

	}

	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);
		
		System.out.print("Please Enter The Card Data File: ");
		String filename =input.nextLine();
		CardList list = loadCardData(filename);
		if (list!=null) {
			System.out.print("Please Enter Transaction Data File: ");
			String transfile = input.nextLine();
			processTransactions(transfile,list);
			for (int i = 0; i < list.size(); i++) {
				System.out.println("------------------------------");
				list.get(i).printStatement();
				System.out.println("------------------------------\r\n\n");
			}
		}
		
		
		
		

	}

}
