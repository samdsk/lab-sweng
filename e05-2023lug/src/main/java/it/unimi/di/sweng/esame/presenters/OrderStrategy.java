package it.unimi.di.sweng.esame.presenters;

import it.unimi.di.sweng.esame.model.Segnalazione;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface OrderStrategy {
	void sort(@NotNull List<Segnalazione> list);
}
