package it.unimi.di.sweng.blackjack;

import ca.mcgill.cs.stg.solitaire.cards.Card;
import ca.mcgill.cs.stg.solitaire.cards.Rank;

import java.util.Iterator;

public interface GiocatoreBJ {
  void carteIniziali();

  void gioca();

  Iterator<Card> getCards();

  String getName();

  default int getPunti() {
    // DONE quanto valgono le carte? Occhio agli assi!
    int sum = 0;
    int aceCount = 0;

    for(Iterator<Card> it = getCards(); it.hasNext();){
      Card card = it.next();
      if(card.getRank() == Rank.ACE){
        aceCount++;
      }else{
        sum += BlackJack.cardValue(card);
      }
    }

    for (int i = 0; i < aceCount; i++) {
      if(sum + 11 > 21) sum++;
      else sum += 11;
    }

    return sum;
  };

  default boolean isSballato() {
    return getPunti() > 21;
  }

  default String asString() {
    final StringBuilder sb = new StringBuilder(getName());
    sb.append(": ").append("[").append(getPunti()).append("] ");
    for (Iterator<Card> cards = getCards(); cards.hasNext(); ) {
      Card card = cards.next();
      sb.append(card).append(" ");
    }
    if (isSballato()) {
      sb.append("SBALLATO");
    }
    return sb.toString();
  }
}
