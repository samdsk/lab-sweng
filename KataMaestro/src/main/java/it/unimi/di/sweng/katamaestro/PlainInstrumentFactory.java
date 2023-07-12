package it.unimi.di.sweng.katamaestro;

public class PlainInstrumentFactory implements InstrumentFactory<MusicalInstrument> {
	@Override
	public MusicalInstrument createTrumpet() {
		return new Trumpet();
	}

	@Override
	public MusicalInstrument createHorn() {
		return new Horn();
	}

	@Override
	public MusicalInstrument createIronRod() {
		return new GermanPercussionMusicInstrument(new IronRod());
	}

	@Override
	public MusicalInstrument createWaterGlass() {
		return new WaterGlassMusicInstrument();
	}
}
