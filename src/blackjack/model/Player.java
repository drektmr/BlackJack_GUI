package blackjack.model;

import java.util.Stack;

public class Player {
	private String name;
	private Stack<Card> cards;
	
	public Player(String pName, Deck deck) {
		this.name=pName;
		this.cards=new Stack<Card>();
		Card card1=deck.getCard();
		Card card2=deck.getCard();
		
		this.cards.push(card1);
		this.cards.push(card2);
	}

	public Stack<Card> getCards() {
		return cards;
	}

	public void addCard(Card toAdd) {
		this.cards.add(toAdd);
	}
	
	public Card getTopCard(Deck deck) {
		return deck.getCard();
	}
	
	public void flipCards() {
		for (Card item: this.cards) {
            item.flipCard();
        }
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
