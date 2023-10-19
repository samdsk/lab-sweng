package it.unimi.di.sweng.rubamazzetto;

import ca.mcgill.cs.stg.solitaire.cards.*;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;



class TavoloTest {

    List<Card> four_cards = List.of(
            Card.get(Rank.ACE, Suit.HEARTS),
            Card.get(Rank.ACE, Suit.DIAMONDS),
            Card.get(Rank.ACE, Suit.CLUBS),
            Card.get(Rank.ACE, Suit.SPADES));
    @Test
    void testTavoloIterator() {
        Tavolo tavolo = new Tavolo();

        for(Card card : four_cards)
            tavolo.metti(card);

        assertThat(tavolo).containsExactlyElementsOf(four_cards);
    }
}