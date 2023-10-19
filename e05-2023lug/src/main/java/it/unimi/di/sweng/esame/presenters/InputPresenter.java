package it.unimi.di.sweng.esame.presenters;

import it.unimi.di.sweng.esame.model.*;
import it.unimi.di.sweng.esame.views.InputView;
import it.unimi.di.sweng.esame.views.SummerReportView;
import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;

public class InputPresenter implements Presenter {
	private final Modello model;
	private final InputView view;

	public InputPresenter(@NotNull Modello model, @NotNull InputView view) {
		this.model = model;
		this.view = view;
		this.view.addHandlers(this);
	}

	@Override
	public void action(@NotNull String cmd, @NotNull String args) {
		try{
			switch (cmd) {
				case "Segnala" -> segnala(args);
				case "Risolvi" -> risolvi(args);
				default -> throw new IllegalArgumentException("Unknown command");
			}
			view.showSuccess();

		}catch (IllegalArgumentException e){
			if(e.getMessage().startsWith(
					"No enum constant it.unimi.di.sweng.esame.model.Tecnico"
			)) view.showError("Tecnico non valido");
			else view.showError(e.getMessage());
		}


	}

	private void segnala(@NotNull String args) {
		String[] data = args.split(";");
		if(data.length!=4)
			throw new IllegalArgumentException("Servono 4 campi separati da punto e virgola");

		String codice = data[0];
		Tecnico tecnico = Tecnico.valueOf(data[1]);

		Appartamento app = new Appartamento(codice,tecnico);

		double lat = Double.parseDouble(data[2]);
		double lon = Double.parseDouble(data[3]);
		LocalTime time = LocalTime.now();

		Segnalazione segnalazione = new Segnalazione(
				app,new Latitudine(lat),
				new Longitudine(lon),
				time
		);

		model.segnala(segnalazione);
	}

	private void risolvi(@NotNull String args) {
		String[] data = args.split(";");
		if(data.length!=2)
			throw new IllegalArgumentException("Servono 2 campi separati da punto e virgola");

		String codice = data[0];
		Tecnico tecnico = Tecnico.valueOf(data[1]);
		Appartamento app = new Appartamento(codice,tecnico);

		model.risolvi(app);
	}
}
