# CORSO INGEGNERIA DEL SOFTWARE A.A. 2022/23

## LABORATORIO 7 (VALUTATO)

* TEAMMATE 1: <Cognome> <Nome> <matricola> 
* TEAMMATE 2: <Cognome> <Nome> <matricola>

Ogni coppia di studenti effettua il **fork** di questo repository.
L'utente che ha effettuato il fork modifica questo README inserendo le opportune **informazioni sui
membri del team** seguendo lo schema sopra riportato.
Inoltre, concede i permessi di scrittura al proprio compagno di team e i **permessi di lettura** ai
docenti (`carlo.bellettini` e `mattia.monga`).

## Briscola a 2 a carte scoperte

### Il gioco

Si deve implementare il gioco della [briscola](https://it.wikipedia.org/wiki/Briscola) nella [variante a 2 giocatori con carte scoperte](https://it.wikipedia.org/wiki/Varianti_della_briscola#Briscola_scoperta) 

All'inizio della partita vengono distribuite 3 carte a ciascuno dei due giocatori 
e viene lasciata una carta scoperta sotto il mazzo che indica il seme di briscola e sarà l'ultima carta a essere distribuita.

Il giocatore di turno, cala una carta e di seguito fa lo stesso l'altro.
Vince il turno il giocatore che ha calato la briscola di valore maggiore o, nel caso in cui non siano state giocate briscole,
il giocatore che ha calato la carta di valore maggiore dello stesso seme della prima carta giocata nella mano (detto *seme di mano*).

Il giocatore che vince il turno prende le due carte giocate e 
le aggiunge al proprio mazzetto. 
Quindi (se ci sono ancora carte disponibili nel mazzo) 
viene data una carta ciascuno (la prima al vincitore del turno).

Il giocatore che vince il turno, diventa il *giocatore di turno* del turno successivo.

Si gioca con un mazzo di 40 carte di semi italiani. 
I valori sono nell'ordine decrescente: 
asso (11 punti), tre (10 punti), re (4 punti), cavallo (3 punti), fante (2 punti), sette, sei, cinque, quattro e due (tutte a 0 punti)

Vince la partita chi totalizza almeno 61 punti (somma dei punti delle carte prese). La partita finisce in pareggio se entrambi i giocatori totalizzano 60 punti,

Ogni giocatore, quando gioca, vede (e quindi può sfruttare nella propria strategia di gioco) sia le proprie carte che quelle dell'avversario, e il secondo del turno conosce la carta prescelta dal primo.


# Codice

Vengono fornite già diverse classi da completare (vedi i `TODO` nei commenti nel codice fornito).

Un possibile `main` che usi le classi definite è:

```java
public static void main(String[] args) {
  Player carlo = new Player("carlo");
  Player mattia = new Player("mattia");
  Deck deck = Deck.createFullDeck();

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
```

Attenzione: nel codice sono presenti anche alcune funzioni attualmente non richiamate,
ma che potrebbero servire per definire le strategie.

### Processo

Una volta effettuato il **clone** del repository, il gruppo completa l'implementazione seguendo la *metodologia TDD*; 
in maggior dettaglio, ripete i passi seguenti fino ad aver implementato tutte le funzionalità richieste:

* scelta la prossima funzionalità richiesta da implementare, inizia una feature di gitflow
* implementa un test per la funzionalità,
* verifica che **il codice compili correttamente**, ma l'**esecuzione del test fallisca**; solo a questo punto effettua un *commit*
  (usando `IntelliJ` o `git add` e `git commit`) iniziando il messaggio di commit con la stringa `ROSSO:`,
* aggiunge la minima implementazione necessaria a realizzare la funzionalità, in modo che **il test esegua con successo**; solo a questo punto
  effettua un *commit* (usando `IntelliJ` o `git add` e `git commit`) iniziando il messaggio di commit con la stringa `VERDE:`,
* procede, se necessario, al **refactoring** del codice, accertandosi che le modifiche non
  comportino il fallimento di alcun test; solo in questo caso fa seguire a ogni
  passo un *commit* (usando `IntelliJ` o `git add` e `git commit`)
  iniziando il messaggio di commit con la stringa `REFACTORING:`,
* ripete i passi precedenti fino a quando non considera la funzionalità realizzata nel suo complesso e allora chiude la feature di gitflow
* effettua un *push* dei passi svolti su gitlab.di.unimi.it con `IntelliJ` o`git push --all`.

Al termine del laboratorio impacchetta l'ultima versione stabile come una release di gitflow chiamata "consegna" ed effettua un ultimo *push* di tutti i rami locali (comprese eventuali feature aperte ma non completate).
Suggeriamo di **verificare su gitlab.di.unimi.it** che ci sia la completa traccia dei *commit* effettuati e di averne dato visibilità ai docenti. 
