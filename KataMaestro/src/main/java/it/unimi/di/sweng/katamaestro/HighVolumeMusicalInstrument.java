package it.unimi.di.sweng.katamaestro;

import org.jetbrains.annotations.NotNull;

public class HighVolumeMusicalInstrument extends MusicalInstrumentDecorator{

	public HighVolumeMusicalInstrument(@NotNull MusicalInstrument instrument) {
		super(instrument);
	}

	@Override
	public String decorate(String original){
		return original.toUpperCase();
	}
}
