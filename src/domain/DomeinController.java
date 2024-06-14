package domain;

import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class DomeinController {

  Scanner scanner = new Scanner(System.in);
  boolean eindeSpel = false;
  Bord bord;

  public void maakSpel() {
    int moeilijkheidsgraad = kiesMoeilijkheidsgraad();
    kiesBord(moeilijkheidsgraad);
    berekenAangrenzendeBommen();
    bord.showAangrenzendeBommen();
  }

  public void startSpel() {
    while (!this.eindeSpel) {
      toonBord();
      vraagActie();
    }

    toonOpenBord();
  }

  private void toonOpenBord() {
    bord.toonOpenBord();
  }

  private void berekenAangrenzendeBommen() {
    bord.berekenAangrenzendeBommen();
  }

  private void kiesBord(int moeilijkheidsgraad) {
    switch (moeilijkheidsgraad) {
      case 1:
        maakBord(8, 8, 10);
        break;
      case 2:
        maakBord(9, 9, 10);
        break;
      case 3:
        maakBord(16, 16, 40);
        break;
      case 4:
        maakBord(24, 16, 99);
        break;
      case 5:
        customBordMaken();
        break;
    }
  }

  private int kiesMoeilijkheidsgraad() {
    System.out
        .printf("Kies moeilijkheidsgraad: %n1. Makkelijk %n2. Gemiddeld %n3. Moeilijk %n4. Expert %n5. Custom %n");
    int moeilijkheidsgraad = scanner.nextInt();
    while (moeilijkheidsgraad < 1 || moeilijkheidsgraad > 5) {
      moeilijkheidsgraad = kiesMoeilijkheidsgraad();
    }
    return moeilijkheidsgraad;
  }

  private void customBordMaken() {
    System.out.println("Geef de breedte van het bord: ");
    int breedte = scanner.nextInt();
    controlleerBreedte(breedte);
    System.out.println("Geef de hoogte van het bord: ");
    int hoogte = scanner.nextInt();
    controlleerHoogte(hoogte);
    System.out.println("Geef het aantal bommen: ");
    int aantalBommen = scanner.nextInt();
    controlleerAantalBommen(aantalBommen, hoogte, breedte);

    maakBord(breedte, hoogte, aantalBommen);
  }

  private void controlleerAantalBommen(int aantalBommen, int hoogte, int breedte) {
    if (aantalBommen + 1 >= hoogte * breedte) {
      System.err.println("Zoveel bommen mag je niet plaatsen");
      customBordMaken();
    }
  }

  private void controlleerHoogte(int hoogte) {
    if (hoogte < 8 || hoogte > 24) {
      System.err.println("Hoogte moet tussen 8 en 24 liggen");
      customBordMaken();
    }
  }

  private void controlleerBreedte(int breedte) {
    if (breedte < 8 || breedte > 30) {
      System.err.println("Breedte moet tussen 8 en 30 liggen");
      customBordMaken();
    }
  }

  public void maakBord(int breedte, int hoogte, int aantalBommen) {
    Bord bord = new Bord(breedte, hoogte);
    plaatsBommen(aantalBommen, breedte, hoogte, bord);
    this.bord = bord;
    bord.showDimensions();
    bord.showBoms();
  }

  public void plaatsBommen(int aantal, int breedte, int hoogte, Bord bord) {
    Random random = new Random();
    for (int i = 0; i < aantal; i++) {
      int rij = random.nextInt(hoogte);
      int kolom = random.nextInt(breedte);

      Boolean magPlaatsen = controleerPlaatsBom(rij, kolom, bord);
      if (magPlaatsen) {
        bord.plaatsBom(rij, kolom);
      } else {
        plaatsBommen(1, bord.BreedteBord, bord.HoogteBord, bord);
      }
    }
  }

  private Boolean controleerPlaatsBom(int rij, int kolom, Bord bord) {
    Set<Pair> plaatsenBommen = bord.getPlaatsenBommen();
    if (plaatsenBommen.contains(new Pair(rij, kolom))) {
      return false;
    }
    return true;
  }

  public boolean isEindeSpel() {
    return eindeSpel;
  }

  public void toonBord() {
    bord.toonBord();
  }

  private void markeerBom(Pair veld) {
    this.eindeSpel = bord.markeerBom(veld.getX(), veld.getY());
  }

  private void vraagActie() {
    System.out.printf("wat wil je doen?%n 1. Openen %n 2. Markeren als bom%n 3. verwijder markering%n 4. Stop spel%n");
    int antwoord = scanner.nextInt();

    switch (antwoord) {
      case 1:
        Pair veld = vraagVak();
        this.eindeSpel = bord.openVak(veld.getX(), veld.getY());
        break;
      case 2:
        Pair veld2 = vraagVak();
        markeerBom(veld2);
        break;
      case 3:
        Pair veld3 = vraagVak();
        bord.verwijderMarkering(veld3.getX(), veld3.getY());
        break;
      case 4:
        this.eindeSpel = true;
        break;
      default:
        System.err.println("Antwoord moet 1, 2, 3 of 4 zijn");
        vraagActie();
        break;
    }
  }

  private Pair vraagVak() {
    System.out.println("Geef de rij: ");
    int rij = scanner.nextInt();
    rij--;
    if (rij < 0 || rij >= bord.HoogteBord) {
      System.err.println("Rij moet tussen 0 en " + (bord.HoogteBord - 1) + " liggen");
      return vraagVak();
    }

    System.out.println("Geef de kolom: ");
    int kolom = scanner.nextInt();
    kolom--;
    if (kolom < 0 || kolom >= bord.BreedteBord) {
      System.err.println("Kolom moet tussen 0 en " + (bord.BreedteBord - 1) + " liggen");
      return vraagVak();
    }

    return new Pair(rij, kolom);
  }
}
