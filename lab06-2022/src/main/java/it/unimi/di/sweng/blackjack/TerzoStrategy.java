package it.unimi.di.sweng.blackjack;

import ca.mcgill.cs.stg.solitaire.cards.Card;
import ca.mcgill.cs.stg.solitaire.cards.Rank;

import java.util.Iterator;

public class TerzoStrategy implements Strategia{
	Strategia next;

	public TerzoStrategy(Strategia next) {
		this.next = next;
	}

	// il giocatore chiede carta indipendentemente dal banco
	// finche non supera 9 di punteggio
	@Override
	public boolean chiediCarta(GiocatoreBJ giocatore, GiocatoreBJ banco) {
		if(giocatore.getPunti() < 9) return true;
		else return next.chiediCarta(giocatore,banco);
	}
}
