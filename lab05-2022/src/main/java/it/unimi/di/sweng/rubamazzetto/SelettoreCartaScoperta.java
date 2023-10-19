package it.unimi.di.sweng.rubamazzetto;

import ca.mcgill.cs.stg.solitaire.cards.Card;

import java.util.List;

public class SelettoreCartaScoperta implements SelettoreCarta {
    SelettoreCarta next;
    public SelettoreCartaScoperta(SelettoreCarta next) {
        this.next = next;
    }

    @Override
    public CardAndPoints strategy(Partita partita, List<Card> mano,Giocatore giocatore) {
        Tavolo tavolo = partita.getTavolo();

        for(Card card : mano){
            if(tavolo.inMostra(card)){
                return new CardAndPoints(card,1);
            }
        }

        return next.strategy(partita,mano,giocatore);

    }
}
