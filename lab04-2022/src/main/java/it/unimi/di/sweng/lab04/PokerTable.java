package it.unimi.di.sweng.lab04;

import ca.mcgill.cs.stg.solitaire.cards.Card;
import ca.mcgill.cs.stg.solitaire.cards.CardStack;
import ca.mcgill.cs.stg.solitaire.cards.Deck;

import java.util.*;

public class PokerTable implements Iterable<Integer>{
    private static final int POKER_HAND_SIZE = 5;
    private final Deck deck;
    private int numberOfPlayers;
    private final Map<Integer,PokerHand> players;

    public PokerTable(int n,Deck deck) {
        this.deck = deck;
        numberOfPlayers = n;

        players = new HashMap<>();

        for (int i = 0; i < numberOfPlayers; i++) {
            players.put(i,new PokerHand(deck,POKER_HAND_SIZE));
        }

    }

    public int numberOfPlayers() {
        return this.numberOfPlayers;
    }

    public PokerHand getHand(int i) {
        PokerHand temp = players.get(i);

        List<Card> cardList = new ArrayList<>();

        for(Card card : temp)
            cardList.add(card);

        PokerHand output = new PokerHand(cardList);

        return output;
    }

    public void change(int player, List<Card> cardToChange) {
        if(cardToChange.size() >= POKER_HAND_SIZE)
            throw new IllegalArgumentException("Can't change be more than "+(POKER_HAND_SIZE-1)+" cards.");

        PokerHand playerHand = players.get(player);

        List<Card> cardListNew = new ArrayList<>();

        for(Card card : playerHand){
            if(cardToChange.contains(card)){
                cardListNew.add(deck.draw());
            }else cardListNew.add(card);
        }

        players.put(player,new PokerHand(cardListNew));

    }

    public Iterator<Integer> iterator() {
        List<Map.Entry<Integer,PokerHand>> list = new ArrayList<>(players.entrySet());
        list.sort(Map.Entry.comparingByValue());

        List<Integer> output = new ArrayList<>();

        for(Map.Entry<Integer,PokerHand> e : list){
            output.add(e.getKey());
        }
        Collections.reverse(output);

        return output.iterator();
    }
}
