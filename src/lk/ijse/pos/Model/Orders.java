package lk.ijse.pos.Model;

import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Orders {
    private String orderid;
    private String customerid;
    private String customerName;
    private String date;
    private String time;
    private ObservableList<OrderDetail> orderDetail;
    private Payment payment;

    public Orders(String orderid, String customerid, String customerName, String date, String time, ObservableList<OrderDetail> orderDetail) {
        this.orderid = orderid;
        this.customerid = customerid;
        this.customerName = customerName;
        this.date = date;
        this.time = time;
        this.orderDetail = orderDetail;
    }

    public Orders(String orderid, String customerid, String customerName, String date, String time, ObservableList<OrderDetail> orderDetail, Payment payment) {
        this.orderid = orderid;
        this.customerid = customerid;
        this.customerName = customerName;
        this.date = date;
        this.time = time;
        this.orderDetail = orderDetail;
        this.payment = payment;
    }

    public Orders(String orderid, String customerid, String date, String time, ObservableList<OrderDetail> orderDetail, Payment payment) {
        this.orderid = orderid;
        this.customerid = customerid;
        this.date = date;
        this.time = time;
        this.orderDetail = orderDetail;
        this.payment = payment;
    }

    public Orders(String orderid, String customerid, String date, String time, ObservableList<OrderDetail> orderDetail) {
        this.orderid = orderid;
        this.customerid = customerid;
        this.date = date;
        this.time = time;
        this.orderDetail = orderDetail;
    }

    public Orders(String orderid, String customerid, String customerName, String date, String time) {
        this.orderid = orderid;
        this.customerid = customerid;
        this.customerName = customerName;
        this.date = date;
        this.time = time;
    }

    public Orders() {
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ObservableList<OrderDetail> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(ObservableList<OrderDetail> orderDetail) {
        this.orderDetail = orderDetail;
    }

    public Payment getPayment() { return payment; }

    public void setPayment(Payment payment) { this.payment = payment; }

    @Override
    public String toString() {
        return "Orders{" +
                "orderid='" + orderid + '\'' +
                ", customerid='" + customerid + '\'' +
                ", customerName='" + customerName + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", orderDetail=" + orderDetail +
                '}';
    }
}
