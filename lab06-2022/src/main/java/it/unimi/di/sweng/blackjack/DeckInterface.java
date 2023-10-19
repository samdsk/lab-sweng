package it.unimi.di.sweng.blackjack;

import ca.mcgill.cs.stg.solitaire.cards.Card;

public interface DeckInterface {
  Card draw();

  boolean isEmpty();
}
