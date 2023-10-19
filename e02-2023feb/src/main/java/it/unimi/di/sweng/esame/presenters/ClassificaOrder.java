package it.unimi.di.sweng.esame.presenters;

import it.unimi.di.sweng.esame.model.Canzone;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ClassificaOrder implements OrderStrategy {
	@Override
	public void sort(@NotNull List<Canzone> list) {
		list.sort((a,b)->{
			if(a.totalPoints() == b.totalPoints())
				return a.getCountry().compareTo(b.getCountry());

			return Integer.compare(b.totalPoints(),a.totalPoints());
		});
	}
}
