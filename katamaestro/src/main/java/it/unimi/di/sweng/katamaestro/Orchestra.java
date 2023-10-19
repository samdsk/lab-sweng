package it.unimi.di.sweng.katamaestro;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Orchestra implements MusicalInstrument {
	final private List<MusicalInstrument> strumenti = new ArrayList<>();

	public void add(@NotNull MusicalInstrument strumento){
		strumenti.add(strumento);
	}
	@Override
	public @NotNull String play() {
		StringBuilder sb = new StringBuilder();

		for(MusicalInstrument i : strumenti){
			sb.append(i.play()).append(' ');
		}

		if(!sb.isEmpty())
			sb.deleteCharAt(sb.length() - 1);

		return sb.toString();
	}
}
