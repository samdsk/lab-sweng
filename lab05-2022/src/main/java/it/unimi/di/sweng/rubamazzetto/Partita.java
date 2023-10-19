package it.unimi.di.sweng.rubamazzetto;

import ca.mcgill.cs.stg.solitaire.cards.CardStack;
import ca.mcgill.cs.stg.solitaire.cards.Deck;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Partita implements Iterable<Giocatore> {
  private final Deck mazzo = new Deck();
  private final List<Giocatore> giocatori = new ArrayList<>();

  public Tavolo getTavolo() {
    return tavolo;
  }

  private final Tavolo tavolo = new Tavolo();

  public Partita() {
    for (int i = 0; i < 4; i++) {
      tavolo.metti(mazzo.draw());
    }
  }

  public void addGiocatore(Giocatore giocatore) {
    giocatori.add(giocatore);
  }

  private void distribuisciCarta() {
    assert deckSize() >= giocatori.size();

    for (Giocatore giocatore : giocatori) {
      giocatore.daiCarta(mazzo.draw());
    }
  }

  public void distribuisciMano(int num) {
    // PRE CONDIZIONI
    assert num <= 3;

    // DONE: distribuisce fino a num carte per giocatore con il vincolo di dare a tutti lo stesso numero di carte

    // DONE: controllare con test decksize < giocatori.size

    if(deckSize()<giocatori.size()) return;

    for (int i = 0; i < num; i++) {
      distribuisciCarta();
    }

    // POST CONDIZIONI
    for (Giocatore giocatore : giocatori) {
      assert giocatore.numCards() <= 3 : "non si possono avere più di tre carte in mano";
      assert giocatori.get(0).numCards() == giocatore.numCards() : "non è stato dato stesso numero di carte a tutti";
      assert giocatore.numCards() == 3 || deckSize() < giocatori.size() : "si possono avere meno di tre carte solo se nel mazzo non ce ne sono abbastanza per fare un altro giro";
    }
  }


  public boolean isFinita() {
    assert giocatori.size() > 1;
    int cartegiocate = tavolo.size();
    for (Giocatore giocatore : giocatori) {
      cartegiocate += giocatore.getPunti();
    }

    return deckSize() < giocatori.size() && 52 - cartegiocate == deckSize();
  }

  private int deckSize() {
    int s = 0;
    CardStack tmp = new CardStack();
    while (!mazzo.isEmpty()) {
      tmp.push(mazzo.draw());
      s += 1;
    }
    while (!tmp.isEmpty()) {
      mazzo.push(tmp.pop());
    }
    return s;
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder();
    for (Giocatore giocatore : giocatori) {
      s.append(giocatore.toString());
      s.append("\n");
    }
    s.append("Tavolo: ");
    s.append(tavolo.toString());
    s.append("\n");
    s.append("Finita: ");
    s.append(isFinita());
    return s.toString();
  }

  @NotNull
  @Override
  public Iterator<Giocatore> iterator() {
    return giocatori.iterator();
  }
}
