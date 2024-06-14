package domain;

public class Veld {
  int CoorX;
  int CoorY;
  VakStatus status = VakStatus.LEEG;
  int aantalAangrenzendeBommen = 0;
  boolean open = false;
  boolean gemarkeerd = false;

  public Veld(int x, int y) {
    this.CoorX = x;
    this.CoorY = y;
  }

  public void setStatus(VakStatus status) {
    this.status = status;
  }

  public VakStatus getStatus() {
    return status;
  }

  public void setAantalAangrenzendeBommen(int aantal) {
    this.aantalAangrenzendeBommen = aantal;
  }

  public int getAantalAangrenzendeBommen() {
    return aantalAangrenzendeBommen;
  }

  public void setOpen(boolean open) {
    this.open = open;
  }

  public boolean isOpen() {
    return open;
  }

  public void setGemarkeerd(boolean gemarkeerd) {
    this.gemarkeerd = gemarkeerd;
  }

  public boolean isGemarkeerd() {
    return gemarkeerd;
  }
}
