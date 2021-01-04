package lk.ijse.pos.Model;

public class Supplier {
    private String supplierID;
    private String firstName;
    private String lastName;
    private String company;
    private int contact;
    private String city;
    private String address;

    public Supplier() {

    }

    public Supplier(String supplierID, String firstName, String lastName, String company, int contact, String city, String address) {
        this.supplierID = supplierID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.company = company;
        this.contact = contact;
        this.city = city;
        this.address = address;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getContact() {
        return contact;
    }

    public void setContact(int contact) {
        this.contact = contact;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "supplierID='" + supplierID + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", company='" + company + '\'' +
                ", contact=" + contact +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
