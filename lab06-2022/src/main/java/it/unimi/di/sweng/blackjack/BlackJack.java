package it.unimi.di.sweng.blackjack;

import ca.mcgill.cs.stg.solitaire.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class BlackJack {
  public static int cardValue(Card c) {
    return Math.min(c.getRank().ordinal() + 1, 10);
  }

  public static void main(String[] args) {
    Mazziere banco = new Mazziere();

    Sfidante carlo = new Sfidante("Carlo", banco);
    Sfidante mattia = new Sfidante("Mattia", banco);
    Sfidante violetta = new Sfidante("Violetta", banco);

    List<Sfidante> sfidanti = new ArrayList<>();

    carlo.setStrategia(new MassimissaPunteggioStrategy(new TerzoStrategy(Strategia.STARE)));
    mattia.setStrategia(new RaddoppiaStrategy(Strategia.STARE));
    violetta.setStrategia(new TerzoStrategy(new MassimissaPunteggioStrategy(Strategia.STARE)));

    sfidanti.add(carlo);
    sfidanti.add(mattia);
    sfidanti.add(violetta);

    for(Sfidante sf : sfidanti){
      sf.carteIniziali();
    }

    banco.carteIniziali();

    for(Sfidante sf : sfidanti){
      sf.gioca();
    }

    banco.gioca();

    //DONE  gestire lo svolgimento di una partita con i tre sfidanti

    System.out.println(banco);
    for (Sfidante sfidante : sfidanti) {
      System.out.println(sfidante);
      if (sfidante.isSballato() || (sfidante.getPunti() < banco.getPunti() && !banco.isSballato()))
        System.out.println("Vince il banco.");
      else if (banco.isSballato() || sfidante.getPunti() > banco.getPunti())
        System.out.println("Il banco perde!!!");
      else
        System.out.println("Pareggio.");
    }
  }
}
