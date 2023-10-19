package it.unimi.di.sweng.esame.model;


import it.unimi.di.sweng.esame.Main;
import it.unimi.di.sweng.esame.Observable;
import it.unimi.di.sweng.esame.Observer;
import org.jetbrains.annotations.NotNull;


import java.io.InputStream;

import java.util.*;

public class Model implements Observable<List<Canzone>> {
  private final Map<String,Canzone> canzoni = new HashMap<>();
  private final List<Observer<List<Canzone>>> observers = new ArrayList<>();
  private final Map<String,String> countries = new HashMap<>();

  //TODO completare la classe

  public void readFile() {
    InputStream is = Main.class.getResourceAsStream("/FinalistNations");
    assert is != null;
    Scanner s = new Scanner(is);

    while (s.hasNextLine()) {
      String linea = s.nextLine();
      String[] el = linea.split(";");
      String name = el[0];
      String cod = el[1];

      Canzone canzone = new Canzone(cod,name);
      canzoni.put(cod,canzone);

      countries.put(name,cod);

      System.out.printf("cod: [%s] name: [%s]\n", cod, name);
    }
  }

  public boolean containsCanzone(@NotNull String code) {
    return canzoni.containsKey(code);
  }

  @Override
  public void addObserver(@NotNull Observer<List<Canzone>> observer) {
    observers.add(observer);
  }

  @Override
  public void notifyObservers() {
    for (Observer<List<Canzone>> o : observers) {
      o.update(this,getState());
    }
  }

  @Override
  public @NotNull List<Canzone> getState() {
    List<Canzone> ans = new ArrayList<>();
    for (Canzone c : canzoni.values()) {
      ans.add(new Canzone(c));
    }
    return ans;
  }

  public @NotNull List<String> getCountries() {
    return new ArrayList<>(countries.keySet());
  }

  public void vote(@NotNull String code, int vote) {
    if(canzoni.containsKey(code)){
      canzoni.get(code).vote(vote);
      notifyObservers();
      return;
    }
    throw new IllegalArgumentException("Invalid vote code: "+code);
  }

  public @NotNull String getCodeFromCountryName(@NotNull String country) {
    if(countries.containsKey(country))
      return countries.get(country);

    throw new IllegalArgumentException("Invalid country: "+country);

  }
}
