package it.unimi.di.sweng.esame.presenters;

import it.unimi.di.sweng.esame.model.Model;
import it.unimi.di.sweng.esame.model.Posizione;
import it.unimi.di.sweng.esame.model.Segnalazione;
import it.unimi.di.sweng.esame.views.CentralStationView;
import org.jetbrains.annotations.NotNull;

public class InputPresenter implements Presenter {
	private final Model model;
	private final CentralStationView view;

	public InputPresenter(@NotNull Model model, @NotNull CentralStationView view) {

		this.model = model;
		this.view = view;

		this.view.addHandlers(this);
	}

	@Override
	public void action(@NotNull String cmd, @NotNull String input) {
		String[] data = input.split(",");

		try{
			if(data.length < 2)
				throw new IllegalArgumentException("campi mancanti");

			int km = Integer.parseInt(data[1]);
			String tratto = data[0].trim();

			Posizione posizione = new Posizione(tratto,km);

			if (cmd.equals("Segnala")) {
				if (data.length != 3)
					throw new IllegalArgumentException("campo descrizione mancante");

				if(model.segnalazioniAperti()>7)
					throw new IllegalArgumentException("troppe segnalazioni aperte");

				String descrizione = data[2].trim();

				model.addSegnalazione(new Segnalazione(posizione,descrizione));
			}

			if(cmd.equals("Risolto")){
				if(!model.contains(posizione))
					throw new IllegalArgumentException("segnalazione non presente per questo tratto");

				model.risolvi(posizione);
			}

			view.showSuccess();

		} catch (NumberFormatException e){
			view.showError("campo km non numerico");
		} catch (IllegalArgumentException e){
			view.showError(e.getMessage());
		}

	}
}
