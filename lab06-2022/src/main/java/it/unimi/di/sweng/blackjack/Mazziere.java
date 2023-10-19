package it.unimi.di.sweng.blackjack;

import ca.mcgill.cs.stg.solitaire.cards.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Mazziere implements GiocatoreBJ{
	// DONE i vari metodi richiesti per aderire all'interfaccia GiocatoreBJ

	final private MultiMazzo multiMazzo;
	final private List<Card> mano;

	public Mazziere(){
		multiMazzo = new MultiMazzo(6);
		mano = new ArrayList<>();
	}

	public Mazziere(MultiMazzo multiMazzo) {
		this.multiMazzo = multiMazzo;
		mano = new ArrayList<>();
	}

	@Override
	public void carteIniziali() {
		assert !multiMazzo.isEmpty();

		mano.add(multiMazzo.draw());
		mano.add(multiMazzo.draw());
	}

	@Override
	public void gioca() {
		if(getPunti()<17){
			mano.add(multiMazzo.draw());
			gioca();
		}
	}

	@Override
	public Iterator<Card> getCards() {
		return Collections.unmodifiableList(mano).iterator();
	}

	@Override
	public String getName() {
		return "Mazziere";
	}

	public Card daiCarta() {
		assert !multiMazzo.isEmpty();

		return multiMazzo.draw();
	}

	@Override
	public String toString() {
		return asString();
	}


}
