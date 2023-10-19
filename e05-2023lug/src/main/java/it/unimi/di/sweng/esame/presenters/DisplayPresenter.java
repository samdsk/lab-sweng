package it.unimi.di.sweng.esame.presenters;

import it.unimi.di.sweng.esame.Observable;
import it.unimi.di.sweng.esame.Observer;
import it.unimi.di.sweng.esame.model.ObservableModel;
import it.unimi.di.sweng.esame.model.Segnalazione;
import it.unimi.di.sweng.esame.model.Tecnico;
import it.unimi.di.sweng.esame.views.OutputView;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class DisplayPresenter implements Observer<List<Segnalazione>> {
	private final ObservableModel model;
	protected final OutputView view;

	public DisplayPresenter(@NotNull ObservableModel model, @NotNull OutputView view) {

		this.model = model;
		this.view = view;

		model.addObsever(this);
	}


	@Override
	public void update(@NotNull Observable<List<Segnalazione>> observable, @NotNull List<Segnalazione> state) {
		resetDisplay();
		print(state);
	}

	private void resetDisplay(){
		for (int i = 0; i < view.size(); i++) {
			view.set(i,"");
		}
	}

	abstract protected void print(@NotNull List<Segnalazione> state);

}
