package it.unimi.di.sweng.esame.presenters;

import it.unimi.di.sweng.esame.Observable;
import it.unimi.di.sweng.esame.model.ObservableModel;
import it.unimi.di.sweng.esame.model.Segnalazione;
import it.unimi.di.sweng.esame.model.Tecnico;
import it.unimi.di.sweng.esame.views.OutputView;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DisplayPresenterOrario extends DisplayPresenter {

	private final OutputView view;
	private final OrderStrategy orderStrategy = new OrarioOrderStrategy();
	private final PrintStrategy printStrategy = new SegnalazioneConData();

	public DisplayPresenterOrario(@NotNull ObservableModel model, @NotNull OutputView view) {
		super(model, view);
		this.view = view;
	}

	protected void print(@NotNull List<Segnalazione> state) {
		orderStrategy.sort(state);

		for (int i = 0; i < view.size() && i < state.size(); i++) {
			view.set(i,printStrategy.format(state.get(i)));
		}
	}

}