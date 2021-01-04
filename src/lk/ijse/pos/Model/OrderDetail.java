package lk.ijse.pos.Model;

public class OrderDetail {
    private String orderid;
    private String itemid;
    private String itemname;
    private double unitprice;
    private int qty;

    public OrderDetail() {
    }

    public OrderDetail(String orderid, String itemid, double unitprice, int qty) {
        this.orderid = orderid;
        this.itemid = itemid;
        this.unitprice = unitprice;
        this.qty = qty;
    }

    public OrderDetail(String orderid, String itemid, String itemname, double unitprice, int qty) {
        this.orderid = orderid;
        this.itemid = itemid;
        this.itemname = itemname;
        this.unitprice = unitprice;
        this.qty = qty;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
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

    @Override
    public String toString() {
        return "OrderDetail{" +
                "orderid='" + orderid + '\'' +
                ", itemid='" + itemid + '\'' +
                ", itemname='" + itemname + '\'' +
                ", unitprice=" + unitprice +
                ", qty=" + qty +
                '}';
    }
}
