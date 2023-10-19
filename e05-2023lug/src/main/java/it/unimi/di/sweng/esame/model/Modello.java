package it.unimi.di.sweng.esame.model;

import it.unimi.di.sweng.esame.Main;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.time.LocalTime;
import java.util.*;


public class Modello {
  private final Map<Appartamento,Segnalazione> segnalazioni = new HashMap<>();

  public void readFile() {
    InputStream is = Main.class.getResourceAsStream("/reports.csv");
    assert is != null;
    Scanner s = new Scanner(is);

    while (s.hasNextLine()) {
      String linea = s.nextLine();
      System.err.println(linea);

      String[] el = linea.split(";");
      String codice = el[0];
      Tecnico tecnico = Tecnico.valueOf(el[1]);
      Latitudine lat = new Latitudine(Double.parseDouble(el[2]));
      Longitudine lon = new Longitudine(Double.parseDouble(el[3]));

      Appartamento app = new Appartamento(codice,tecnico);
      Segnalazione segnalazione = new Segnalazione(app,lat,lon, LocalTime.parse(el[4]));

      segnalazioni.put(app,segnalazione);
    }
  }

  public @NotNull List<Segnalazione> getSegnalzioni() {
    return new ArrayList<>(segnalazioni.values());
  }

  public void segnala(@NotNull Segnalazione segnalazione) {
    if(contains(segnalazione.app()))
      throw new IllegalArgumentException("intervento già presente");

    segnalazioni.put(segnalazione.app(),segnalazione);
  }

  public boolean contains(@NotNull Appartamento app) {
    return segnalazioni.containsKey(app);
  }

  public void risolvi(@NotNull Appartamento app) {
    if(!contains(app))
      throw new IllegalArgumentException("intervento non presente");
    segnalazioni.remove(app);
  }
}
