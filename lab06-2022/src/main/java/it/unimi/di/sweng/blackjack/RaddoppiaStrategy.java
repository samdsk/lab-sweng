package it.unimi.di.sweng.blackjack;

import ca.mcgill.cs.stg.solitaire.cards.Card;
import ca.mcgill.cs.stg.solitaire.cards.Rank;

import java.util.Iterator;

public class RaddoppiaStrategy implements Strategia{
	private final Strategia next;

	public RaddoppiaStrategy(Strategia next) {
		this.next = next;
	}

	// chiede di fare il raddoppio
	// se il banco ha la prima carta diverso da ACE,
	// il valore della carta è minore di 8,
	// il punteggio complessivo del giocatore è minore di 11

	@Override
	public boolean chiediCarta(GiocatoreBJ giocatore, GiocatoreBJ banco) {
		Iterator<Card> it = banco.getCards();
		assert it.hasNext() : "Banco non ha carte";

		Card bancoPrimaCarta = it.next();

		if(bancoPrimaCarta.getRank() != Rank.ACE
				&& BlackJack.cardValue(bancoPrimaCarta) < 8
				&& giocatore.getPunti() < 11) return true;

		else return next.chiediCarta(giocatore,banco);

	}
}
