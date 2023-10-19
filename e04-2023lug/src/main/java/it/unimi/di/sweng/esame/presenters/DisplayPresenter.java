package it.unimi.di.sweng.esame.presenters;

import it.unimi.di.sweng.esame.Main;
import it.unimi.di.sweng.esame.Observable;
import it.unimi.di.sweng.esame.Observer;
import it.unimi.di.sweng.esame.model.ObservableModel;
import it.unimi.di.sweng.esame.model.Postazione;
import it.unimi.di.sweng.esame.views.DisplayView;
import org.jetbrains.annotations.NotNull;

public class DisplayPresenter implements Observer<Postazione[]> {
	private final ObservableModel model;
	private final DisplayView view;
	private PrintStrategy printStrategy;

	public DisplayPresenter(@NotNull ObservableModel model, @NotNull DisplayView view) {

		this.model = model;
		this.view = view;

		this.model.addObserver(this);
	}

	@Override
	public void update(@NotNull Observable<Postazione[]> observable, Postazione @NotNull [] state) {
		String[] strings = printStrategy.print(state);

		for (int i = 0; i < state.length && i < Main.NUMPOSTAZIONI; i++) {
			view.set(i,strings[i]);
		}
	}

	public DisplayPresenter setPrintSTrategy(PrintStrategy printStrategy) {

		this.printStrategy = printStrategy;
		return this;
	}
}
