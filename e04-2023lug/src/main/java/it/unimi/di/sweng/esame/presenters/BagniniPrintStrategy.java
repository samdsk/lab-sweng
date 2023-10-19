package it.unimi.di.sweng.esame.presenters;

import it.unimi.di.sweng.esame.model.Postazione;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class BagniniPrintStrategy implements PrintStrategy {
	private final Comparator<Postazione> comparator = Comparator.comparing(a -> a.bagnino().name());
	@Override
	public @NotNull String[] print(Postazione[] postazioni) {
		int size = postazioni.length;

		List<Postazione> list = new ArrayList<>();
		for (Postazione postazione : postazioni) {
			if(postazione != Postazione.EmptyPostazione)
				list.add(postazione);
		}

		list.sort(comparator);

		String[] ans = new String[size];
		int count_bagnini = 0;
		for (int i = 0; i < list.size(); i++) {
			ans[i] = list.get(i).formatBagnini();
			count_bagnini++;
		}

		for (int i = count_bagnini; i < size; i++) {
			ans[i] = "";
		}

		return ans;
	}
}
