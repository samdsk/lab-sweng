package it.unimi.di.sweng.rubamazzetto;

import ca.mcgill.cs.stg.solitaire.cards.Card;

import java.util.List;

public interface SelettoreCarta {
    CardAndPoints strategy(Partita partita, List<Card> mano, Giocatore giocatore);
}
