package lk.ijse.pos.Model;

public class BatchDetail {
    private String batchid;
    private String itemid;
    private String itemName;
    private int qty;
    private double buyingPrice;
    private double sellingPrice;

    public BatchDetail() {
    }

    public BatchDetail(String batchid, String itemid, String itemName, int qty, double buyingPrice, double sellingPrice) {
        this.batchid = batchid;
        this.itemid = itemid;
        this.itemName = itemName;
        this.qty = qty;
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
    }

    public String getBatchid() {
        return batchid;
    }

    public void setBatchid(String batchid) {
        this.batchid = batchid;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
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

    @Override
    public String toString() {
        return "BatchDetail{" +
                "batchid='" + batchid + '\'' +
                ", itemid='" + itemid + '\'' +
                ", itemName='" + itemName + '\'' +
                ", qty=" + qty +
                ", buyingPrice=" + buyingPrice +
                ", sellingPrice=" + sellingPrice +
                '}';
    }
}
