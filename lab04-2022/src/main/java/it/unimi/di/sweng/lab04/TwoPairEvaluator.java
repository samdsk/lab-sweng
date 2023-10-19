package it.unimi.di.sweng.lab04;

import ca.mcgill.cs.stg.solitaire.cards.Card;
import ca.mcgill.cs.stg.solitaire.cards.Rank;

import java.util.HashMap;
import java.util.Map;

public class TwoPairEvaluator implements ChainedHandEvaluator {
    ChainedHandEvaluator next;

    public TwoPairEvaluator(ChainedHandEvaluator next) {
        this.next = next;
    }

    @Override
    public HandRank eval(PokerHand pokerHand) {
        Map<Rank, Integer> map = getRankIntegerMap(pokerHand);

        int count = 0;
        for( Integer i : map.values()){
            if(i.intValue() == 2) count++;
        }

        if(count == 2) return HandRank.TWO_PAIR;
        if(next != null) return next.eval(pokerHand);
        return null;
    }
}
