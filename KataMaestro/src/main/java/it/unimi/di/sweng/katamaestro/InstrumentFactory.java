package it.unimi.di.sweng.katamaestro;

public interface InstrumentFactory<T> {
	T createTrumpet();

	T createHorn();

	T createIronRod();

	T createWaterGlass();
}
