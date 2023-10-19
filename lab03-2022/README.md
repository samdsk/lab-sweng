# CORSO INGEGNERIA DEL SOFTWARE A.A. 2022/23

## LABORATORIO 3

* TEAMMATE 1: <Cognome> <Nome> <matricola>
* TEAMMATE 2: <Cognome> <Nome> <matricola>

Ogni coppia di studenti procede a effettuare il **fork** di questo repository.
L'utente che ha effettuato il fork modifica questo README inserendo le opportune **informazioni sui
membri del team** seguendo lo schema sopra riportato.
Inoltre, concede i permessi di scrittura al proprio compagno di team e i **permessi di lettura** ai
docenti (`carlo.bellettini` e `mattia.monga`).



### Processo

Una volta effettuato il **clone** del repository, il gruppo implementa secondo la *metodologia TDD*
le specifiche riportate di seguito; in maggior dettaglio, ripete i passi seguenti fino ad aver implementato tutte le funzionalità richieste:

* implementa un test per le funzionalità richieste **procedendo nell'ordine** in cui sono specificate,
* verifica che **il codice compili correttamente**, ma l'**esecuzione del test fallisca**; solo a questo punto effettua un *commit* (usando `IntelliJ` o `git add` e `git commit`) iniziando il messaggio di commit con la stringa `ROSSO:`,
* aggiunge la minima implementazione necessaria a realizzare la funzionalità, in modo che **il test esegua con successo**; solo a questo punto
  effettua un *commit* (usando `IntelliJ` o `git add` e `git commit`) iniziando il messaggio di commit con la stringa `VERDE:`,
* procede, se necessario, al **refactoring** del codice, accertandosi che le modifiche non
  comportino il fallimento di alcun test; solo in questo caso fa seguire ad ogni
  passo un *commit* (usando `IntelliJ` o `git add` e `git commit`) iniziando il messaggio di commit con la stringa `REFACTORING:`,
* effettua un *push* dei passi svolti su gitlab.di.unimi.it con `IntelliJ` o`git push origin master`.

Al termine del laboratorio effettua un ultimo *push* e **verifica su
gitlab.di.unimi.it** che ci sia la completa traccia di *commit* effettuati. Si
suggerisce di eseguire i test non soltanto con Idea, ma anche eseguendo il
comando `gradle test` da riga di comando.

### Refactoring

