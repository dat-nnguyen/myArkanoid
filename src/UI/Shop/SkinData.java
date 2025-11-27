package UI.Shop;

public class SkinData {
  private final int id;
  private final String imagePath;
  private final int price;
  private boolean isOwned;

  public SkinData(int id, String imagePath, int price, boolean isOwned) {
    this.id = id;
    this.imagePath = imagePath;
    this.price = price;
    this.isOwned = isOwned;
  }

  public int getId() {
    return id;
  }

  public String getImagePath() {
    return imagePath;
  }

  public int getPrice() {
    return price;
  }

  public boolean isOwned() {
    return isOwned;
  }

  public void setOwned(boolean owned) {
    isOwned = owned;
  }
}
