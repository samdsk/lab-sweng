package it.unimi.di.sweng.esame.model;

import it.unimi.di.sweng.esame.Observable;
import it.unimi.di.sweng.esame.Observer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ObservableModel extends Model implements Observable<List<Partenza>> {
	private final List<Observer<List<Partenza>>> observers = new ArrayList<>();

	public ObservableModel() {
		super();
	}

	@Override
	public void addObserver(@NotNull Observer<@NotNull List<Partenza>> observer) {
		observers.add(observer);
	}

	@Override
	public void removeObserver(@NotNull Observer<@NotNull List<Partenza>> observer) {
		observers.remove(observer);
	}
	@Override
	public void removeTrain(@NotNull Treno treno) {
		super.removeTrain(treno);
		notifyObservers();
	}
	@Override
	public @NotNull List<Partenza> getState() {
		return new ArrayList<>(partenze.values());
	}

	@Override
	public void notifyObservers() {
		for (Observer<List<Partenza>> o : observers) {
			o.update(this,getState());
		}
	}

	@Override
	public void setDelay(@NotNull Treno treno, int delay) {
		super.setDelay(treno, delay);
		notifyObservers();
	}
}
