package it.unimi.di.sweng.esame.presenters;

import it.unimi.di.sweng.esame.Main;
import it.unimi.di.sweng.esame.Observable;
import it.unimi.di.sweng.esame.model.ObservableModel;
import it.unimi.di.sweng.esame.model.Segnalazione;
import it.unimi.di.sweng.esame.views.DisplayView;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;

public class SegnalazioniRisolte extends DisplayPresenter {
	private final ObservableModel model;
	private final DisplayView view;

	public SegnalazioniRisolte(@NotNull ObservableModel model, @NotNull DisplayView view) {
		super();
		this.model = model;
		this.view = view;

		model.addObserver(this);
	}

	@Override
	public void update(@NotNull Observable<List<Segnalazione>> observable, @NotNull List<Segnalazione> state) {
		Iterator<Segnalazione> it = state.iterator();
		int counter = 0;
		while(it.hasNext() && counter< Main.PANEL_SIZE){
			Segnalazione s = it.next();
			if(s.isRisolto()){
				view.set(counter++,s.formatDisplay());
			}
		}

		for (int i = counter; i < Main.PANEL_SIZE; i++) {
			view.set(i,"");
		}
	}
}
