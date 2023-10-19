package it.unimi.di.sweng.rubamazzetto;

import ca.mcgill.cs.stg.solitaire.cards.Card;

import java.util.List;

public class SelettorePrimaCarta implements SelettoreCarta {
    SelettoreCarta next;
    public SelettorePrimaCarta(SelettoreCarta next) {
        this.next = next;
    }

    @Override
    public CardAndPoints strategy(Partita partita, List<Card> mano, Giocatore giocatore) {
        Tavolo tavolo = partita.getTavolo();
        if(mano.size() == 0) return null;

        Card card = mano.get(0);

        tavolo.metti(card);
        return new CardAndPoints(card,0);

    }
}
