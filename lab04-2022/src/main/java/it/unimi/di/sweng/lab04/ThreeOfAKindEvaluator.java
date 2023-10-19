package it.unimi.di.sweng.lab04;

import ca.mcgill.cs.stg.solitaire.cards.Card;
import ca.mcgill.cs.stg.solitaire.cards.Rank;

import java.util.HashMap;
import java.util.Map;

public class ThreeOfAKindEvaluator implements ChainedHandEvaluator {
    ChainedHandEvaluator next;
    public ThreeOfAKindEvaluator(ChainedHandEvaluator next) {
        this.next = next;
    }

    @Override
    public HandRank eval(PokerHand pokerHand) {
        Map<Rank, Integer> map = getRankIntegerMap(pokerHand);

        if(map.values().contains(3)) return HandRank.THREE_OF_A_KIND;
        if(next != null) return next.eval(pokerHand);

        return null;
    }
}
