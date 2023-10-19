package it.unimi.di.sweng.katamaestro;

import org.jetbrains.annotations.NotNull;

public class WaterGlassMusicInstrument extends WaterGlass implements MusicalInstrument {
	
	@Override
	public @NotNull String play() {
		return tap();
	}

}
