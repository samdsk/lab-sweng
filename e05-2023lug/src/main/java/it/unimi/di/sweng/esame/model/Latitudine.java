package it.unimi.di.sweng.esame.model;

public record Latitudine(double lat) {
	public Latitudine {
		double temp = Math.abs(lat);
		if(temp<0 || temp>90)
			throw new IllegalArgumentException(
					"Latitudine non valida"
			);
	}
}
