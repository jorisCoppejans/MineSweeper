package main;

import domain.DomeinController;

public class StartUpConsole {
  static DomeinController dc = new DomeinController();

  public static void main(String[] args) {
    dc.maakSpel();

    dc.startSpel();
  }
}
