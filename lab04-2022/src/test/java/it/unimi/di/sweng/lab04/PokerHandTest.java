package it.unimi.di.sweng.lab04;


import static org.assertj.core.api.Assertions.*;


import ca.mcgill.cs.stg.solitaire.cards.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Timeout(2)
public class PokerHandTest {

    private final Card[] THREE_OF_A_KIND = {
            Card.get(Rank.FIVE, Suit.HEARTS),
            Card.get(Rank.FIVE, Suit.DIAMONDS),
            Card.get(Rank.FIVE, Suit.SPADES),
            Card.get(Rank.THREE, Suit.SPADES),
            Card.get(Rank.KING, Suit.SPADES)
    };

    private final Card[] FOUR_OF_A_KIND = {
            Card.get(Rank.FIVE, Suit.HEARTS),
            Card.get(Rank.FIVE, Suit.DIAMONDS),
            Card.get(Rank.FIVE, Suit.SPADES),
            Card.get(Rank.FIVE, Suit.CLUBS),
            Card.get(Rank.KING, Suit.SPADES)
    };
    private final Card[] TWO_PAIR = {
            Card.get(Rank.FIVE, Suit.HEARTS),
            Card.get(Rank.FIVE, Suit.DIAMONDS),
            Card.get(Rank.THREE, Suit.SPADES),
            Card.get(Rank.THREE, Suit.CLUBS),
            Card.get(Rank.KING, Suit.SPADES)
    };
    private final Card[] DIFFERENT = {
            Card.get(Rank.ACE, Suit.HEARTS),
            Card.get(Rank.FIVE, Suit.DIAMONDS),
            Card.get(Rank.THREE, Suit.SPADES),
            Card.get(Rank.TEN, Suit.CLUBS),
            Card.get(Rank.KING, Suit.SPADES)
    };

    private Deck getEmptyDeck() {
        Deck deck = new Deck();
        for (int i = 0; i < 52; i++) {
            deck.draw();
        }

        return deck;
    }

    private Deck createDeckWithCards(Card[] cards) {
        Deck deck = getEmptyDeck();
        for (Card card : cards) deck.push(card);
        return deck;
    }

    @Test
    public void noInputOrEmptyInputTest() {
        PokerHand pokerHand = new PokerHand("");
        assertThat(pokerHand.toString()).isEqualTo("");
    }

    @Test
    public void pokerHandTest() {
        Card[] cards = {
                Card.get(Rank.ACE, Suit.HEARTS),
                Card.get(Rank.TWO, Suit.HEARTS),
                Card.get(Rank.THREE, Suit.HEARTS),
                Card.get(Rank.FOUR, Suit.HEARTS),
                Card.get(Rank.FIVE, Suit.HEARTS)
        };

        Deck deck = createDeckWithCards(cards);

        PokerHand pokerHand = new PokerHand(deck, 5);

        assertThat(pokerHand.toString()).isEqualTo("FIVE of HEARTS, FOUR of HEARTS, THREE of HEARTS, TWO of HEARTS, ACE of HEARTS");
    }

    @Test
    void getRankTest() {
        Deck deck = createDeckWithCards(THREE_OF_A_KIND);

        PokerHand pokerHand = new PokerHand(deck, 5);

        assertThat(pokerHand.getRank()).isEqualTo(HandRank.THREE_OF_A_KIND);
    }

    @Test
    void getRankFourOfAKind() {
        Deck deck = createDeckWithCards(FOUR_OF_A_KIND);

        PokerHand pokerHand = new PokerHand(deck, 5);

        assertThat(pokerHand.getRank()).isEqualTo(HandRank.FOUR_OF_A_KIND);
    }

    @Test
    void pokerHandIteratorTest() {
        Deck deck = createDeckWithCards(FOUR_OF_A_KIND);
        PokerHand pokerHand = new PokerHand(deck, 5);

        assertThat((Iterable<Card>) pokerHand).contains(FOUR_OF_A_KIND);
    }

