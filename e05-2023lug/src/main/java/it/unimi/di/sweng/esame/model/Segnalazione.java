package it.unimi.di.sweng.esame.model;

import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;
import java.util.Locale;

public record Segnalazione(@NotNull Appartamento app,
                           @NotNull Latitudine lat,
                           @NotNull Longitudine lon,
                           @NotNull LocalTime orario) {
	public @NotNull String formatSegnalazione() {
		return String.format("Richiesto un %s all'appartamento %s alle %s",
				app.tecnico(),app.codice(),orario);
	}

	public @NotNull String formatDistanza() {
		return String.format(Locale.ITALY,"Richiesto un %s all'appartamento %s a %.2fkm",
				app.tecnico(),app.codice(),distanza());
	}

	public double distanza() {
		double AMMINISTRATORE_LAT = 45.5;
		double AMMINISTRATORE_LON = 9.2;
		return haversine(lat.lat(),lon.lon(),AMMINISTRATORE_LAT,AMMINISTRATORE_LON);
	}

	// Return the approximate distance between two points on Earth
	// (given as decimal degrees of latitude and longitude), in km
	double haversine(double lat1, double lon1, double lat2, double lon2) {
		lat1 = Math.toRadians(lat1);
		lat2 = Math.toRadians(lat2);
		double dLat = lat2 - lat1;
		double dLon = Math.toRadians(lon2 - lon1);

		double a = Math.pow(Math.sin(dLat / 2), 2) + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
		double c = 2 * Math.asin(Math.sqrt(a));
		return 6371 * c; // mean Earth radius in km
	}
}
