package it.unimi.di.sweng.blackjack;

import ca.mcgill.cs.stg.solitaire.cards.Card;
import ca.mcgill.cs.stg.solitaire.cards.Rank;
import ca.mcgill.cs.stg.solitaire.cards.Suit;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MazziereTest {
	@Test
	void testMazziere() {
		MultiMazzo multiMazzo = new MultiMazzo(6);
		Mazziere mazziere = new Mazziere(multiMazzo);

		assertThat(mazziere.getName()).isEqualTo("Mazziere");
	}

	@Test
	void testMazziereGetCards() {
		List<Card> cards = List.of(
				Card.get(Rank.ACE, Suit.HEARTS),
				Card.get(Rank.ACE, Suit.DIAMONDS),
				Card.get(Rank.ACE, Suit.CLUBS)
		);

		Mazziere mazziere = SfidanteTest.createBancoFromListOfCards(cards);

		mazziere.carteIniziali();

		Iterator<Card> it = mazziere.getCards();

		assertThat(it.next()).isEqualTo(Card.get(Rank.ACE, Suit.CLUBS));
		assertThat(it.next()).isEqualTo(Card.get(Rank.ACE, Suit.DIAMONDS));
	}

	@Test
	void testMazziereDaiCarta() {
		List<Card> cards = List.of(
				Card.get(Rank.ACE, Suit.HEARTS),
				Card.get(Rank.ACE, Suit.DIAMONDS),
				Card.get(Rank.ACE, Suit.CLUBS)
		);

		Mazziere mazziere = SfidanteTest.createBancoFromListOfCards(cards);

		assertThat(mazziere.daiCarta()).isEqualTo(Card.get(Rank.ACE,Suit.CLUBS));
	}

	@Test
	void testMazziereGioca() {
		List<Card> cards = List.of(
				Card.get(Rank.JACK, Suit.HEARTS),
				Card.get(Rank.TEN, Suit.HEARTS),
				Card.get(Rank.ACE, Suit.HEARTS),
				Card.get(Rank.TWO, Suit.DIAMONDS),
				Card.get(Rank.ACE, Suit.CLUBS)
		);

		Mazziere banco = SfidanteTest.createBancoFromListOfCards(cards);
		banco.carteIniziali();

		banco.gioca();

		assertThat(banco.isSballato()).isEqualTo(true);
		assertThat(banco.getPunti()).isEqualTo(24);
	}
}