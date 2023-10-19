package it.unimi.di.sweng.blackjack;

// DONE implementare almeno tre strategie di vostra invenzione per comporre il modo di giocare dei giocatori
// potranno basarsi sulle proprie carte o sulla carta iniziale del banco

// 1. MassimissaPunteggioStrategy cerca di raggiungere almeno 17 punti
// 2. RaddoppiaStrategy dipendente dal banco e dalle carte del giocatore (maggiori info nel file Radddoppia)
// 3. TerzaStrategy cerca di raggiundere almeno 9 punti

public interface Strategia {
  //DONE ?  implementare la strategia finale che sceglie di
  // "stare" sempre, come NullObject Pattern
  boolean chiediCarta(GiocatoreBJ giocatore, GiocatoreBJ banco);

  // Null Object Pattern
  public static Strategia STARE = new Strategia() {
    @Override
    public boolean chiediCarta(GiocatoreBJ giocatore, GiocatoreBJ banco) {
      return false;
    }
  };
}

