package blackjack.model;

public class Card {
	protected int number;
	protected boolean view;
	
	public Card(int pNumber) throws MyException{
		if(pNumber<1 || pNumber>13) {
			throw new MyException("Número de carta incorrecto.","E001");
		}else {
			this.setNumber(pNumber);
			this.view=false;
		}
	}
	
	public void flipCard() {
		this.view=true;
	}
	
	
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	
	public boolean getView() {
		return this.view;
	}
	
	public void setView(boolean pView) {
		this.view=pView;		
	}
}
