package it.unimi.di.sweng.esame.presenters;

import it.unimi.di.sweng.esame.model.Segnalazione;
import org.jetbrains.annotations.NotNull;

public class SegnalazioneConData implements PrintStrategy {
	@Override
	public @NotNull String format(Segnalazione segnalazione) {
		return segnalazione.formatSegnalazione();
	}
}
