package it.unimi.di.sweng.esame.model;

import it.unimi.di.sweng.esame.Observable;
import it.unimi.di.sweng.esame.Observer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ObservableModel extends Model implements Observable<List<Segnalazione>> {
	private final List<Observer<List<Segnalazione>>> observers = new ArrayList<>();
	public ObservableModel() {
		super();
	}

	@Override
	public void addSegnalazione(@NotNull Segnalazione segnalazione) {
		super.addSegnalazione(segnalazione);
		notifyObservers();
	}

	@Override
	public void addObserver(@NotNull Observer<List<Segnalazione>> observer) {
		observers.add(observer);
	}

	@Override
	public void notifyObservers() {
		for (Observer<List<Segnalazione>> o : observers) {
			o.update(this,getState());
		}
	}

	@Override
	public @NotNull List<Segnalazione> getState() {
		return super.getSegnalazioni();
	}

	@Override
	public void risolvi(@NotNull Posizione posizione) {
		super.risolvi(posizione);
		notifyObservers();
	}
}
