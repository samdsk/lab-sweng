package it.unimi.di.sweng.briscola;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class BriscolaTest {

	@Test
	void testEstablishTurnWinner() {
		Player carlo = new Player("Carlo");
		Player maria = new Player("Maria");

		Deck deck = Deck.createEmptyDeck();

		List<Card> cards = List.of(
				Card.get(Rank.CAVALLO,Suit.COPPE),

				Card.get(Rank.ASSO,Suit.DENARI),
				Card.get(Rank.RE,Suit.COPPE),
				Card.get(Rank.ASSO,Suit.BASTONI),

				Card.get(Rank.CAVALLO,Suit.BASTONI),
				Card.get(Rank.TRE,Suit.BASTONI),
				Card.get(Rank.TRE,Suit.COPPE)
		);

		for(Card card : cards) deck.push(card);

		Briscola briscola = new Briscola(carlo,maria,deck);

		Player winner = briscola.establishTurnWinner(
				Card.get(Rank.CAVALLO,Suit.BASTONI), // carlo
				Card.get(Rank.RE,Suit.COPPE) // maria
		);

		assertThat(briscola.getBriscola()).isEqualTo(Suit.COPPE);

		assertThat(winner.getName()).isEqualTo("Maria");
	}

	@Test
	void testEstablishTurnWinnerWithBriscola() {
		Player carlo = new Player("Carlo");
		Player maria = new Player("Maria");

		Deck deck = Deck.createEmptyDeck();

		List<Card> cards = List.of(
				Card.get(Rank.CAVALLO,Suit.COPPE),

				Card.get(Rank.ASSO,Suit.DENARI),
				Card.get(Rank.DUE,Suit.COPPE),
				Card.get(Rank.ASSO,Suit.BASTONI),

				Card.get(Rank.CAVALLO,Suit.BASTONI),
				Card.get(Rank.TRE,Suit.DENARI),
				Card.get(Rank.TRE,Suit.BASTONI)
		);

		for(Card card : cards) deck.push(card);

		Briscola briscola = new Briscola(carlo,maria,deck);

		Player winner = briscola.establishTurnWinner(
				Card.get(Rank.CAVALLO,Suit.BASTONI), // carlo
				Card.get(Rank.DUE,Suit.COPPE) // maria
		);

		assertThat(briscola.getBriscola()).isEqualTo(Suit.COPPE);

		assertThat(winner.getName()).isEqualTo("Maria");
	}

	@Test
	void testEstablishTurnWinnerWith2Briscola() {
		Player carlo = new Player("Carlo");
		Player maria = new Player("Maria");

		Deck deck = Deck.createEmptyDeck();

		List<Card> cards = List.of(
				Card.get(Rank.CAVALLO,Suit.COPPE),

				Card.get(Rank.ASSO,Suit.DENARI),
				Card.get(Rank.TRE,Suit.COPPE),
				Card.get(Rank.ASSO,Suit.BASTONI),

				Card.get(Rank.CAVALLO,Suit.BASTONI),
				Card.get(Rank.DUE,Suit.COPPE),
				Card.get(Rank.TRE,Suit.BASTONI)
		);

		for(Card card : cards) deck.push(card);

		Briscola briscola = new Briscola(carlo,maria,deck);

		Player winner = briscola.establishTurnWinner(
				Card.get(Rank.DUE,Suit.COPPE), // carlo
				Card.get(Rank.TRE,Suit.COPPE) // maria
		);

		assertThat(briscola.getBriscola()).isEqualTo(Suit.COPPE);

		assertThat(winner.getName()).isEqualTo("Maria");
	}

	@Test
	void testEstablishGameWinner() {
		Player carlo = new Player("Carlo");
		carlo.addWonCardsToPersonalDeck(Card.get(Rank.TRE,Suit.COPPE),
				Card.get(Rank.CINQUE,Suit.DENARI));

		Player maria = new Player("Maria");
		maria.addWonCardsToPersonalDeck(Card.get(Rank.ASSO,Suit.COPPE),
				Card.get(Rank.CAVALLO,Suit.DENARI));

		Deck deck = Deck.createFullDeck();
		Briscola briscola = new Briscola(carlo,maria,deck);

		Player winner = briscola.establishGameWinner();

		assertThat(winner.getName()).isEqualTo("Maria");
	}

	@Test
	void testEstablishGameWinnerDrawMatch() {
		Player carlo = new Player("Carlo");
		carlo.addWonCardsToPersonalDeck(Card.get(Rank.TRE,Suit.COPPE),
				Card.get(Rank.CINQUE,Suit.DENARI));

		Player maria = new Player("Maria");
		maria.addWonCardsToPersonalDeck(Card.get(Rank.CAVALLO,Suit.DENARI),
				Card.get(Rank.SETTE,Suit.COPPE));
		maria.addWonCardsToPersonalDeck(Card.get(Rank.RE,Suit.DENARI),
				Card.get(Rank.CAVALLO,Suit.COPPE));


		Deck deck = Deck.createFullDeck();
		Briscola briscola = new Briscola(carlo,maria,deck);

		Player winner = briscola.establishGameWinner();

		assertThat(winner.getName()).isEqualTo("Pareggio!");
	}


}