Prestare attenzione ai seguenti [code smell](https://it.wikipedia.org/wiki/Code_smell):

* codice duplicato, o pressoché uguale, in diverse sezioni,
* troppi livelli di indentazione (es., > 2),
* metodo troppo lungo (es., > 10 linee di codice),
* lunghe sequenze di *if*-*else* o *switch case*,
* nomi di classi/metodi/campi/variabili non significativi,
* troppi attributi per classe (es., > 2),
* uso di metodi *setter*/*getter* per modificare/accedere campi privati.

In presenza di *code smell*, il gruppo effettua alcuni passi di *refactoring*,
per ottenere codice più *leggibile* e *manutenibile*.

Di seguito si accenna ad alcune possibili azioni di refactoring.
Accanto al tipo di refactoring è stata elencata una delle [Refactor Actions](https://www.baeldung.com/intellij-refactoring) di Idea (se presente) che la coppia può usare.

* [Rename Method](http://refactoring.com/catalog/renameMethod.html) (oppure Field, o Variable): *Rename*,
* [Replace Magic Number with Symbolic Constant](http://refactoring.com/catalog/replaceMagicNumberWithSymbolicConstant.html): *Extract Constant*,
* [Extract Variable](http://refactoring.com/catalog/extractVariable.html): *Extract  Variable*,  *Extract Field*,
* [Extract Method](http://refactoring.com/catalog/extractMethod.html): *Extract Method*,
* [Extract Class](http://refactoring.com/catalog/extractClass.html): *Extract Class*,
* [Replace Array with Object](http://refactoring.com/catalog/replaceArrayWithObject.html).

Una lista di possibili passi di refactoring è accessibile dal seguente
[Refactoring Catalog](https://refactoring.com/catalog/).



## Specifica dei requisiti

Le funzionalità  di seguito elencate vanno implementate **nell'ordine in cui sono presentate**. Si suggerisce  di prendere visione di una funzionalità  per volta (procedendo subito al ciclo di implementazione della medesima) in modo da non farsi influenzare dalle specifiche successive circa le scelte di progetto.

Obiettivo dell'esercizio è la creazione di un semplice interprete [Forth](https://en.wikipedia.org/wiki/Forth_(programming_language)). I programmi Forth sono stringhe, la cui interpretazione è guidata da una struttura dati Last-In-First-Out (stack). 

Un interprete Forth deve avere un metodo `input` che permette di resettare lo stack e trasmettere l'intero programma da eseguire (dati e istruzioni). 
Una rappresentazione testuale dello stack può essere ottenuta grazie al metodo `toString`. 

* Se non ha ricevuto nessun input (o una stringa vuota) il metodo toString riporta uno stack vuoto `"<- Top"`.
```java
input("") produce uno stack vuoto che toString riporta come "<- Top"
```

* La ricezione via input di un programma contenente solo numeri interi (separati da uno spazio), li impila sullo stack.
```java
input("1") produce uno stack che toString riporta come "1 <- Top"
input("1 2") produce uno stack che toString riporta come "1 2 <- Top"
```

* I numeri possono essere dotati di segno, ma nell'output va indicato solo se negativo
```java
input("1 -2 3") produce uno stack che toString riporta come "1 -2 3 <- Top"
input("1 +2 3") produce uno stack che toString riporta come "1 2 3 <- Top"
```

* Il separatore tra un elemento e l'altro nella stringa dell'input non è garantito essere un singolo spazio, ma può essere un qualunque numero di spazi e newline.
```java
input("1 2") produce ... "1 2 <- Top"
input("1\n2") produce ... "1 2 <- Top"
input("1   2 \n3") produce ... "1 2 3 <- Top"
```

* L'interprete è in grado di eseguire le somme: l'operatore `+`  sostituisce i due operandi in cima allo stack con la loro somma. 
```java
input("1 2 +") produce ... "3 <- Top"
input("1 2 + 5 +") produce ... "8 <- Top"
```

* L'interprete considera corretto l'input solo se tutti i token (numeri, operatori, parole chiave) sono separati da almeno un separatore. In caso contrario solleva un'eccezione IllegalArgumentException con messaggio "Token error '<token>'". 
```java
input("1 2+") solleva una eccezione IllegalArgumentException con messaggio "Token error '2+'"
input("1 2 ++5 +") solleva una eccezione IllegalArgumentException con messaggio "Token error '++5'"
```

* Se non c'è un numero sufficiente di operandi sullo stack, si ottiene un'eccezione IllegalArgumentException con messaggio "Stack Underflow".
```java
input("1 +") solleva una eccezione IllegalArgumentException con messaggio "Stack Underflow"
```

* L'interprete è in grado di eseguire le moltiplicazioni: l'operatore  `*` sostituisce i due operandi in cima allo stack con il loro prodotto. In caso di numero di operandi insufficienti, solleva eccezione come nel test precedente.
```java
input("1 2 *") produce ... "2 <- Top"
input("1 2 * 5 *") produce ... "10 <- Top"
```

* In maniera analoga l'interprete è in grado di eseguire le sottrazioni e le divisioni intere (operatori `-` e `/`)
```java
input("1 2 -") produce ... "-1 <- Top"
input("1 2 /") produce ... "0 <- Top"
```

* A questo punto rilasciare la **prima release** del vostro progetto di nome "v1.0"

* Il comando "dup" duplica la cima dello stack.
```java
input("1 2 3 dup") produce ... "1 2 3 3 <- Top"
```

* Il comando "swap" scambia due operandi sulla cima dello stack 
```java
input("1 2 3 swap") produce ... "1 3 2 <- Top"
```

* Il comando "drop" cancella il dato sulla cima dello stack.
```java
input("1 2 3 drop") produce ... "1 2 <- Top"
```

* Verificare (dovrebbe essere un verde diretto) che comandi e operazioni siano combinabili a piacere 
```java
input("1 2 + 3 * 4 dup 5 + drop swap") produce ... "4 9 <- Top"
input("1 2 + 3 * drop swap") solleva eccezione
```

* A questo punto rilasciare la **seconda release** del vostro progetto di nome "v2.0"

* L'interprete accetta anche la definizione di nuovi comandi (*word* nel gergo di Forth). Per farlo bisogna usare i due comandi `:` e `;` che denotano, rispettivamente, l'inizio e la fine della definizione, che verrà denominata usando la prima parola della definizione.
```java
// definizione della word "raddoppia"
input(": raddoppia 2 * ; 5 raddoppia dup raddoppia") produce ... "10 20 <- Top"
```

* La presenza in input di una word non definita solleva una eccezione `IllegalArgumentException` con messaggio `"Undefined word '<word>'"`.

```java
input("pippo") solleva una eccezione "Undefined word 'pippo'"
input("1 2 pippo") solleva una eccezione "Undefined word 'pippo'"
input("1 : raddoppia 2 * ; raddoppi raddoppia") solleva una eccezione "Undefined word 'raddoppi'"
```

* A questo punto avete finito e potete rilasciare la **terza release** del vostro progetto di nome "v3.0"

