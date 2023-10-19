package it.unimi.di.sweng.esame.presenters;

import it.unimi.di.sweng.esame.model.Postazione;

public class PostazionePrintStrategy implements PrintStrategy {
	@Override
	public String[] print(Postazione[] postazioni) {
		int size = postazioni.length;
		String[] ans = new String[size];

		for (int i = 0; i < size; i++) {
			ans[i] = postazioni[i].formatPostazione();
		}

		return ans;
	}
}
