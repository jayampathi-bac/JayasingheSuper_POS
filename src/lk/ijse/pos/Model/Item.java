package lk.ijse.pos.Model;

public class Item {
    private String itemid;
    private String name;
    private String brand;
    private int qtyOnHand;
    private double buyingPrice;
    private double sellingPrice;
    private String category;
    private String description;

    public Item() {
    }

    public Item(String itemid, String name, String brand, int qtyOnHand, double buyingPrice, double sellingPrice, String category, String description) {
        this.itemid = itemid;
        this.name = name;
        this.brand = brand;
        this.qtyOnHand = qtyOnHand;
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
        this.category = category;
        this.description = description;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public double getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(double buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemid='" + itemid + '\'' +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", qtyOnHand=" + qtyOnHand +
                ", buyingPrice=" + buyingPrice +
                ", sellingPrice=" + sellingPrice +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
