package it.unimi.di.sweng.esame.presenters;

import it.unimi.di.sweng.esame.model.Canzone;
import org.jetbrains.annotations.NotNull;

public class ClassificaPrintStrategy implements PrintStrategy {
	@Override
	public @NotNull String format(@NotNull Canzone canzone) {
		return canzone.formatClassifica();
	}
}
