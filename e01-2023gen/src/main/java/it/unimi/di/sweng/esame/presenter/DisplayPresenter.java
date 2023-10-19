package it.unimi.di.sweng.esame.presenter;

import it.unimi.di.sweng.esame.Main;
import it.unimi.di.sweng.esame.Observable;
import it.unimi.di.sweng.esame.Observer;
import it.unimi.di.sweng.esame.model.ObservableModel;
import it.unimi.di.sweng.esame.model.Partenza;
import it.unimi.di.sweng.esame.model.Treno;
import it.unimi.di.sweng.esame.view.DepartureView;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;

public class DisplayPresenter implements Observer<List<Partenza>>, InputPresenter {
	private final @NotNull  ObservableModel model;
	private final @NotNull DepartureView view;
	private final int offset;

	private final Comparator<Partenza> comparator = Partenza::compareTo;

	public DisplayPresenter(@NotNull ObservableModel model, @NotNull DepartureView view, int offset) {

		this.model = model;
		this.view = view;
		this.offset = offset;

		model.addObserver(this);
		view.addHandlers(this);
	}

	@Override
	public void update(@NotNull Observable<List<Partenza>> observable, @NotNull List<Partenza> state) {
		state.sort(comparator);
		int i = 0;
		for (; i < Main.MAX_ROW_ITEMS_IN_VIEW && i+offset < state.size(); i++) {
			view.set(i,state.get(i+offset).viewFormat());
		}

		for (int j = i; j < Main.MAX_ROW_ITEMS_IN_VIEW; j++) {
			view.set(i,"");
		}
	}

	@Override
	public void action(String text1, String text2) {
		String nomeTreno = text1.split("-")[0].trim();
		Treno treno = new Treno(nomeTreno);

		model.removeTrain(treno);
	}
}
