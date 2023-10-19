package it.unimi.di.sweng.blackjack;

import ca.mcgill.cs.stg.solitaire.cards.Card;
import ca.mcgill.cs.stg.solitaire.cards.Deck;

import java.util.List;


public class MultiMazzo implements DeckInterface {
  // TODO deve implementare DeckInterface
  private final Deck[] decks;
  private int deckIndex;

  /**
   *
   * @param numMazzi il numero di mazzi
   * @throws IllegalArgumentException se {numMazzi} è < 1
   */
  public MultiMazzo(int numMazzi) {
    //TODO definire un costruttore che crei un mazzo composto da numMazzi mescolato casualmente

    if(numMazzi<1) throw new IllegalArgumentException("Numero di mazzi deve essere >= 1");

    decks = new Deck[numMazzi];

    for (int i = 0; i < numMazzi; i++) {
      decks[i] = new Deck();
    }
  }

  public MultiMazzo(List<Deck> list){
    int n = list.size();

    if(n<1)
      throw new IllegalArgumentException("List deve contenere almeno un deck");

    decks = new Deck[n];

    for (int i = 0; i < n; i++) {
      decks[i] = list.get(i);
    }

  }

  public int getDecksNumber() {
    return decks.length;
  }

  @Override
  public Card draw() {
    if(isEmpty())
      throw new IllegalStateException("Can't draw from an empty multi deck");

    if(deckIndex >= getDecksNumber()) deckIndex = 0;

    Deck deck = decks[deckIndex];

    if(deck.isEmpty()){
      deckIndex++;
      return draw();
    }
    else return deck.draw();
  }

  @Override
  public boolean isEmpty() {
    for(Deck deck : decks){
      if(!deck.isEmpty()) return false;
    }

    return true;
  }

}
