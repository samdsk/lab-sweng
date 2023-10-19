package it.unimi.di.sweng.esame.presenters;

import it.unimi.di.sweng.esame.model.Postazione;
import org.jetbrains.annotations.NotNull;

public interface PrintStrategy {
	@NotNull String[] print(Postazione[] postazioni);
}
