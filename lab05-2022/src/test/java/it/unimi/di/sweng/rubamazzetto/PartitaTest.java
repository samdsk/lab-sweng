package it.unimi.di.sweng.rubamazzetto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PartitaTest {

    @Test
    void distribuisciMano() {
        Partita partita = new Partita();

        Giocatore carlo = new Giocatore("Carlo",partita);
        Giocatore maria = new Giocatore("Maria",partita);

        partita.distribuisciMano(3);

        assertThat(carlo.numCards()).isEqualTo(3);
        assertThat(maria.numCards()).isEqualTo(3);
    }

    @Test
    void testDeckSize() {
        Partita partita = new Partita();

        for (int i = 0; i < 55; i++) {
            Giocatore g = new Giocatore(i+"",partita);
        }

        partita.distribuisciMano(3);

        for(Giocatore g : partita){
            assertThat(g.numCards()).isEqualTo(0);
        }
    }

    @Test
    void testPartita() {

        Partita partita = new Partita();

        Giocatore Carlo = new Giocatore("Carlo", partita);
        Giocatore Mattia = new Giocatore("Mattia", partita);
        Giocatore Priscilla = new Giocatore("Priscilla", partita);

        //DONE: definire per ogni partecipante una composizione di strategie:
        // ho definito la priorita della catena di strategie come:
        // RubaMazzo -> Prendi da Tavolo -> Proteggi proprio mazzetto -> gioca la prima carta che hai

        partita.distribuisciMano(3);
        while (!partita.isFinita()){

            System.out.println(partita);
            for (Giocatore giocatore : partita) {
                giocatore.turno();
            }
            partita.distribuisciMano(1);
            System.out.println(partita);

        }
    }
}