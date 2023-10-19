package it.unimi.di.sweng.esame.model;

public record Longitudine(double lon) {
	public Longitudine {
		double temp = Math.abs(lon);
		if(temp<0 || temp>180)
			throw new IllegalArgumentException(
					"Longitudine non valida"
			);
	}
}
