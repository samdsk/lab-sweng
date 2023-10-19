package it.unimi.di.sweng.esame.presenters;

import it.unimi.di.sweng.esame.model.Canzone;
import org.jetbrains.annotations.NotNull;

public interface PrintStrategy {
	@NotNull String format(@NotNull Canzone canzone);
}
