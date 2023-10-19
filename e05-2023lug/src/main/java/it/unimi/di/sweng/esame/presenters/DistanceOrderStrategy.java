package it.unimi.di.sweng.esame.presenters;

import it.unimi.di.sweng.esame.model.Segnalazione;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;

public class DistanceOrderStrategy implements OrderStrategy {
	@Override
	public void sort(@NotNull List<Segnalazione> list) {
		list.sort(Comparator.comparing(Segnalazione::distanza));
	}
}
