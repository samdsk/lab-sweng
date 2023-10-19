package it.unimi.di.sweng.esame.presenters;

import it.unimi.di.sweng.esame.Main;
import it.unimi.di.sweng.esame.Observable;
import it.unimi.di.sweng.esame.Observer;
import it.unimi.di.sweng.esame.model.Canzone;
import it.unimi.di.sweng.esame.model.Model;
import it.unimi.di.sweng.esame.views.DisplayView;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DisplayPresenter implements Observer<List<Canzone>> {
	private final Observable<List<Canzone>> model;
	private final DisplayView view;
	private int offset = 0;
	private int limit = Main.PANEL_SIZE;
	private @NotNull PrintStrategy printStrategy = new ClassificaPrintStrategy();
	private @NotNull OrderStrategy orderStrategy = new ClassificaOrder();

	public DisplayPresenter(@NotNull Observable<List<Canzone>> model,@NotNull DisplayView view) {
		this.model = model;
		this.view = view;
		model.addObserver(this);
	}

	public DisplayPresenter(@NotNull Observable<List<Canzone>> model, @NotNull DisplayView view, int offset) {
		this(model,view);
		this.offset = offset;
	}

	public DisplayPresenter(@NotNull Observable<List<Canzone>> model, @NotNull DisplayView view, int offset, int limit) {
		this(model,view,offset);
		this.limit = limit;
	}

	@Override
	public void update(@NotNull Observable<List<Canzone>> observable, @NotNull List<Canzone> state) {
		orderStrategy.sort(state);

		for (int i = 0; i < limit && i+offset < state.size(); i++) {
			view.set(i,printStrategy.format(state.get(i+offset)));
		}
	}

	public @NotNull DisplayPresenter setPrintStrategy(@NotNull PrintStrategy printStrategy) {
		this.printStrategy = printStrategy;
		return this;
	}

	public DisplayPresenter setOrderStrategy(OrderStrategy orderStrategy) {
		this.orderStrategy = orderStrategy;
		return this;
	}
}
