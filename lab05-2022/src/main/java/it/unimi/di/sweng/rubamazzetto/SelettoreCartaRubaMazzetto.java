package it.unimi.di.sweng.rubamazzetto;

import ca.mcgill.cs.stg.solitaire.cards.Card;
import ca.mcgill.cs.stg.solitaire.cards.Rank;

import java.util.List;

public class SelettoreCartaRubaMazzetto implements SelettoreCarta {
    SelettoreCarta next;
    public SelettoreCartaRubaMazzetto(SelettoreCarta next) {
        this.next = next;
    }

    @Override
    public CardAndPoints strategy(Partita partita, List<Card> mano,Giocatore giocatore) {
        for(Giocatore otherPlayer : partita){
            if(otherPlayer == giocatore) continue;

            for(Card card : mano){
                if(partita.getTavolo().inMostra(card)) continue;

                Rank mazzettoTop = otherPlayer.getMazzettoTop();
                if(mazzettoTop != null && mazzettoTop == card.getRank()){
                    return new CardAndPoints(card,otherPlayer.getPunti());
                }
            }
        }

        return next.strategy(partita,mano,giocatore);

    }
}
