package it.unimi.di.sweng.lab04;

import ca.mcgill.cs.stg.solitaire.cards.Card;
import ca.mcgill.cs.stg.solitaire.cards.Rank;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class FourOfAKindEvaluator implements ChainedHandEvaluator {
    final ChainedHandEvaluator next;
    public FourOfAKindEvaluator(ChainedHandEvaluator next) {
        this.next = next;
    }

    @Override
    public HandRank eval(PokerHand pokerHand) {
        Map<Rank, Integer> map = getRankIntegerMap(pokerHand);

        if(map.values().contains(4))
            return HandRank.FOUR_OF_A_KIND;
        if(next != null) return next.eval(pokerHand);

        return null;
    }

}
