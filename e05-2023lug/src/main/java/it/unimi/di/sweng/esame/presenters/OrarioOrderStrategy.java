package it.unimi.di.sweng.esame.presenters;

import it.unimi.di.sweng.esame.model.Segnalazione;
import jdk.jshell.Snippet;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;

public class OrarioOrderStrategy implements OrderStrategy {
	private final Comparator<Segnalazione> comparator = Comparator.comparing(Segnalazione::orario);
	@Override
	public void sort(@NotNull List<Segnalazione> list) {
		list.sort(comparator);
	}
}
