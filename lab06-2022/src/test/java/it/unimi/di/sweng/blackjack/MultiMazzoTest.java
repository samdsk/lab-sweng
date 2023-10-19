package it.unimi.di.sweng.blackjack;

import ca.mcgill.cs.stg.solitaire.cards.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MultiMazzoTest {
    @Test
    void testMultiDeckConstructor() {
        MultiMazzo multiMazzo = new MultiMazzo(6);
        assertThat(multiMazzo.getDecksNumber()).isEqualTo(6);
    }

    @Test
    void testMultiDeckConZeroMazzi() {
        assertThatThrownBy(()-> new MultiMazzo(0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Numero di mazzi deve essere >= 1");
    }

    @Test
    void testMultiMazzoFromListOfDecks() {
        MultiMazzo multiMazzo = new MultiMazzo(List.of(new Deck(),new Deck()));
        assertThatThrownBy(() -> new MultiMazzo(List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("List deve contenere almeno un deck");
        assertThat(multiMazzo.getDecksNumber()).isEqualTo(2);
    }

    @Test
    void testMultiDeckDrawFromOneDeck() {
        List<Deck> decks = new ArrayList<>();

        Deck deck = emptyDeck();
        deck.push(Card.get(Rank.ACE, Suit.HEARTS));
        decks.add(deck);

        MultiMazzo multiMazzo = new MultiMazzo(decks);

        Card card = multiMazzo.draw();

        assertThat(card).isEqualTo(Card.get(Rank.ACE,Suit.HEARTS));
    }

    @Test
    void testMultiDeckEmpty() {
        List<Deck> decks = new ArrayList<>();
        Deck deck_1 = emptyDeck();
        deck_1.push(Card.get(Rank.ACE,Suit.HEARTS));

        decks.add(deck_1);
        MultiMazzo multiMazzo = new MultiMazzo(decks);
        multiMazzo.draw();
        assertThat(multiMazzo.isEmpty()).isEqualTo(true);
    }

    @Test
    void testMultiDeckDrawFrom3Decks() {
        List<Deck> decks = new ArrayList<>();
        Deck deck_1 = emptyDeck();
        Deck deck_2 = emptyDeck();
        Deck deck_3 = emptyDeck();

        deck_1.push(Card.get(Rank.ACE,Suit.HEARTS));

        deck_2.push(Card.get(Rank.JACK,Suit.CLUBS));
        deck_2.push(Card.get(Rank.KING,Suit.CLUBS));

        deck_3.push(Card.get(Rank.KING,Suit.CLUBS));

        decks.add(deck_1);
        decks.add(deck_2);
        decks.add(deck_3);

        MultiMazzo multiMazzo = new MultiMazzo(decks);

        assertThat(multiMazzo.draw()).isEqualTo(Card.get(Rank.ACE,Suit.HEARTS));
        assertThat(multiMazzo.draw()).isEqualTo(Card.get(Rank.KING,Suit.CLUBS));
        assertThat(multiMazzo.draw()).isEqualTo(Card.get(Rank.JACK,Suit.CLUBS));
        assertThat(multiMazzo.draw()).isEqualTo(Card.get(Rank.KING,Suit.CLUBS));
    }

    public static Deck emptyDeck(){
        Deck deck = new Deck();

        for (int i = 0; i < 52; i++) {
            deck.draw();
        }

        return deck;
    }
}