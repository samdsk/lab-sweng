package it.unimi.di.sweng.lab04;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import ca.mcgill.cs.stg.solitaire.cards.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

class PokerTableTest {
    @Test
    void pokerTableTest() {
        PokerTable pokerTable = new PokerTable(4,new Deck());
        assertThat(pokerTable.numberOfPlayers()).isEqualTo(4);
    }

    @Test
    void getHandTest() {
        PokerTable pokerTable = new PokerTable(4,new Deck());
        PokerHand hand = pokerTable.getHand(1);

        int count = 0;
        for(Card c : hand) count++;

        assertThat(count).isEqualTo(5);
    }


    @Test
    void changePokerHandInPokerTableTest() {
        PokerTable pokerTable = new PokerTable(3,new Deck());
        int player = 1;

        PokerHand hand = pokerTable.getHand(player);
        List<Card> oldHand = new ArrayList<>();

        for(Card c : hand) oldHand.add(c);

        assertThat((Iterable<Card>) pokerTable.getHand(player)).containsExactlyElementsOf(oldHand);

        List<Card> cardToChange = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            cardToChange.add(oldHand.get(i));
        }

        pokerTable.change(player,cardToChange);

        assertThat((Iterable<Card>) pokerTable.getHand(player)).doesNotContainAnyElementsOf(cardToChange);
    }

    @Test
    void changeMoreCardsThanCardStackSizeTest() {
        PokerTable pokerTable = new PokerTable(3,new Deck());
        int player = 2;
        PokerHand hand = pokerTable.getHand(player);

        List<Card> cardToChange = new ArrayList<>();

        for (Card c : hand) {
            cardToChange.add(c);
        }

        assertThatThrownBy(()->pokerTable.change(player,cardToChange))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Can't change be more than 4 cards.");
    }
    private final Card[] THREE_OF_A_KIND = {
            Card.get(Rank.FIVE, Suit.HEARTS),
            Card.get(Rank.FIVE, Suit.DIAMONDS),
            Card.get(Rank.FIVE, Suit.SPADES),
            Card.get(Rank.THREE, Suit.SPADES),
            Card.get(Rank.KING, Suit.SPADES)
    };

    private final Card[] FOUR_OF_A_KIND = {
            Card.get(Rank.TWO, Suit.HEARTS),
            Card.get(Rank.TWO, Suit.DIAMONDS),
            Card.get(Rank.TWO, Suit.SPADES),
            Card.get(Rank.TWO, Suit.CLUBS),
            Card.get(Rank.KING, Suit.DIAMONDS)
    };
    private final Card[] TWO_PAIR = {
            Card.get(Rank.SEVEN, Suit.HEARTS),
            Card.get(Rank.SEVEN, Suit.DIAMONDS),
            Card.get(Rank.FOUR, Suit.SPADES),
            Card.get(Rank.FOUR, Suit.CLUBS),
            Card.get(Rank.KING, Suit.CLUBS)
    };
    @Test
    void pokerTableIterableTest() {
        Deck deck = new Deck();

        for (int i = 0; i < 52; i++) {
            deck.draw();
        }

        for(Card card : TWO_PAIR){ // player 2
            deck.push(card);
        }

        for(Card card : FOUR_OF_A_KIND){ // player 1
            deck.push(card);
        }

        for(Card card : THREE_OF_A_KIND){ // player 0
            deck.push(card);
        }

        // ordine : 1 0 2


        PokerTable pokerTable = new PokerTable(3,deck);

        for (int i = 0; i < 3; i++) {
            System.out.println("player "+i+" - "+pokerTable.getHand(i).toString());

        }

        Iterator<Integer> it = pokerTable.iterator();

        assertThat(it.next()).isEqualTo(1);
        assertThat(it.next()).isEqualTo(0);
        assertThat(it.next()).isEqualTo(2);
    }
}