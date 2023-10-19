package it.unimi.di.sweng.briscola;

import org.jetbrains.annotations.NotNull;

public final class Card {

  private static final Card[][] CARDS = new Card[Suit.values().length][];

  static {
    for (Suit suit : Suit.values()) {
      CARDS[suit.ordinal()] = new Card[Rank.values().length];
      for (Rank rank : Rank.values())
        CARDS[suit.ordinal()][rank.ordinal()] = new Card(rank, suit);
    }
  }

  @NotNull
  private final Rank rank;
  @NotNull
  private final Suit suit;

  private Card(@NotNull Rank rank, @NotNull Suit suit) {
    this.rank = rank;
    this.suit = suit;
  }

  @NotNull
  public static Card get(@NotNull Rank rank, @NotNull Suit suit) {
    return CARDS[suit.ordinal()][rank.ordinal()];
  }

  @NotNull
  public Rank getRank() {
    return rank;
  }

  @NotNull
  public Suit getSuit() {
    return suit;
  }

  @Override
  @NotNull
  public String toString() {
    return rank + " di " + suit;
  }


}
