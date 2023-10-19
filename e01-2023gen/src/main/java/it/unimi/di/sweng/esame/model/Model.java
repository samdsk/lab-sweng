package it.unimi.di.sweng.esame.model;

import it.unimi.di.sweng.esame.Main;
import it.unimi.di.sweng.esame.Observable;
import it.unimi.di.sweng.esame.Observer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.io.InputStream;
import java.time.LocalTime;
import java.util.*;

public class Model{
  protected final Map<Treno,Partenza> partenze = new HashMap<>();

  public void readFile() {
    InputStream is = Main.class.getResourceAsStream("/trains.csv");
    assert is != null;
    Scanner s = new Scanner(is);

    while (s.hasNextLine()) {
      String linea = s.nextLine();
      String[] el = linea.split(",");
      String cod = el[0];
      String destination = el[1];
      String depTime = el[2];
      String delay = el[3];

      Treno treno = new Treno(cod);

      partenze.putIfAbsent(
              treno,
              new Partenza(
                      treno,
                      destination,
                      LocalTime.parse(depTime),
                      Integer.parseInt(delay)
              )
      );

      System.out.printf("cod: [%s] dest: [%s] time: [%s] delay: [%s]\n", cod, destination, depTime, delay);
    }

  }

  public int departureCount() {
    return partenze.size();
  }

  public boolean containsTrain(@NotNull Treno treno) {
    return partenze.containsKey(treno);
  }

  public void removeTrain(@NotNull Treno treno) {
    partenze.remove(treno);
  }

  public void setDelay(@NotNull Treno treno, int delay) {
    if(partenze.containsKey(treno)){
      partenze.put(treno,partenze.get(treno).changeDelay(delay));
    }
  }

  public @Nullable Partenza getPartenza(@NotNull Treno treno) {
    return partenze.get(treno);
  }
}
