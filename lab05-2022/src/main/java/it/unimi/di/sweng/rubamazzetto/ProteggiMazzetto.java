package it.unimi.di.sweng.rubamazzetto;

import ca.mcgill.cs.stg.solitaire.cards.Card;

import java.util.List;

public class ProteggiMazzetto implements SelettoreCarta {
    SelettoreCarta next;
    public ProteggiMazzetto(SelettoreCarta next) {
        this.next = next;
    }

    @Override
    public CardAndPoints strategy(Partita partita, List<Card> mano, Giocatore giocatore) {
        for(Card card : mano){
            if(giocatore.getMazzettoTop() == card.getRank()
                    && !partita.getTavolo().inMostra(card)){
                partita.getTavolo().metti(card);
                return new CardAndPoints(card,0);
            }
        }

        return next.strategy(partita,mano,giocatore);

    }
}
