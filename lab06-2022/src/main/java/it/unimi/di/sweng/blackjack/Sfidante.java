package it.unimi.di.sweng.blackjack;

import ca.mcgill.cs.stg.solitaire.cards.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Sfidante implements GiocatoreBJ{
  //DONE i vari metodi richiesti per aderire all'interfaccia GiocatoreBJ

  final private String name;
  final private Mazziere banco;
  final private List<Card> mano = new ArrayList<>();
  private Strategia strategia;


  public Sfidante(String name, Mazziere banco) {
    this.name = name;
    this.banco = banco;
  }

  public void setStrategia(Strategia strategia) {
    assert this.strategia == null : "non puoi cambiare strategia se è già settata";
    this.strategia = strategia;
  }

  @Override
  public void carteIniziali() {
    mano.add(banco.daiCarta());
    mano.add(banco.daiCarta());
  }

  @Override
  public void gioca() {
    if(strategia.chiediCarta(this,banco)){
      prendiCartaDalBanco();
      gioca();
    }
  }

  @Override
  public Iterator<Card> getCards() {
    return Collections.unmodifiableList(mano).iterator();
  }

  @Override
  public String getName() {
    return name;
  }

  public void prendiCartaDalBanco() {
    mano.add(banco.daiCarta());
  }

  @Override
  public String toString() {
    return asString();
  }


}
