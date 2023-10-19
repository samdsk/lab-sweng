package it.unimi.di.sweng.esame.presenters;

import it.unimi.di.sweng.esame.model.Segnalazione;
import org.jetbrains.annotations.NotNull;

public class SegnalzioneConDistanza implements PrintStrategy {
	@Override
	public @NotNull String format(@NotNull Segnalazione segnalazione) {
		return segnalazione.formatDistanza();
	}
}
