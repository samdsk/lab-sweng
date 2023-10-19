package it.unimi.di.sweng.briscola;

import org.jetbrains.annotations.NotNull;

public class Briscola {
  private final Deck deck;
  private final Card briscola;

  private boolean availableBriscolaCard = true;
  private final Player[] players = new Player[2];

  @NotNull
  private Player firstCardPlayer;

  public Briscola(@NotNull Player p1, @NotNull Player p2, @NotNull Deck d) {
    deck = d;

    players[0] = p1;
    players[1] = p2;
    firstCardPlayer = p1;

    for (int i = 0; i < 3; i++)
      for (int j = 0; j < 2; j++)
        players[j].giveCard(deck.draw());

    briscola = deck.draw();
  }

  public void playTurn() {

    System.out.println("\n\n---\nricordo che briscola è " + getBriscola());

    System.out.println(firstCardPlayer);
    Card firstCard = firstCardPlayer.chooseFirstCard(this);
    System.out.println("ha scelto: " + firstCard);

    System.out.println(otherPlayer(firstCardPlayer));
    Card secondCard = otherPlayer(firstCardPlayer).chooseSecondCard(this);
    System.out.println("ha scelto: " + secondCard);

    firstCardPlayer = establishTurnWinner(firstCard, secondCard);
    System.out.println("vince questa mano: " + firstCardPlayer.getName());
    firstCardPlayer.addWonCardsToPersonalDeck(firstCard, secondCard);
  }


  @NotNull  // spostato a livello private package per facilitarne il testing
  Player establishTurnWinner(@NotNull Card first, @NotNull Card second) {
    // DONE identifica (e restituisce) il vincitore del turno applicando le regole alle due carte giocate
    if(first.getSuit() == getBriscola() && second.getSuit() != getBriscola()){
      return firstCardPlayer;
    }

    if(first.getSuit() != getBriscola() && second.getSuit() == getBriscola()){
      return otherPlayer(firstCardPlayer);
    }

    if(first.getRank().ordinal() > second.getRank().ordinal())
      return firstCardPlayer;
    else return otherPlayer(firstCardPlayer);
  }

  public void giveEachPlayerOneCard() {
    firstCardPlayer.giveCard(draw());
    otherPlayer(firstCardPlayer).giveCard(draw());
  }

  @NotNull
  public Suit getBriscola() {
    return briscola.getSuit();
  }


  public boolean isBriscola(@NotNull Card c) {
    return c.getSuit() == getBriscola();
  }

  @NotNull
  private Card draw() {
    assert availableBriscolaCard;
    if (deck.isEmpty()) {
      availableBriscolaCard = false;
      return briscola;
    }
    return deck.draw();
  }

  public boolean availableCards() {
    return availableBriscolaCard;
  }

  @NotNull
  public Player establishGameWinner() {
    // TODO identifica (e restituisce) il vincitore della partita
    //  (o un NULL OBJECT adatto in caso di pareggio)

    Player otherPlayer = otherPlayer(firstCardPlayer);

    if(firstCardPlayer.compareTo(otherPlayer) > 0)
      return firstCardPlayer;
    else if(firstCardPlayer.compareTo(otherPlayer) < 0)
      return otherPlayer;
    else return Player.PAREGGIO;

  }

  @NotNull
  public Player otherPlayer(@NotNull Player player) {
    if (players[0] == player)
      return players[1];
    return players[0];
  }

  public static void main(String[] args) {
    Player carlo = new Player("carlo");
    Player mattia = new Player("mattia");
    Deck deck = Deck.createFullDeck();

    carlo.setFirstStrategy(
            new PlayLeastValuableToBeatOtherPlayer(
                    new PlaySemeBriscola(
                            new PlayFirstCard()))
    );

    carlo.setSecondStrategy(
            new PlayLeastValuableToBeatOtherPlayerSecondStrat(
                    new PlaySemeBriscola(
                    new PlayFirstCard()
            ))
    );

    mattia.setFirstStrategy(
            new PlayLeastValuable(
                    new PlaySemeBriscola(
                            new PlayFirstCard()
            ))
    );

    mattia.setSecondStrategy(
            new PlaySemeBriscola(
                    new PlayFirstCard())
    );

    Briscola briscola = new Briscola(carlo, mattia, deck); //dà carte iniziali e estrae briscola

    for (int i = 0; i < 20; i++) {
      briscola.playTurn(); //esegue scelta carte da giocare e attua giocata

      if (briscola.availableCards())
        briscola.giveEachPlayerOneCard();
    }

    Player winner = briscola.establishGameWinner();
    winner.shoutResult(); // il giocatore esulta per la propria vittoria, ma attenzione ai pareggi
  }
}


