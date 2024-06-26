package domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Bord {
  List<List<Veld>> velden;
  int BreedteBord;
  int HoogteBord;
  Set<Pair> bomPlaatsen = new HashSet<>();
  int bommenOntdekt = 0;
  int geplaatsteBommen = 0;

  public Bord(int breedteBord, int hoogteBord) {
    this.BreedteBord = breedteBord;
    this.HoogteBord = hoogteBord;

    maakVelden();
  }

  private void maakVelden() {
    velden = new ArrayList<>();
    for (int x = 0; x < HoogteBord; x++) {
      List<Veld> rij = new ArrayList<>();
      for (int y = 0; y < BreedteBord; y++) {
        rij.add(new Veld(x, y));
      }

      velden.add(rij);
    }
  }

  public void showDimensions() {
    System.out.printf("Breedte: %d, Hoogte: %d\n", BreedteBord, HoogteBord);
    System.out.println("Total size: " + velden.stream().mapToInt(List::size).sum());
  }

  public void plaatsBom(int rij, int kolom) {
    velden.get(rij).get(kolom).setStatus(VakStatus.BOM);
    bomPlaatsen.add(new Pair(rij, kolom));
  }

  public void showBoms() {
    getPlaatsenBommen().forEach(veld -> System.out.printf("Bom op rij %d, kolom %d\n", veld.getX(), veld.getY()));
    System.out.println("Total bombs: " + getPlaatsenBommen().size());
  }

  public Set<Pair> getPlaatsenBommen() {
    return this.bomPlaatsen;
  }

  public void berekenAangrenzendeBommen() {
    int[] extraX = { -1, -1, -1, 0, 0, 1, 1, 1 };
    int[] extraY = { -1, 0, 1, -1, 1, -1, 0, 1 };

    for (int rij = 0; rij < this.velden.size(); rij++) {
      for (int kolom = 0; kolom < this.velden.get(rij).size(); kolom++) {
        if (this.velden.get(rij).get(kolom).getStatus() != VakStatus.BOM) {
          int aantalAangrenzendeBommen = 0;
          for (int i = 0; i < 8; i++) {
            int x = rij + extraX[i];
            int y = kolom + extraY[i];

            if (x >= 0 && x < this.velden.size() && y >= 0 && y < this.velden.get(0).size()) {
              if (this.velden.get(x).get(y).getStatus() == VakStatus.BOM) {
                aantalAangrenzendeBommen++;
              }
            }
          }
          this.velden.get(rij).get(kolom).setAantalAangrenzendeBommen(aantalAangrenzendeBommen);
          if (aantalAangrenzendeBommen > 0) {
            this.velden.get(rij).get(kolom).setStatus(VakStatus.AANGRENSENDE);
          }
        }
      }
    }
  }

  public void showAangrenzendeBommen() {
    for (int rij = 0; rij < this.velden.size(); rij++) {
      for (int kolom = 0; kolom < this.velden.get(rij).size(); kolom++) {
        System.out.printf("Rij: %d, Kolom: %d, Aantal aangrenzende bommen: %d\n", rij, kolom,
            this.velden.get(rij).get(kolom).getAantalAangrenzendeBommen());
      }
    }
  }

  public void toonBord() {
    for (int rij = 0; rij < this.velden.size(); rij++) {
      for (int kolom = 0; kolom < this.velden.get(rij).size(); kolom++) {
        Veld veld = this.velden.get(rij).get(kolom);
        if (veld.isGemarkeerd()) {
          System.out.print("|X|");
        } else if (!veld.isOpen()) {
          System.out.print("| |");
        } else if (veld.getStatus() == VakStatus.BOM) {
          System.out.print("|B|");
        } else if (veld.getStatus() == VakStatus.AANGRENSENDE) {
          System.out.printf("|%d|", veld.getAantalAangrenzendeBommen());
        } else {
          System.out.print("|0|");
        }
      }
      System.out.println();
    }
  }

  public boolean openVak(int x, int y) {
    Veld veld = this.velden.get(x).get(y);
    if (veld.getStatus() == VakStatus.BOM) {
      System.out.println("Bom gevonden! Je bent verloren!");
      return true;
    } else if (veld.isOpen()) {
      System.out.println("Dit veld is al open");
    } else if (veld.getStatus() == VakStatus.AANGRENSENDE) {
      veld.setOpen(true);
    } else {
      openLegeVakken(x, y);
    }
    return false;
  }

  private void openLegeVakken(int x, int y) {
    if (x < 0 || x >= this.velden.size() || y < 0 || y >= this.velden.get(x).size()
        || this.velden.get(x).get(y).isOpen()) {
      return;
    }

    Veld veld = this.velden.get(x).get(y);
    veld.setOpen(true);

    if (veld.getStatus() == VakStatus.AANGRENSENDE) {
      return;
    }

    this.velden.get(x).get(y).setOpen(true);

    int[] extraX = { -1, -1, -1, 0, 0, 1, 1, 1 };
    int[] extraY = { -1, 0, 1, -1, 1, -1, 0, 1 };

    for (int i = 0; i < 8; i++) {
      openLegeVakken(x + extraX[i], y + extraY[i]);
    }
  }

  public boolean markeerBom(int x, int y) {
    if (this.geplaatsteBommen == this.bomPlaatsen.size()) {
      System.out.println("Alle bommen zijn al gemarkeerd");
      return false;
    }
    if (this.velden.get(x).get(y).isGemarkeerd()) {
      System.out.println("Dit veld is al gemarkeerd");
      return false;
    }

    if (this.velden.get(x).get(y).isOpen()) {
      System.out.println("Dit veld is al open");
      return false;
    }

    this.velden.get(x).get(y).setGemarkeerd(true);

    if (this.velden.get(x).get(y).getStatus() == VakStatus.BOM) {
      this.bommenOntdekt++;
    }
    if (this.bommenOntdekt == this.bomPlaatsen.size()) {
      System.out.println("Proficiat! Je hebt gewonnen!");
      return true;
    }

    this.geplaatsteBommen++;
    return false;
  }

  public void toonOpenBord() {
    for (List<Veld> rij : this.velden) {
      for (Veld veld : rij) {
        veld.setOpen(true);
      }
    }
    toonBord();
  }

  public void verwijderMarkering(int x, int y) {
    Veld veld = this.velden.get(x).get(y);
    if (!veld.isGemarkeerd()) {
      System.out.println("Dit veld is niet gemarkeerd");
    } else if (veld.isGemarkeerd()) {
      veld.setGemarkeerd(false);
      if (veld.getStatus() == VakStatus.BOM) {
        this.bommenOntdekt--;
      }
    }
  }
}
