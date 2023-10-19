package it.unimi.di.sweng.briscola;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;

public class PlayLeastValuableToBeatOtherPlayer implements Strategy {
	@NotNull final private Strategy next;
	public PlayLeastValuableToBeatOtherPlayer(@NotNull Strategy next) {
		this.next = next;
	}

	@Override
	public @NotNull Card chooseCard(@NotNull Player me, @NotNull Player other, @NotNull Suit briscola) {
		Iterator<Card> oit = other.iterator();
		Card otherPlayerMax = oit.next();

		while (oit.hasNext()){
			Card temp = oit.next();
			if(temp.getSuit() == briscola) return next.chooseCard(me,other,briscola);

			if(temp.getRank().ordinal() > otherPlayerMax.getRank().ordinal())
				otherPlayerMax = temp;
		}

		Iterator<Card> mit = me.iterator();

		Card playedCard = null;

		while(mit.hasNext()){
			Card temp = mit.next();
			int tempValue = temp.getRank().ordinal();

			if(tempValue > otherPlayerMax.getRank().ordinal()){
				if(playedCard == null){
					playedCard = temp;
				}else if(tempValue < playedCard.getRank().ordinal()){
					playedCard = temp;
				}
			}

		}

		if(playedCard == null || playedCard.getSuit() == briscola)
			return next.chooseCard(me,other,briscola);

		return playedCard;
	}
}
