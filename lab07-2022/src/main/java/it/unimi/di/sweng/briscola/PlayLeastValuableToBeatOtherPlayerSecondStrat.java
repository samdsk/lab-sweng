package it.unimi.di.sweng.briscola;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class PlayLeastValuableToBeatOtherPlayerSecondStrat implements Strategy {
	@NotNull private final Strategy next;
	public PlayLeastValuableToBeatOtherPlayerSecondStrat(Strategy next) {
		this.next = next;
	}

	@Override
	public @NotNull Card chooseCard(@NotNull Player me, @NotNull Player other, @NotNull Suit briscola) {
		Card otherPlayed = other.playedCard();

		Iterator<Card> it = me.iterator();

		Card played = null;

		while(it.hasNext()){
			Card temp = it.next();
			int tempValue = temp.getRank().ordinal();

			if(tempValue > otherPlayed.getRank().ordinal()){
				if(played == null){
					played = temp;
				}else if(tempValue < played.getRank().ordinal()){
					played = temp;
				}
			}
		}

		if(played == null || played.getSuit() == briscola)
			return next.chooseCard(me,other,briscola);

		return played;
	}
}
