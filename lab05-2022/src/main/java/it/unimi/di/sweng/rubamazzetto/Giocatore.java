package it.unimi.di.sweng.rubamazzetto;

import ca.mcgill.cs.stg.solitaire.cards.Card;
import ca.mcgill.cs.stg.solitaire.cards.Rank;

import java.util.ArrayList;
import java.util.List;

public class Giocatore {

  private final String nome;
  private List<Card> mano = new ArrayList<>();
  private Rank mazzettoTop;

  private final Partita partita;
  private static final SelettoreCarta strategy = new SelettoreCartaRubaMazzetto(
          new SelettoreCartaScoperta(
                  new ProteggiMazzetto(
                          new SelettorePrimaCarta(null))));

  private int punti;

  public Giocatore(String nome, Partita partita) {
    this.nome = nome;
    this.partita = partita;
    partita.addGiocatore(this);
    punti = 0;
  }

  public Rank getMazzettoTop() {
    return mazzettoTop;
  }

  public int getPunti() {
    return punti;
  }

  public void daiCarta(Card carta) {
    mano.add(carta);
  }

  public void turno(Partita external_partita, SelettoreCarta external_strategy) {
    turnoHelper(external_partita,external_strategy);
  }

  private void turnoHelper(Partita partita, SelettoreCarta strategy){
    CardAndPoints result = strategy.strategy(partita,List.copyOf(mano),this);
    // System.out.println(nome+" "+result);

    if(result == null) return;

    if(mano.contains(result.card())) {
      mano.remove(result.card());
    }

    if(result.points() == 0) return;

    punti += result.points() + 1;
    mazzettoTop = result.card().getRank();

    if(partita.getTavolo().inMostra(result.card())){
      partita.getTavolo().prendi(result.card());
      return;
    }

    for(Giocatore player : partita){
      if(player == this) continue;

      if(player.getMazzettoTop() == result.card().getRank()){
        player.mazzettoTop = null;
        player.punti = 0;
        return;
      }
    }
  }
  public void turno() {
    // TODO: svolge il proprio turno scegliendo (e poi giocando) una delle proprie carte in base alla propria strategia
    turnoHelper(this.partita,this.strategy);
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder(nome);
    s.append(": ");
    s.append("[").append(mano.size()).append("]");
    if (punti > 0) {
      s.append("mazzetto con ");
      s.append(punti);
      s.append(" carte, cima ");
      s.append(mazzettoTop);
      s.append("; ");
    }
    for (Card card : mano) {
      s.append(card.toString());
      s.append(", ");
    }
    return s.toString();
  }

  public int numCards() {
    return mano.size();
  }
}
