package it.unimi.di.sweng.esame.presenters;

import it.unimi.di.sweng.esame.Observable;
import it.unimi.di.sweng.esame.model.ObservableModel;
import it.unimi.di.sweng.esame.model.Segnalazione;
import it.unimi.di.sweng.esame.model.Tecnico;
import it.unimi.di.sweng.esame.views.OutputView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DisplayPresenterFabbri extends DisplayPresenter {
	private final OrderStrategy orderStrategy = new DistanceOrderStrategy();
	private final PrintStrategy printStrategy = new SegnalzioneConDistanza();

	public DisplayPresenterFabbri(ObservableModel model, OutputView view) {
		super(model, view);
	}
	protected void print(@NotNull List<Segnalazione> state) {
		state = rimuoviTecniciNonFabbri(state);
		orderStrategy.sort(state);

		for (int i = 0; i < view.size() && i < state.size(); i++) {
			Segnalazione segnalazione = state.get(i);

			if(segnalazione.app().tecnico() == Tecnico.FABBRO)
				view.set(i,printStrategy.format(state.get(i)));
		}
	}

	private List<Segnalazione> rimuoviTecniciNonFabbri(@NotNull List<Segnalazione> list){
		List<Segnalazione> ans = new ArrayList<>();
		for (Segnalazione segnalazione : list) {
			if(segnalazione.app().tecnico()==Tecnico.FABBRO)
				ans.add(segnalazione);
		}

		return ans;
	}

}
