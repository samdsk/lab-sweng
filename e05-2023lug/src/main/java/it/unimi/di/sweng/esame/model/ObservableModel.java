package it.unimi.di.sweng.esame.model;

import it.unimi.di.sweng.esame.Observable;
import it.unimi.di.sweng.esame.Observer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ObservableModel extends Modello implements Observable<List<Segnalazione>> {
	public ObservableModel() {
		super();
	}

	private final List<Observer<List<Segnalazione>>> observers = new ArrayList<>();
	@Override
	public void addObsever(@NotNull Observer<List<Segnalazione>> observer) {
		observers.add(observer);
	}

	@Override
	public void notifyObservers() {
		for (Observer<List<Segnalazione>> observer : observers) {
			observer.update(this,getState());
		}
	}

	@Override
	public void segnala(@NotNull Segnalazione segnalazione) {
		super.segnala(segnalazione);
		notifyObservers();
	}

	@Override
	public void risolvi(@NotNull Appartamento app) {
		super.risolvi(app);
		notifyObservers();
	}

	@Override
	public @NotNull List<Segnalazione> getState() {
		return super.getSegnalzioni();
	}
}
