# CORSO INGEGNERIA DEL SOFTWARE A.A. 2022/23

## LABORATORIO 5

* TEAMMATE 1: <Cognome> <Nome> <matricola> 
* TEAMMATE 2: <Cognome> <Nome> <matricola>

Ogni coppia di studenti procede a effettuare il **fork** di questo repository.
L'utente che ha effettuato il fork modifica questo README inserendo le opportune **informazioni sui
membri del team** seguendo lo schema sopra riportato.
Inoltre, concede i permessi di scrittura al proprio compagno di team e i **permessi di lettura** ai
docenti (`carlo.bellettini` e `mattia.monga`).



## Rubamazzetto

Il codice fornito simula partite a rubamazzetto. Ogni giocatore inizialmente
riceve 3 carte, e 4 carte vengono poste scoperte sul tavolo. A turno i giocatori
devono giocare una delle loro carte: se tale carta ha lo stesso valore di una
sul tavolo, il giocatore pone le due carte con lo stesso valore in un mazzetto
davanti a sé in modo che tutti possano vedere l'ultima "presa". Se non c'è una
carta dello stesso valore sul tavolo e invece uno dei mazzetti degli altri
giocatori ha in cima una carta dello stesso valore di quella giocata, il
giocatore può "rubare" il mazzetto all'avversario, sottraendolo all'avversario e
ponendolo insieme alla carta giocata in cima al proprio. Altrimenti la carta
viene posta insieme alle altre scoperta sul tavolo. Quando tutti i giocatori
hanno finito il loro turno, viene distribuita a ciascuno una nuova carta presa
dal mazzo, finché ce ne sono abbastanza; poi si gioca fino a che tutti hanno
esaurito le loro carte. Vince il giocatore che alla fine ha più carte nel
proprio mazzetto.

### Obiettivi

#### Comprensione del codice esistente

Esaminare le classi fornite per capire qual è l'architettura generale del programma.
Può esservi utile tentare di rispondere a queste domande:

- come controllare se una carta con un certo `Rank` è sul tavolo?

- qual è il significato dell'attributo `punti` della classe `Giocatore`?

- qual è la condizione che viene testata per capire se una partita è finita? 
  Potete pensare a una formulazione diversa (più semplice o più chiara)?

#### Scrittura del codice 

La maggior parte dei punti in cui dovete intervenire nel codice sono segnalati da un commento`//TODO`.

Procedete con una tecnica TDD: per ogni parte di codice che volete aggiungere, occorre partire da un test; 
se modificate una parte esistente, va aggiunto un test che copra la modifica voluta.


- far sì che `Tavolo` implementi l'interfaccia `Iterable<Card>`, permettendo di
  iterare sulle carte scoperte sul tavolo.

- completare `Giocatore.turno` in maniera che ogni giocatore giochi una carta
  (decisa da una strategia di gioco) in maniera conforme alle regole del gioco,
  descritte sopra.
  
- usare il pattern "Chain of Responsibility" per creare almeno 4 strategie di
  gioco che implementino l'interfaccia `SelettoreCarta` (da definire ma già citata nella classe `Giocatore`) per decidere 
  quale tra le carte **in mano** al giocatore verrà giocata. Per
  esempio, potete definire le seguenti strategie: 
  
    1. scegli una carta qualsiasi 
       (strategia che "funziona" sempre, cioè non c'è mai bisogno di una successiva strategia);
       Attenzione: scegliere "a caso" può rendere difficile costruire dei test, qual è un approccio più adatto al TDD?

    1. se c'è, scegli una carta che ha lo stesso valore di una di quelle sul tavolo;

    1. se c'è, scegli una carta che ha lo stesso valore della cima del mazzetto di uno degli avversari; 

    1. se c'è, scegli una carta che ha lo stesso valore della cima del tuo mazzetto (per proteggerlo).
   
Alla fine la simulazione di una partita dovrebbe essere qualcosa del genere (pseudocodice):

```java
  public static void main(String[] args) {

    Partita partita = new Partita();

    Giocatore Carlo = new Giocatore("Carlo", partita);
    Giocatore Mattia = new Giocatore("Mattia", partita);
    Giocatore Priscilla = new Giocatore("Priscilla", partita);

    //TODO: definire per ogni partecipante una composizione di strategie

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
```

