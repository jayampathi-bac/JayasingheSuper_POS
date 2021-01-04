package lk.ijse.pos.Model;

public class LoyalityCard {
    private String lcid;
    private String customerid;
    private String firstName;
    private String lastName;
    private Double points;
    private String remarks;

    public LoyalityCard() {

    }

    public LoyalityCard(String lcid, String customerid, String firstName, String lastName, Double points, String remarks) {
        this.lcid = lcid;
        this.customerid = customerid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.points = points;
        this.remarks = remarks;
    }

    public String getLcid() {
        return lcid;
    }

    public void setLcid(String lcid) {
        this.lcid = lcid;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "LoyalityCard{" +
                "lcid='" + lcid + '\'' +
                ", customerid='" + customerid + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", points=" + points +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
