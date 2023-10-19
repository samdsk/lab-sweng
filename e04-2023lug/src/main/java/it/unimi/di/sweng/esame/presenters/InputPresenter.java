package it.unimi.di.sweng.esame.presenters;

import it.unimi.di.sweng.esame.model.Bagnino;
import it.unimi.di.sweng.esame.model.ConcretePostazione;
import it.unimi.di.sweng.esame.model.Flag;
import it.unimi.di.sweng.esame.model.Model;
import it.unimi.di.sweng.esame.views.PostazioneView;
import org.jetbrains.annotations.NotNull;

public class InputPresenter implements Presenter {
	private final Model model;
	private final PostazioneView view;
	private final int ZONA;

	public InputPresenter(@NotNull Model model, @NotNull PostazioneView view, int zona) {
		this.model = model;
		this.view = view;
		this.ZONA = zona;

		this.view.addHandlers(this);
	}

	@Override
	public void action(@NotNull String cmd, @NotNull String input) {
		try{
			if(cmd.equals("Arriva")){
				model.arriva(new ConcretePostazione(new Bagnino(input), ZONA, Flag.NONE), ZONA);
				view.setBagnino(input);
			}

			if(cmd.equals("Va via")){
				model.vaVia(ZONA);
				view.setBagnino("postazione non presidiata");
			}

			if(cmd.equals("Segnala")){
				segnala(input);
			}
			view.showSuccess();
		}catch (IllegalArgumentException e){
			if(e.getMessage().startsWith("No enum"))
				view.showError("Bandiera non valida");
			else view.showError(e.getMessage());
		}
	}

	private void segnala(@NotNull String input) {
		String[] data = input.split(",");
		if(data.length <2)
			throw new IllegalArgumentException("Indicare colore bandiera");

		String flag = data[1];
		model.segnala(Flag.valueOf(flag),ZONA);
	}
}
