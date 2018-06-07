//Stephen Hanna 109097796
package pkgfinal.project;

import java.util.ArrayList;

public class CardList {

	private ArrayList<BankCard> ListOfCards;

	public CardList() {

		this.ListOfCards = new ArrayList<BankCard>();
	}

	public void add(BankCard b) {

		ListOfCards.add(b);
	}

	public void add(int index, BankCard b) {
		if (index >= 0 && index <= ListOfCards.size()) {

			ListOfCards.add(index, b);
		}

		else
			ListOfCards.add(b);

	}
	public int size() {return ListOfCards.size();}
	
	public BankCard get(int index) {
		
		if (index >= 0 && index <= ListOfCards.size()) {
			
			return ListOfCards.get(index);
		}
		else return null;
		
	}
	
	public int indexOf(long cardNumber) {
	
		for (int i =0; i<ListOfCards.size(); i++) {
			
			if (cardNumber== ListOfCards.get(i).cardNumber) return i;
		}
		return -1;
	}
	
}
