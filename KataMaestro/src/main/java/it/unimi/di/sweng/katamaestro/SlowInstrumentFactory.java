package it.unimi.di.sweng.katamaestro;

public class SlowInstrumentFactory implements InstrumentFactory<MusicalInstrument> {

	@Override
	public MusicalInstrument createTrumpet() {
		return new SlowTempoMusicalInstrument(new Trumpet());
	}

	@Override
	public MusicalInstrument createHorn() {
		return new SlowTempoMusicalInstrument(new Horn());
	}

	@Override
	public MusicalInstrument createIronRod() {
		return new SlowTempoMusicalInstrument(new GermanPercussionMusicInstrument(new IronRod()));
	}

	@Override
	public MusicalInstrument createWaterGlass() {
		return new SlowTempoMusicalInstrument(new WaterGlassMusicInstrument());
	}
}
