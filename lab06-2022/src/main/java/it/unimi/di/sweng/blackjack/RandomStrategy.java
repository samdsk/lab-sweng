package it.unimi.di.sweng.blackjack;

// ha senso concatenare una dopo l'altra due strategie Random?
public class RandomStrategy implements Strategia {
  final private Strategia next;

  public RandomStrategy(Strategia next) {
    assert next != null : "se si usa NullObject Pattern non si ha mai strategia 'null'";
    this.next = next;
  }

  @Override
  public boolean chiediCarta(GiocatoreBJ giocatore, GiocatoreBJ banco) {
    if (Math.random() > 0.5)
      return true;
    return next.chiediCarta(giocatore,banco);
  }
}
