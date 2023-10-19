package it.unimi.di.sweng.lab04;

import ca.mcgill.cs.stg.solitaire.cards.Card;
import ca.mcgill.cs.stg.solitaire.cards.Rank;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public interface ChainedHandEvaluator {
    HandRank eval(PokerHand pokerHand);
    @NotNull
    default Map<Rank, Integer> getRankIntegerMap(PokerHand pokerHand) {
        Map<Rank,Integer> map = new HashMap<>();

        for(Card card : pokerHand){
            Rank tempRank = card.getRank();
            map.put(tempRank,map.getOrDefault(tempRank,0)+1);
        }
        return map;
    }
}
