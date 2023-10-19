package it.unimi.di.sweng.esame.model;

import it.unimi.di.sweng.esame.Main;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Model {
	public Model() {
		postazioni = new Postazione[Main.NUMPOSTAZIONI];
		Arrays.fill(postazioni, Postazione.EmptyPostazione);
	}

	private final Postazione[] postazioni;
	private final Map<Bagnino,Postazione> bagninoPostazioneMap = new HashMap<>();
	public void arriva(@NotNull Postazione postazione, int zona) {
		zonaCheck(zona);

		if(postazioni[zona] != Postazione.EmptyPostazione)
			throw new IllegalArgumentException("postazione già occupata");

		if(contains(postazione.bagnino()))
			throw new IllegalArgumentException("bagnino già presente in altra postazione");

		postazioni[zona] = postazione;
		bagninoPostazioneMap.put(postazione.bagnino(),postazione);
	}

	public boolean contains(@NotNull Bagnino bagnino) {
		return bagninoPostazioneMap.containsKey(bagnino);
	}
	public boolean contains(int zona) {
		zonaCheck(zona);

		return postazioni[zona] != Postazione.EmptyPostazione;
	}

	public void vaVia(int zona) {
		zonaCheck(zona);

		if(!contains(zona))
			throw new IllegalArgumentException("Bagnino non presente");

		Postazione postazione = postazioni[zona];

		postazioni[zona] = Postazione.EmptyPostazione;
		bagninoPostazioneMap.remove(postazione.bagnino());
	}

	private void zonaCheck(int zona){
		if(zona<0 || zona>=Main.NUMPOSTAZIONI)
			throw new IllegalArgumentException("zona non valida");
	}

	public void segnala(@NotNull Flag flag, int zona) {
		zonaCheck(zona);
		if(contains(zona)){
			postazioni[zona].changeFlag(flag);
		}else throw new IllegalArgumentException("postazione non presidiata");
	}

	public Postazione[] getPostazioni() {
		Postazione[] ans = new Postazione[Main.NUMPOSTAZIONI];
		for (int i = 0; i < Main.NUMPOSTAZIONI; i++) {
			if(postazioni[i] == Postazione.EmptyPostazione)
				ans[i] = Postazione.EmptyPostazione;
			else ans[i] = new ConcretePostazione(postazioni[i]);
		}

		return ans;
	}
}
