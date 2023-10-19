package it.unimi.di.sweng.briscola;

import org.jetbrains.annotations.NotNull;

public class PlayFirstCard implements Strategy{

	@Override
	public @NotNull Card chooseCard(@NotNull Player me, @NotNull Player other, @NotNull Suit briscola) {
		assert me.iterator().hasNext() : "Il giocatore "+ me.getName() +" non ha carte!";
		return me.iterator().next();
	}
}
