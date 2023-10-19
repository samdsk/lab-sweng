package it.unimi.di.sweng.esame.presenters;

import it.unimi.di.sweng.esame.model.Segnalazione;
import org.jetbrains.annotations.NotNull;

public interface PrintStrategy {
	@NotNull String format(@NotNull Segnalazione segnalazione);
}
