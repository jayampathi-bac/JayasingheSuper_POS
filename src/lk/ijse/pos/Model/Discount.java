package lk.ijse.pos.Model;

import java.util.Date;

public class Discount {
    private String sdid;
    private Date startDate;
    private Date endDate;
    private double lowerLimit;
    private double upperlimit;
    private double amount;
    private double percentage;
    private String remarks;


    public Discount() {
    }

    public Discount(String sdid, Date startDate, Date endDate, double lowerLimit, double upperlimit, double amount, double percentage, String remarks) {
        this.sdid = sdid;
        this.startDate = startDate;
        this.endDate = endDate;
        this.lowerLimit = lowerLimit;
        this.upperlimit = upperlimit;
        this.amount = amount;
        this.percentage = percentage;
        this.remarks = remarks;
    }

    public Discount(String sdid, Date startDate, Date endDate, double lowerLimit, double upperlimit, double amount, double percentage) {
        this.sdid = sdid;
        this.startDate = startDate;
        this.endDate = endDate;
        this.lowerLimit = lowerLimit;
        this.upperlimit = upperlimit;
        this.amount = amount;
        this.percentage = percentage;
    }

    public String getSdid() {
        return sdid;
    }

    public void setSdid(String sdid) {
        this.sdid = sdid;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(double lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public double getUpperlimit() {
        return upperlimit;
    }

    public void setUpperlimit(double upperlimit) {
        this.upperlimit = upperlimit;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "Discount{" +
                "sdid='" + sdid + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", lowerLimit=" + lowerLimit +
                ", upperlimit=" + upperlimit +
                ", amount=" + amount +
                ", percentage=" + percentage +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
