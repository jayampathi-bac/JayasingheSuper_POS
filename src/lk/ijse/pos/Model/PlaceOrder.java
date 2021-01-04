package lk.ijse.pos.Model;

public class PlaceOrder {
    private String itemcode;
    private String itemname;
    private double unitprice;
    private int qty;
    private double total;

    public PlaceOrder() {
    }

    public PlaceOrder(String itemcode, String itemname, double unitprice, int qty, double total) {
        this.itemcode = itemcode;
        this.itemname = itemname;
        this.unitprice = unitprice;
        this.qty = qty;
        this.total = total;
    }

    public String getItemcode() {
        return itemcode;
    }

    public void setItemcode(String itemcode) {
        this.itemcode = itemcode;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public double getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(double unitprice) {
        this.unitprice = unitprice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
