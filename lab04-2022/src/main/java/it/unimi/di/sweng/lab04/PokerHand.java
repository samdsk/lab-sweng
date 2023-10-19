package it.unimi.di.sweng.lab04;

import ca.mcgill.cs.stg.solitaire.cards.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;


public class PokerHand implements Comparable<PokerHand>, Iterable<Card>{
    private final CardStack cardStack;
    private int numberOfCards;
    private static final ChainedHandEvaluator chain = new TwoPairEvaluator(
            new ThreeOfAKindEvaluator(
                    new FourOfAKindEvaluator(null)));

    public PokerHand(String cards) {
        cardStack = new CardStack();
        numberOfCards = 0;

        Scanner scanner = new Scanner(cards);

        while(scanner.hasNext()){
            String card = scanner.next();
            char suitChar = card.charAt(card.length()-1);

            try {
                Suit suit = parseSuit(suitChar);
                Rank rank = parseRank(card.substring(0,card.length()-1));
                cardStack.push(Card.get(rank,suit));
                numberOfCards++;
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(card+" is not a supported card");
            }

        }

    }

    public PokerHand(List<Card> listOfCards){
        cardStack = new CardStack(listOfCards);
        numberOfCards = cardStack.size();
    }

    public PokerHand(Deck deck, int numberOfCards) {
        cardStack = new CardStack();

        for (int i = 0; i < numberOfCards; i++) {
            cardStack.push(deck.draw());
        }

        this.numberOfCards = numberOfCards;
    }



    public static Rank parseRank(String rank) {
        int r = -1;
        if(isNumber(rank)) {
            r += Integer.parseInt(rank);
            return Rank.values()[r];
        }

        return getRankFromNotNumberChar(rank.charAt(0));
    }

    /**
    * @param rank requires character 'J', 'Q' or 'K'
    * @throws IllegalArgumentException if char is not supported
    * @return Rank
    */
    @NotNull
    private static Rank getRankFromNotNumberChar(char rank) {
        switch (rank){
            case 'J':
                return Rank.JACK;
            case 'K':
                return Rank.KING;
            case 'Q':
                return Rank.QUEEN;
            default: throw new IllegalArgumentException("Unknown Rank");
        }
    }

    public static boolean isNumber(String number) {
        try{
            Integer.parseInt(number);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }

    /**
     * @param suit excepts only 'H','D','C' or 'S'
     * @return Suit
     * @throws IllegalArgumentException if character not supported
     */

    public static Suit parseSuit(char suit) {
        switch (suit){
            case 'H': return Suit.HEARTS;
            case 'D': return Suit.DIAMONDS;
            case 'C': return Suit.CLUBS;
            case 'S': return Suit.SPADES;
            default: throw new IllegalArgumentException("Unknown Suit");
        }
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < numberOfCards; i++) {
            Card temp = cardStack.peek(i);
            output.append(temp.toString());
            if (i < numberOfCards - 1) output.append(", ");
        }
        return output.toString();
    }

    public HandRank getRank() {
        return chain.eval(this);
    }

    @NotNull
    @Override
    public Iterator<Card> iterator() {
        List<Card> output = new ArrayList<>();

        for (int i = 0; i < numberOfCards; i++) {
            Card temp = cardStack.peek(i);
            output.add(Card.get(temp.getRank(),temp.getSuit()));
        }

        return output.iterator();
    }

    @Override
    public int compareTo(@NotNull PokerHand p) {
        if(this.getRank().ordinal()< p.getRank().ordinal()){
            return -1;
        }else if(this.getRank().ordinal() > p.getRank().ordinal()) {
            return 1;
        }

        return 0;

    }
}
