package it.unimi.di.sweng.briscola;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class PlayLeastValuable implements Strategy{
	@NotNull final private Strategy next;
	public PlayLeastValuable(@NotNull Strategy next) {
		this.next = next;
	}

	@Override
	public @NotNull Card chooseCard(@NotNull Player me, @NotNull Player other, @NotNull Suit briscola) {
		Iterator<Card> it = me.iterator();

		Card playedCard = it.next();

		while(it.hasNext()){
			Card temp = it.next();
			if(temp.getRank().ordinal() < playedCard.getRank().ordinal())
				playedCard = temp;
		}

		if(playedCard.getRank().points() != 0)
			return next.chooseCard(me,other,briscola);

		return playedCard;
	}
}
