package it.unimi.di.sweng.briscola;

import org.jetbrains.annotations.NotNull;

public class PlaySemeBriscola implements Strategy {
	@NotNull
	final private Strategy next;
	public PlaySemeBriscola(Strategy next) {
		this.next = next;
	}

	@Override
	public @NotNull Card chooseCard(@NotNull Player me, @NotNull Player other, @NotNull Suit briscola) {

		for(Card card : me){
			if(card.getSuit() == briscola) return card;
		}

		return next.chooseCard(me,other,briscola);
	}
}
