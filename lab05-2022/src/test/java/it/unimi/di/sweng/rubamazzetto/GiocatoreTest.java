package it.unimi.di.sweng.rubamazzetto;
import ca.mcgill.cs.stg.solitaire.cards.Card;


import ca.mcgill.cs.stg.solitaire.cards.Rank;
import ca.mcgill.cs.stg.solitaire.cards.Suit;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GiocatoreTest {
    List<Card> carlo_carte = List.of(
            Card.get(Rank.ACE, Suit.DIAMONDS),
            Card.get(Rank.FIVE, Suit.DIAMONDS),
            Card.get(Rank.ACE, Suit.CLUBS)
    );

    List<Card> maria_carte = List.of(
            Card.get(Rank.TWO, Suit.SPADES),
            Card.get(Rank.TEN, Suit.DIAMONDS),
            Card.get(Rank.ACE, Suit.SPADES)
    );

    List<Card> scoperte = List.of(
            Card.get(Rank.ACE, Suit.HEARTS),
            Card.get(Rank.TWO, Suit.DIAMONDS),
            Card.get(Rank.THREE, Suit.SPADES),
            Card.get(Rank.FOUR, Suit.CLUBS)
    );

    @Test
    void testTurnoCartaSulTavolo() {
        // Testa con una carta presente sul tavolo
        SelettoreCarta strategy = new SelettoreCartaScoperta(null);

        Partita partita = new Partita();

        Giocatore carlo = new Giocatore("Carlo",partita);
        Giocatore maria = new Giocatore("Maria",partita);

        Tavolo tavolo = partita.getTavolo();
        createTavoloFromList(tavolo,scoperte);

        daiCarteGiocatore(carlo, carlo_carte);
        daiCarteGiocatore(maria, maria_carte);

        carlo.turno(partita,strategy);
        maria.turno(partita,strategy);

        assertThat(carlo.getPunti()).isEqualTo(2);
        assertThat(carlo.getMazzettoTop()).isEqualTo(Rank.ACE);

        assertThat(maria.getPunti()).isEqualTo(2);
        assertThat(maria.getMazzettoTop()).isEqualTo(Rank.TWO);

        assertThat(partita.getTavolo().size()).isEqualTo(2);
    }
    @Test
    void testGiocaPrimaCarta() {
        SelettoreCarta strategy = new SelettorePrimaCarta(null);

        Partita partita = new Partita();

        Giocatore carlo = new Giocatore("Carlo",partita);

        Tavolo tavolo = partita.getTavolo();
        createTavoloFromList(tavolo,scoperte);

        List<Card> carte = List.of(
                Card.get(Rank.TEN, Suit.DIAMONDS),
                Card.get(Rank.FIVE, Suit.DIAMONDS),
                Card.get(Rank.JACK, Suit.CLUBS)
        );

        daiCarteGiocatore(carlo, carte);

        carlo.turno(partita,strategy);

        assertThat(carlo.getPunti()).isEqualTo(0);
        assertThat(carlo.getMazzettoTop()).isEqualTo(null);

        assertThat(partita.getTavolo().size()).isEqualTo(5);
    }

    @Test
    void testTurnoRubaMazzo() {

        SelettoreCarta strategy = new SelettoreCartaScoperta(null);
        SelettoreCarta strategy_rubamazzetto = new SelettoreCartaRubaMazzetto(null);

        Partita partita = new Partita();

        Giocatore carlo = new Giocatore("Carlo",partita);
        Giocatore maria = new Giocatore("Maria",partita);

        Tavolo tavolo = partita.getTavolo();
        createTavoloFromList(tavolo,scoperte);

        daiCarteGiocatore(carlo, carlo_carte);
        daiCarteGiocatore(maria, maria_carte);

        carlo.turno(partita,strategy);
        maria.turno(partita,strategy_rubamazzetto);

        assertThat(carlo.getPunti()).isEqualTo(0);
        assertThat(carlo.getMazzettoTop()).isEqualTo(null);

        // carlo prende ACE dal tavolo e poi maria gioca ACE
        // visto che carlo ha ACE sul Mazzetto Top
        // carlo quindi prede tutti i punti
        // maria ha un mazzetto con 3 carte = punti = 3
        // con Mazzetto Top = ACE

        assertThat(maria.getPunti()).isEqualTo(3);
        assertThat(maria.getMazzettoTop()).isEqualTo(Rank.ACE);

        assertThat(partita.getTavolo().size()).isEqualTo(3);

    }

    @Test
    void testProteggiMazzetto() {
        SelettoreCarta strategy_tavolo = new SelettoreCartaScoperta(null);
        SelettoreCarta strategy_proteggi = new ProteggiMazzetto(null);
        SelettoreCarta strategy_ruba = new SelettoreCartaRubaMazzetto(new SelettoreCartaScoperta(null));

        Partita partita = new Partita();

        Giocatore carlo = new Giocatore("Carlo",partita);
        Giocatore maria = new Giocatore("Maria",partita);

        Tavolo tavolo = partita.getTavolo();
        createTavoloFromList(tavolo,scoperte);

        daiCarteGiocatore(carlo, carlo_carte);
        daiCarteGiocatore(maria, maria_carte);

        carlo.turno(partita,strategy_tavolo);
        maria.turno(partita,strategy_tavolo);

        // tavolo check

        assertThat(carlo.getPunti()).isEqualTo(2);
        assertThat(carlo.getMazzettoTop()).isEqualTo(Rank.ACE);

        assertThat(maria.getPunti()).isEqualTo(2);
        assertThat(maria.getMazzettoTop()).isEqualTo(Rank.TWO);

        assertThat(partita.getTavolo().size()).isEqualTo(2);

        // proteggi

        carlo.turno(partita,strategy_proteggi);

        // gioca ruba ACE da carlo
        maria.turno(partita,strategy_ruba);

        assertThat(carlo.getPunti()).isEqualTo(2);
        assertThat(carlo.getMazzettoTop()).isEqualTo(Rank.ACE);

        assertThat(maria.getPunti()).isEqualTo(4);
        assertThat(maria.getMazzettoTop()).isEqualTo(Rank.ACE);

        assertThat(partita.getTavolo().size()).isEqualTo(2);
    }
    @Test
    void testChain() {
        Partita partita = new Partita();

        Giocatore carlo = new Giocatore("Carlo",partita);
        Giocatore maria = new Giocatore("Maria",partita);

        Tavolo tavolo = partita.getTavolo();
        createTavoloFromList(tavolo,scoperte);

        daiCarteGiocatore(carlo, carlo_carte);
        daiCarteGiocatore(maria, maria_carte);

        // carlo prede da tavolo ACE

        carlo.turno();

        assertThat(carlo.getPunti()).isEqualTo(2);
        assertThat(carlo.getMazzettoTop()).isEqualTo(Rank.ACE);

        // maria ruba mazzetto a carlo ACE
        maria.turno();

        assertThat(carlo.getPunti()).isEqualTo(0);
        assertThat(carlo.getMazzettoTop()).isEqualTo(null);

        assertThat(maria.getPunti()).isEqualTo(3);
        assertThat(maria.getMazzettoTop()).isEqualTo(Rank.ACE);

        assertThat(partita.getTavolo().size()).isEqualTo(3);

        // la nuova carta viene aggiunto in coda

        carlo.daiCarta(Card.get(Rank.TEN,Suit.CLUBS));
        maria.daiCarta(Card.get(Rank.NINE,Suit.CLUBS));

        // carlo ruba ACE a maria
        carlo.turno();

        assertThat(carlo.getPunti()).isEqualTo(4);
        assertThat(carlo.getMazzettoTop()).isEqualTo(Rank.ACE);

        //maria gioca tavolo 2
        maria.turno();

        assertThat(maria.getPunti()).isEqualTo(2);
        assertThat(maria.getMazzettoTop()).isEqualTo(Rank.TWO);

        carlo.daiCarta(Card.get(Rank.SEVEN,Suit.CLUBS));
        maria.daiCarta(Card.get(Rank.TWO,Suit.SPADES));

        // gioca prima carta
        carlo.turno();

        assertThat(carlo.getPunti()).isEqualTo(4);
        assertThat(carlo.getMazzettoTop()).isEqualTo(Rank.ACE);

        assertThat(partita.getTavolo().size()).isEqualTo(3);
        assertThat(partita.getTavolo().inMostra(Card.get(Rank.FIVE,Suit.DIAMONDS))).isEqualTo(true);

        //maria gioca proteggi
        maria.turno();

        assertThat(maria.getPunti()).isEqualTo(2);
        assertThat(maria.getMazzettoTop()).isEqualTo(Rank.TWO);

        assertThat(partita.getTavolo().size()).isEqualTo(4);
        assertThat(partita.getTavolo().inMostra(Card.get(Rank.TWO,Suit.SPADES))).isEqualTo(true);
    }

    private static void daiCarteGiocatore(Giocatore maria, List<Card> maria_carte) {
        for(Card c : maria_carte)
            maria.daiCarta(c);
    }

    private static void createEmptyTavolo(Tavolo tavolo) {
        List<Card> carte_da_togliere = new ArrayList<>();

        for(Card c : tavolo){
            carte_da_togliere.add(c);
        }

        for(Card c : carte_da_togliere)
            tavolo.prendi(c);
    }

    private static void createTavoloFromList(Tavolo tavolo,List<Card> cards){
        createEmptyTavolo(tavolo);

        for(Card c : cards)
            tavolo.metti(c);
    }
}