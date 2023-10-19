package it.unimi.di.sweng.esame.presenters;

import it.unimi.di.sweng.esame.model.Canzone;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PodioOrderStrategy implements OrderStrategy {
	@Override
	public void sort(@NotNull List<Canzone> list) {
		list.sort((a,b)->{
			if(a.totalPoints() == b.totalPoints())
				if(a.getFirstCount() == b.getFirstCount())
					return a.getCountry().compareTo(b.getCountry());
				else return Integer.compare(b.getFirstCount(),a.getFirstCount());

			return Integer.compare(b.totalPoints(),a.totalPoints());
		});
	}
}
