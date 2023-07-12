package it.unimi.di.sweng.katamaestro;

import org.jetbrains.annotations.NotNull;

public class GermanPercussionMusicInstrument implements MusicalInstrument {
	GermanPercussion germanPercussion;
	public GermanPercussionMusicInstrument(GermanPercussion germanPercussion) {
		this.germanPercussion = germanPercussion;
	}

	@Override
	public @NotNull String play() {
		return germanPercussion.spiel();
	}
}
