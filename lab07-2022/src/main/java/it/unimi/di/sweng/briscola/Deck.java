package it.unimi.di.sweng.briscola;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.LinkedList;


/*
 * A differenza del solito, di default si crea un deck vuoto.
 * per creare un mazzo completo e mischiato bisogna successivamente chiamare shuffleFullDeck
 */


public class Deck {
  private final LinkedList<Card> cards;

  private Deck() {
    cards = new LinkedList<>();
  }

  @NotNull
  static Deck createEmptyDeck() {
    return new Deck();
  }

  @NotNull
  static Deck createFullDeck() {
    Deck deck = new Deck();
    deck.shuffleFullDeck();
    return deck;
  }

  private void shuffleFullDeck() {
    cards.clear();
    for (Suit suit : Suit.values())
      for (Rank rank : Rank.values())
        cards.add(Card.get(rank, suit));
    Collections.shuffle(cards);
  }

  public void push(@NotNull Card card) {
    assert !cards.contains(card);
    cards.push(card);
  }

  @NotNull
  public Card draw() {
    assert !isEmpty();
    return cards.pop();
  }

  public boolean isEmpty() {
    return cards.isEmpty();
  }

  public int remainingCards() {
    return cards.size();
  }
}
