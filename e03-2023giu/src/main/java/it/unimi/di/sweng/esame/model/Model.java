package it.unimi.di.sweng.esame.model;

import it.unimi.di.sweng.esame.Observable;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model{
	private final Map<Posizione,Segnalazione> segnalazioni = new HashMap<>();
	private int segnalazioniAperti = 0;

	public void addSegnalazione(@NotNull Segnalazione segnalazione) {
		if(contains(segnalazione.getPosizione()))
			throw new IllegalArgumentException("altra segnalazione già presente per questo tratto");

		segnalazioni.put(
				segnalazione.getPosizione(),
				segnalazione
		);

		segnalazioniAperti++;
	}

	public boolean contains(@NotNull Posizione posizione) {
		return segnalazioni.containsKey(posizione);
	}

	public int segnalazioniAperti() {
		return segnalazioniAperti;
	}

	public void risolvi(@NotNull Posizione posizione) {
		if(contains(posizione)){
			segnalazioni.put(posizione,
					segnalazioni.get(posizione).segnalaComeRisolto());

			segnalazioniAperti--;
		}
	}

	public List<Segnalazione> getSegnalazioni(){
		return new ArrayList<>(segnalazioni.values());
	}
}
