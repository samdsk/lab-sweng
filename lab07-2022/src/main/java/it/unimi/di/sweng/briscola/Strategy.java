package it.unimi.di.sweng.briscola;

import org.jetbrains.annotations.NotNull;

public interface Strategy {
  // DONE: implementare almeno 3 strategie usabili per scelta prima carta
  //      e 3 strategie usabili per scelta seconda carta, strutturandole secondo il pattern CHAIN OF RESPONSIBILITY
  //      E' possibile che alcune siano condivise (cioè indipendenti da turno):
  //      ad esempio una eventuale strategia che gioca una carta qualsiasi

  @NotNull
  Card chooseCard(@NotNull Player me, @NotNull Player other, @NotNull Suit briscola);
}
