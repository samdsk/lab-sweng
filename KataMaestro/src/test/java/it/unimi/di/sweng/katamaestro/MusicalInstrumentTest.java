package it.unimi.di.sweng.katamaestro;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MusicalInstrumentTest {
	@Test
	void testTrumpetClass() {
		MusicalInstrument trumpet = new Trumpet();

		assertThat(trumpet.play()).isEqualTo("pepepe");
	}
	@Test
	void testHornClass() {
		MusicalInstrument SUT = new Horn();

		assertThat(SUT.play()).isEqualTo("papapa");
	}

	@Test
	void testWaterGlass() {
		WaterGlass SUT = new WaterGlass();

		assertThat(SUT.tap()).isEqualTo("diding");
	}

	@Test
	void testGermanPercussionIronRod() {
		GermanPercussion SUT = new IronRod();

		assertThat(SUT.spiel()).isEqualTo("tatang");
	}

	@Test
	void testWaterGlassMusicInstrument() {
		MusicalInstrument SUT = new WaterGlassMusicInstrument();

		assertThat(SUT.play()).isEqualTo("diding");
	}

	@Test
	void testGermanPercussionMusicInstrument() {
		MusicalInstrument SUT = new GermanPercussionMusicInstrument(new IronRod());

		assertThat(SUT.play()).isEqualTo("tatang");
	}

	@Test
	void testOrchestra() {
		Orchestra SUT = new Orchestra();
		SUT.add(new Trumpet());

		assertThat(SUT.play()).isEqualTo("pepepe");
	}

	@Test
	void testOrchestraConPiuStrumenti() {
		Orchestra SUT = new Orchestra();
		SUT.add(new Trumpet());
		SUT.add(new Horn());
		SUT.add(new WaterGlassMusicInstrument());

		assertThat(SUT.play()).isEqualTo("pepepe papapa diding");
	}

	@Test
	void testHighVolumeMusicalInstrument() {
		MusicalInstrument SUT = new HighVolumeMusicalInstrument(new Trumpet());

		assertThat(SUT.play()).isEqualTo("PEPEPE");
	}

	@Test
	void testSlowTempoMusicalInstrument() {
		MusicalInstrument SUT = new SlowTempoMusicalInstrument(new Trumpet());

		assertThat(SUT.play()).isEqualTo("peepeepee");
	}

	@Test
	void testSlowInstrumentFactoryTrumpet(){
		InstrumentFactory<MusicalInstrument> SUT = new SlowInstrumentFactory();

		assertThat(SUT.createTrumpet().play()).isEqualTo("peepeepee");
		assertThat(SUT.createHorn().play()).isEqualTo("paapaapaa");
		assertThat(SUT.createIronRod().play()).isEqualTo("taataang");
		assertThat(SUT.createWaterGlass().play()).isEqualTo("diidiing");
	}
	@Test
	void testPlainInstrumentFactory(){
		InstrumentFactory<MusicalInstrument> SUT = new PlainInstrumentFactory();

		Orchestra orchestra = new Orchestra();
		orchestra.add(SUT.createTrumpet());
		orchestra.add(SUT.createHorn());
		orchestra.add(SUT.createWaterGlass());
		orchestra.add(SUT.createIronRod());

		assertThat(orchestra.play()).isEqualTo("pepepe papapa diding tatang");
	}

	@Test
	void testHighVolumeInstrumentFactory() {
		InstrumentFactory<MusicalInstrument> SUT = new HighVolumeInstrumentFactory();

		Orchestra orchestra = new Orchestra();
		orchestra.add(SUT.createTrumpet());
		orchestra.add(SUT.createHorn());
		orchestra.add(SUT.createWaterGlass());
		orchestra.add(SUT.createIronRod());

		assertThat(orchestra.play()).isEqualTo("PEPEPE PAPAPA DIDING TATANG");
	}

	@Test
	void testObserver() {
		ObservableMusicInstrument SUT = new ObservableMusicInstrument(new Trumpet());

		Vicino vicino = new Vicino();

		SUT.register(vicino);
		SUT.play();

		assertThat(vicino.counts()).isEqualTo("Trumpet:1");
	}

	@Test
	void testObserverManyInstruments() {
		ObservableMusicInstrument ironRod = new ObservableMusicInstrument(new GermanPercussionMusicInstrument(new IronRod()));
		ObservableMusicInstrument waterGlass = new ObservableMusicInstrument(new WaterGlassMusicInstrument());
		ObservableMusicInstrument trumpet = new ObservableMusicInstrument(new Trumpet());
		ObservableMusicInstrument horn = new ObservableMusicInstrument(new Horn());

		Vicino vicino = new Vicino();

		ironRod.register(vicino);
		waterGlass.register(vicino);
		trumpet.register(vicino);
		horn.register(vicino);

		ironRod.play();
		waterGlass.play();
		waterGlass.play();
		horn.play();
		trumpet.play();
		horn.play();
		trumpet.play();
		waterGlass.play();
		trumpet.play();
		waterGlass.play();

		assertThat(vicino.counts()).isEqualTo("GermanPercussionMusicInstrument:1\nHorn:2\nTrumpet:3\nWaterGlassMusicInstrument:4");
	}

	@Test
	void testObservableInstrumentFactory() {
		InstrumentFactory<ObservableMusicInstrument> factory = new ObservableInstrumentFactory();
		Observer<String> vicino = mock(Vicino.class);

		ObservableMusicInstrument trumpet = factory.createTrumpet();

		trumpet.register(vicino);
		trumpet.play();

		verify(vicino,times(1)).update("Trumpet");



	}
}
