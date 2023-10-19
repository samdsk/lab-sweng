package it.unimi.di.sweng.blackjack;

import ca.mcgill.cs.stg.solitaire.cards.*;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SfidanteTest {
	@Test
	void testSfidanteInit() {
		Mazziere banco = new Mazziere();
		Sfidante sfidante = new Sfidante("Carlo",banco);

		assertThat(sfidante.getName()).isEqualTo("Carlo");
	}

	@Test
	void testCarteIniziali() {
		List<Card> cards = List.of(
				Card.get(Rank.ACE,Suit.HEARTS),
				Card.get(Rank.ACE,Suit.DIAMONDS)
		);

		Mazziere banco = createBancoFromListOfCards(cards);

		Sfidante carlo = new Sfidante("Carlo", banco);

		carlo.carteIniziali();
		Iterator<Card> it = carlo.getCards();

		assertThat(it.next()).isEqualTo(Card.get(Rank.ACE,Suit.DIAMONDS));
		assertThat(it.next()).isEqualTo(Card.get(Rank.ACE,Suit.HEARTS));
	}

	@Test
	void testGetPuntiSfidante() {
		List<Card> cards = List.of(
				Card.get(Rank.ACE,Suit.DIAMONDS),
				Card.get(Rank.ACE,Suit.HEARTS),
				Card.get(Rank.FIVE,Suit.HEARTS),
				Card.get(Rank.JACK,Suit.DIAMONDS)
		);

		Mazziere banco = createBancoFromListOfCards(cards);

		Sfidante carlo = new Sfidante("Carlo", banco);
		Sfidante maria = new Sfidante("Maria", banco);

		carlo.carteIniziali();
		maria.carteIniziali();

		assertThat(carlo.getPunti()).isEqualTo(15);
		assertThat(maria.getPunti()).isEqualTo(12);
	}

	@Test
	void testIsGiocatoreBJSballato() {

		List<Card> cards = List.of(
				Card.get(Rank.FIVE,Suit.HEARTS),
				Card.get(Rank.JACK,Suit.DIAMONDS),
				Card.get(Rank.TEN,Suit.DIAMONDS)
		);

		Mazziere banco = createBancoFromListOfCards(cards);

		Sfidante carlo = new Sfidante("Carlo", banco);

		carlo.carteIniziali();
		carlo.prendiCartaDalBanco();

		assertThat(carlo.getPunti()).isEqualTo(25);
		assertThat(carlo.isSballato()).isEqualTo(true);
	}

	@Test
	void testStrategiaMassimissaPunteggio() {

		List<Card> cards = List.of(
					Card.get(Rank.FIVE,Suit.HEARTS),
					Card.get(Rank.THREE,Suit.HEARTS),
					Card.get(Rank.EIGHT,Suit.HEARTS),
					Card.get(Rank.JACK,Suit.DIAMONDS),
					Card.get(Rank.TEN,Suit.DIAMONDS),
					Card.get(Rank.SEVEN,Suit.CLUBS),
					Card.get(Rank.THREE,Suit.DIAMONDS)
				);

		Mazziere banco = createBancoFromListOfCards(cards);

		Sfidante carlo = new Sfidante("Carlo", banco);

		carlo.carteIniziali();
		banco.carteIniziali();

		carlo.setStrategia(new MassimissaPunteggioStrategy(Strategia.STARE));

		carlo.gioca();

		assertThat(carlo.isSballato()).isEqualTo(false);
		assertThat(carlo.getPunti()).isEqualTo(18);

	}

	@Test
	void testRaddoppiaStrategy() {
		List<Card> cards = List.of(
				Card.get(Rank.FIVE,Suit.HEARTS),
				Card.get(Rank.THREE,Suit.HEARTS),
				Card.get(Rank.TEN,Suit.HEARTS),

				Card.get(Rank.JACK,Suit.DIAMONDS),
				Card.get(Rank.SEVEN,Suit.DIAMONDS),

				Card.get(Rank.SEVEN,Suit.CLUBS),
				Card.get(Rank.THREE,Suit.DIAMONDS)
		);

		Mazziere banco = createBancoFromListOfCards(cards);

		Sfidante carlo = new Sfidante("Carlo", banco);

		carlo.carteIniziali();
		banco.carteIniziali();

		Strategia raddoppiaStrategy = new RaddoppiaStrategy(Strategia.STARE);

		carlo.setStrategia(raddoppiaStrategy);

		carlo.gioca();

		assertThat(carlo.isSballato()).isEqualTo(false);
		assertThat(carlo.getPunti()).isEqualTo(20);

	}

	@Test
	void testTerzoStrategy() {
		List<Card> cards = List.of(
				Card.get(Rank.FIVE,Suit.HEARTS),
				Card.get(Rank.THREE,Suit.HEARTS),
				Card.get(Rank.TWO,Suit.HEARTS),

				Card.get(Rank.JACK,Suit.DIAMONDS),
				Card.get(Rank.SEVEN,Suit.DIAMONDS),

				Card.get(Rank.TWO,Suit.CLUBS),
				Card.get(Rank.THREE,Suit.DIAMONDS)
		);

		Mazziere banco = createBancoFromListOfCards(cards);

		Sfidante carlo = new Sfidante("Carlo", banco);

		carlo.carteIniziali();
		banco.carteIniziali();

		Strategia terzoStrategy = new TerzoStrategy(Strategia.STARE);

		carlo.setStrategia(terzoStrategy);

		carlo.gioca();

		assertThat(carlo.isSballato()).isEqualTo(false);
		assertThat(carlo.getPunti()).isEqualTo(10);

	}

	public static Mazziere createBancoFromListOfCards(List<Card> cards){
		Deck deck = MultiMazzoTest.emptyDeck();

		for(Card card : cards) deck.push(card);

		MultiMazzo multiMazzo = new MultiMazzo(List.of(deck));
		return new Mazziere(multiMazzo);
	}
}