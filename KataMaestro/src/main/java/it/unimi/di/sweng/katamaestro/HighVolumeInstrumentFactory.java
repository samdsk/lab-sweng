package it.unimi.di.sweng.katamaestro;

public class HighVolumeInstrumentFactory implements InstrumentFactory<MusicalInstrument> {
	@Override
	public MusicalInstrument createTrumpet() {
		return new HighVolumeMusicalInstrument(new Trumpet());
	}

	@Override
	public MusicalInstrument createHorn() {
		return new HighVolumeMusicalInstrument(new Horn());
	}

	@Override
	public MusicalInstrument createIronRod() {
		return new HighVolumeMusicalInstrument(new GermanPercussionMusicInstrument(new IronRod()));
	}

	@Override
	public MusicalInstrument createWaterGlass() {
		return new HighVolumeMusicalInstrument(new WaterGlassMusicInstrument());
	}

}