    @Test
    void chainedHandEvaluatorTest() {
        ChainedHandEvaluator chain = new TwoPairEvaluator(null);

        Deck deck = createDeckWithCards(TWO_PAIR);
        PokerHand pokerHand = new PokerHand(deck, 5);

        assertThat(chain.eval(pokerHand)).isEqualTo(HandRank.TWO_PAIR);
    }

    @Test
    void threeOfAKindEvaluatorTest() {
        ChainedHandEvaluator chain = new TwoPairEvaluator(new ThreeOfAKindEvaluator(null));

        Deck deck = createDeckWithCards(THREE_OF_A_KIND);
        PokerHand pokerHand = new PokerHand(deck, 5);

        assertThat(chain.eval(pokerHand)).isEqualTo(HandRank.THREE_OF_A_KIND);
    }

    @Test
    void fourOfAKindEvaluatorTest() {
        ChainedHandEvaluator chain = new TwoPairEvaluator(
                new ThreeOfAKindEvaluator(
                        new FourOfAKindEvaluator(null)));

        Deck deck = createDeckWithCards(FOUR_OF_A_KIND);
        PokerHand pokerHand = new PokerHand(deck, 5);

        assertThat(chain.eval(pokerHand)).isEqualTo(HandRank.FOUR_OF_A_KIND);
    }

    @Test
    void pokerHandFromStringTest() {
        PokerHand pokerHand = new PokerHand("7H JC JH 1C 4C");
        assertThat(pokerHand.toString()).isEqualTo("SEVEN of HEARTS, JACK of CLUBS, JACK of HEARTS, ACE of CLUBS, FOUR of CLUBS");
    }

    @Test
    void parseRankTest() {
        assertThat(PokerHand.parseRank("10")).isEqualTo(Rank.TEN);
    }

    @Test
    void isItANumberTest() {
        assertThat(PokerHand.isNumber("10")).isEqualTo(true);
        assertThat(PokerHand.isNumber("J")).isEqualTo(false);
    }

    @Test
    void parseRankNotNumberTest() {
        assertThat(PokerHand.parseRank("J")).isEqualTo(Rank.JACK);
        assertThat(PokerHand.parseRank("Q")).isEqualTo(Rank.QUEEN);
        assertThat(PokerHand.parseRank("K")).isEqualTo(Rank.KING);
    }

    @Test
    void parseSuitTest() {
        assertThat(PokerHand.parseSuit('H')).isEqualTo(Suit.HEARTS);
        assertThat(PokerHand.parseSuit('D')).isEqualTo(Suit.DIAMONDS);
        assertThat(PokerHand.parseSuit('C')).isEqualTo(Suit.CLUBS);
        assertThat(PokerHand.parseSuit('S')).isEqualTo(Suit.SPADES);
    }

    @Test
    void constructPokerHandWithListOfCardsTest() {
        PokerHand pokerHand = new PokerHand(Arrays.asList(DIFFERENT));
        int i = 0;
        for(Card  card: pokerHand){
            assertThat(card).isEqualTo(DIFFERENT[i++]);
        }
    }

    @Test
    void pokerHandComparableTest() {
        PokerHand[] pokerHands = new PokerHand[3];

        pokerHands[0] = new PokerHand(Arrays.asList(THREE_OF_A_KIND));
        pokerHands[1] = new PokerHand(Arrays.asList(FOUR_OF_A_KIND));
        pokerHands[2] = new PokerHand(Arrays.asList(TWO_PAIR));

        Arrays.sort(pokerHands);

        assertThat(pokerHands[0].getRank()).isEqualTo(HandRank.TWO_PAIR);
        assertThat(pokerHands[1].getRank()).isEqualTo(HandRank.THREE_OF_A_KIND);
        assertThat(pokerHands[2].getRank()).isEqualTo(HandRank.FOUR_OF_A_KIND);
    }
}
