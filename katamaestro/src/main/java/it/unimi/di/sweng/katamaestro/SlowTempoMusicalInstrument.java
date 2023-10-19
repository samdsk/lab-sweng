package it.unimi.di.sweng.katamaestro;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SlowTempoMusicalInstrument extends MusicalInstrumentDecorator{
	public SlowTempoMusicalInstrument(@NotNull MusicalInstrument instrument){
		super(instrument);
	}

	@Override
	public String decorate(String original){
		StringBuilder sb = new StringBuilder();

		List<Character> vowels = List.of('A','E','I','U','O','a','e','i','o','u');

		for(char c : original.toCharArray()){
			sb.append(c);

			if(vowels.contains(c)){
				sb.append(c);
			}
		}

		return sb.toString();
	}
}
