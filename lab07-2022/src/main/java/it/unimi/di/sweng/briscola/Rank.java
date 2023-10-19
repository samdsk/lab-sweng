package it.unimi.di.sweng.briscola;

public enum Rank {
  
  DUE    (0),
  QUATTRO(0),
  CINQUE (0),
  SEI    (0),
  SETTE  (0),
  FANTE  (2),
  CAVALLO(3),
  RE     (4),
  TRE    (10),
  ASSO   (11);
  
  private final int points;

  Rank(int points) {
    this.points = points;
  }

  public int points() {
    return points;
  }
}
