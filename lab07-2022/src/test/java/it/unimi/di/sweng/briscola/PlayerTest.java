package it.unimi.di.sweng.briscola;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PlayerTest {
	@Test
	void testPlayerComparable() {
		Player carlo = new Player("Carlo");
		carlo.addWonCardsToPersonalDeck(Card.get(Rank.ASSO,Suit.BASTONI),Card.get(Rank.RE,Suit.BASTONI));

		Player maria = new Player("Maria");
		maria.addWonCardsToPersonalDeck(Card.get(Rank.FANTE,Suit.DENARI),Card.get(Rank.CAVALLO,Suit.COPPE));

		assertThat(carlo.compareTo(maria)).isEqualTo(10);
	}

	@Test
	void testPlayerIterable() {

		Card[] cards = {
			Card.get(Rank.ASSO, Suit.BASTONI),
			Card.get(Rank.FANTE, Suit.DENARI),
			Card.get(Rank.RE, Suit.BASTONI)
		};

		Player carlo = createPlayer("Carlo",cards);

		assertThat((Iterable<Card>) carlo).containsExactly(cards);
	}

	@Test
	void testStrategyPlayFirstCard() {
		Card[] cards = {
			Card.get(Rank.CAVALLO,Suit.BASTONI),
			Card.get(Rank.CINQUE,Suit.DENARI),
			Card.get(Rank.ASSO,Suit.SPADE)
		};

		Player player = createPlayer("Carlo",cards);

		player.setFirstStrategy(new PlayFirstCard());

		Briscola mockBriscola = mock(Briscola.class);

		Card playedCard = player.chooseFirstCard(mockBriscola);

		assertThat(playedCard).isEqualTo(cards[0]);
	}

	@Test
	void testGiocaSemeBriscola() {
		// gioca la prima carta con il seme briscola

		Card[] cards = {
				Card.get(Rank.CAVALLO,Suit.BASTONI),
				Card.get(Rank.CINQUE,Suit.DENARI),
				Card.get(Rank.ASSO,Suit.DENARI)
		};

		Player player = createPlayer("Carlo",cards);

		player.setFirstStrategy(new PlaySemeBriscola(new PlayFirstCard()));

		Briscola mockBriscola = mock(Briscola.class);
		when(mockBriscola.getBriscola()).thenReturn(Suit.DENARI);

		Card playedCard = player.chooseFirstCard(mockBriscola);

		assertThat(playedCard).isEqualTo(cards[1]);
	}

	@Test
	void testGiocaCartaDiRankMinore() {
		Card[] cards = {
				Card.get(Rank.SETTE,Suit.BASTONI),
				Card.get(Rank.CINQUE,Suit.DENARI),
				Card.get(Rank.ASSO,Suit.DENARI)
		};

		Player player = createPlayer("Carlo",cards);

		player.setFirstStrategy(new PlayLeastValuable(new PlayFirstCard()));

		Briscola mockBriscola = mock(Briscola.class);

		Card playedCard = player.chooseFirstCard(mockBriscola);

		assertThat(playedCard).isEqualTo(cards[1]);

	}

	@Test
	void testGiocaCartaDiValoreAlto() {
		// gioca la carta di valore di basso
		// ma sufficientemente alto rispetto l'avversario

		Card[] cards1 = {
				Card.get(Rank.SEI,Suit.BASTONI),
				Card.get(Rank.CAVALLO,Suit.DENARI),
				Card.get(Rank.RE,Suit.DENARI)
		};
		Card[] cards2 = {
				Card.get(Rank.SETTE,Suit.BASTONI),
				Card.get(Rank.CINQUE,Suit.DENARI),
				Card.get(Rank.FANTE,Suit.DENARI)
		};

		Player player1 = createPlayer("Carlo",cards1);
		Player player2 = createPlayer("Maria",cards2);

		player1.setFirstStrategy(new PlayLeastValuableToBeatOtherPlayer(new PlayFirstCard()));

		Briscola mockBriscola = mock(Briscola.class);
		when(mockBriscola.getBriscola()).thenReturn(Suit.COPPE);
		when(mockBriscola.otherPlayer(player1)).thenReturn(player2);

		Card playedCard = player1.chooseFirstCard(mockBriscola);

		assertThat(playedCard).isEqualTo(cards1[1]);

	}

	@Test
	void testGiocaLaCartaSufficientementeAlta() {
		// strat seconda carta: gioca la carta
		// sufficientemente alta per battere la cart agiocata dall'avversario

		Card[] cards1 = {
				Card.get(Rank.RE,Suit.DENARI),
				Card.get(Rank.SEI,Suit.BASTONI),
				Card.get(Rank.CAVALLO,Suit.DENARI)
		};
		Card[] cards2 = {
				Card.get(Rank.SETTE,Suit.BASTONI),
				Card.get(Rank.CINQUE,Suit.DENARI),
				Card.get(Rank.TRE,Suit.DENARI)
		};

		Player player1 = createPlayer("Carlo",cards1);
		Player player2 = createPlayer("Maria",cards2);

		player1.setFirstStrategy(new PlayFirstCard());
		player2.setSecondStrategy(new PlayLeastValuableToBeatOtherPlayerSecondStrat(new PlayFirstCard()));

		Briscola mockBriscola = mock(Briscola.class);
		when(mockBriscola.getBriscola()).thenReturn(Suit.COPPE);
		when(mockBriscola.otherPlayer(player1)).thenReturn(player2);
		when(mockBriscola.otherPlayer(player2)).thenReturn(player1);

		Card playedCard1 = player1.chooseFirstCard(mockBriscola);
		Card playedCard2 = player2.chooseSecondCard(mockBriscola);

		assertThat(playedCard1).isEqualTo(cards1[0]);
		assertThat(playedCard2).isEqualTo(cards2[2]);

	}

	public static Player createPlayer(String name, Card... cards){
		Player player = new Player(name);

		for(Card card : cards) player.giveCard(card);

		return player;
	}
}