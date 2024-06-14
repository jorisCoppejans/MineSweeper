package main;

import domain.DomeinController;

public class StartUpConsole {
  static DomeinController dc = new DomeinController();

  public static void main(String[] args) {
    dc.startSpel();

    // while (!dc.isEindeSpel()) {
    dc.toonBord();
    // dc.vraagCoordinaten();
    // }

  }
}
