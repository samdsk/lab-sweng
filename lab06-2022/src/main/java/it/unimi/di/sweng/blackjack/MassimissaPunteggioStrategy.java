package it.unimi.di.sweng.blackjack;

public class MassimissaPunteggioStrategy implements Strategia {
	final private Strategia next;
	public MassimissaPunteggioStrategy(Strategia next) {
		this.next = next;
	}

	@Override
	public boolean chiediCarta(GiocatoreBJ giocatore, GiocatoreBJ banco) {
		if(giocatore.getPunti()<17) return true;
		else return next.chiediCarta(giocatore,banco);
	}
}
