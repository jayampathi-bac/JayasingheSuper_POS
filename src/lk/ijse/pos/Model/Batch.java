package lk.ijse.pos.Model;

public class Batch {
    private String batchid;
    private String supplierid ;
    private String supplierName;
    private String company;
    private double totalCost;
    private String date;

    public Batch(String batchid, String supplierid, String supplierName, String company, double totalCost, String date) {
        this.batchid = batchid;
        this.supplierid = supplierid;
        this.supplierName = supplierName;
        this.company = company;
        this.totalCost = totalCost;
        this.date = date;
    }

    public Batch() {
    }

    public String getBatchid() {
        return batchid;
    }

    public void setBatchid(String batchid) {
        this.batchid = batchid;
    }

    public String getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}

