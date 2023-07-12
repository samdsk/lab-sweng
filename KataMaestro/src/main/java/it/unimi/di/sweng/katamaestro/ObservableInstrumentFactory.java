package it.unimi.di.sweng.katamaestro;

public class ObservableInstrumentFactory implements InstrumentFactory<ObservableMusicInstrument>{
	public ObservableMusicInstrument createTrumpet() {
		return new ObservableMusicInstrument(new Trumpet());
	}

	public ObservableMusicInstrument createHorn() {
		return new ObservableMusicInstrument(new Horn());
	}

	public ObservableMusicInstrument createIronRod() {
		return new ObservableMusicInstrument(
				new GermanPercussionMusicInstrument(new IronRod()));
	}

	public ObservableMusicInstrument createWaterGlass() {
		return new ObservableMusicInstrument(
				new WaterGlassMusicInstrument());
	}
}
