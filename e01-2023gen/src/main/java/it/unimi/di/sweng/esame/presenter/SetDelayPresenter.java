package it.unimi.di.sweng.esame.presenter;

import it.unimi.di.sweng.esame.model.Model;
import it.unimi.di.sweng.esame.model.Treno;
import it.unimi.di.sweng.esame.view.SetDelayView;
import org.jetbrains.annotations.NotNull;

public class SetDelayPresenter implements InputPresenter {
	private final Model model;
	private final SetDelayView view;

	public SetDelayPresenter(@NotNull Model model, @NotNull SetDelayView view) {
		this.model = model;
		this.view = view;

		this.view.addHandlers(this);

	}

	@Override
	public void action(String treno, String delayText) {
		if(treno.length()==0) return;
		try{
			int delay = Integer.parseInt(delayText);
			model.setDelay(new Treno(treno), delay);
		}catch (NumberFormatException ignored){};
	}
}
