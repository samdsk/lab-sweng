package it.unimi.di.sweng.katamaestro;

import org.jetbrains.annotations.NotNull;

public abstract class MusicalInstrumentDecorator implements MusicalInstrument{
	protected final @NotNull MusicalInstrument instrument;

	protected MusicalInstrumentDecorator(@NotNull MusicalInstrument instrument){
		this.instrument = instrument;
	}

	@Override
	public @NotNull String play(){
		return decorate(instrument.play());
	}

	protected String decorate(String original){
		return original;
	}
}
