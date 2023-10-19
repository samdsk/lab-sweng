package it.unimi.di.sweng.katamaestro;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ObservableMusicInstrument extends MusicalInstrumentDecorator{

	private final List<Observer<String>> observerList = new ArrayList<>();
	protected ObservableMusicInstrument(@NotNull MusicalInstrument instrument) {
		super(instrument);
	}


	public void register(Observer<String> observer) {
		observerList.add(observer);
	}

	public void notifyAllObservers(){
		for(Observer<String> b : observerList){
			b.update(instrument.getClass().getSimpleName());
		}
	}

	@Override
	protected String decorate(String original){
		notifyAllObservers();
		return original;
	}

}
