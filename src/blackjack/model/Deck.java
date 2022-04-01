package blackjack.model;

import java.util.Collections;
import java.util.Stack;

public class Deck {
	private Stack<Card> cards;
	
	public Deck() throws MyException {
		this.cards=new Stack<Card>();
		this.generateDeck();
	}
	
	private void generateDeck() throws MyException{
		for(int i=0;i<4;i++) {
			for(int j=1;j<14;j++) {
				switch(i) {
					case 0:
						this.cards.add(new Spade(j));
						break;

					case 1:
						this.cards.add(new Diamond(j));
						break;
					
					case 2:
						this.cards.add(new Clover(j));
						break;
					
					case 3:
						this.cards.add(new Heart(j));
						break;	
				}
			}
		}
		Collections.shuffle(this.cards);
	}
	public int size() {
		return this.cards.size();
	}
	
	
	public Card getCard() {
		return this.cards.pop();
	}
}
