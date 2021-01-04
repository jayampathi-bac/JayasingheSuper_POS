package lk.ijse.pos.Model;

public class Payment {
    private String paymentid;
    private String orderid;
    private double grossPrice;
    private double points;
    private double totalDiscount;
    private double netPrice;
    private String lcid;

    public Payment() {
    }

    public Payment(String paymentid, String orderid, double grossPrice, double points, double totalDiscount, double netPrice) {
        this.paymentid = paymentid;
        this.orderid = orderid;
        this.grossPrice = grossPrice;
        this.points = points;
        this.totalDiscount = totalDiscount;
        this.netPrice = netPrice;
    }

    public Payment(String paymentid, String orderid, double grossPrice, double points, double totalDiscount, double netPrice, String lcid) {
        this.paymentid = paymentid;
        this.orderid = orderid;
        this.grossPrice = grossPrice;
        this.points = points;
        this.totalDiscount = totalDiscount;
        this.netPrice = netPrice;
        this.lcid = lcid;
    }

    public String getPaymentid() {
        return paymentid;
    }

    public void setPaymentid(String paymentid) {
        this.paymentid = paymentid;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public double getGrossPrice() {
        return grossPrice;
    }

    public void setGrossPrice(double grossPrice) {
        this.grossPrice = grossPrice;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public double getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public double getNetPrice() {
        return netPrice;
    }

    public void setNetPrice(double netPrice) {
        this.netPrice = netPrice;
    }

    public String getLcid() { return lcid; }

    public void setLcid(String lcid) { this.lcid = lcid; }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentid='" + paymentid + '\'' +
                ", orderid='" + orderid + '\'' +
                ", grossPrice=" + grossPrice +
                ", points=" + points +
                ", totalDiscount=" + totalDiscount +
                ", netPrice=" + netPrice +
                ", lcid='" + lcid + '\'' +
                '}';
    }
}
