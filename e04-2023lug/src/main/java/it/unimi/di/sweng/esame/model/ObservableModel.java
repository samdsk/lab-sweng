package it.unimi.di.sweng.esame.model;

import it.unimi.di.sweng.esame.Observable;
import it.unimi.di.sweng.esame.Observer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ObservableModel extends Model implements Observable<Postazione[]> {
	private final List<Observer<Postazione[]>> observers = new ArrayList<>();
	public ObservableModel() {
		super();
	}

	@Override
	public void arriva(@NotNull Postazione postazione, int zona) {
		super.arriva(postazione, zona);
		notifyObservers();
	}

	@Override
	public void vaVia(int zona) {
		super.vaVia(zona);
		notifyObservers();
	}

	@Override
	public void segnala(@NotNull Flag flag, int zona) {
		super.segnala(flag, zona);
		notifyObservers();
	}

	@Override
	public void addObserver(@NotNull Observer<Postazione[]> observer) {
		observers.add(observer);
	}

	@Override
	public void notifyObservers() {
		for (Observer<Postazione[]> observer : observers) {
			observer.update(this,getState());
		}
	}

	@Override
	public @NotNull Postazione @NotNull [] getState() {
		return getPostazioni();
	}
}
