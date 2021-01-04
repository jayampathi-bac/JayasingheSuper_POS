package lk.ijse.pos.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.pos.DB.DBConnection;
import lk.ijse.pos.Model.Customer;

import java.awt.event.KeyEvent;
import java.awt.image.DataBuffer;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tblCustomer.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("customerid"));
        tblCustomer.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("firstname"));
        tblCustomer.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("lastname"));
        tblCustomer.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("contact"));
        tblCustomer.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblCustomer.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("city"));

        load();
        loadCustomerID();

    }

    private void loadCustomerID() {
        try{
            String SQL ="SELECT customerid FROM customer ORDER BY customerid DESC limit 1";
            ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery(SQL);
            if(rst.next()){
                String id =rst.getString(1);
                String[] temparr = id.split("C");
                int temp = Integer.parseInt(temparr[1]);
                temp+=1;

                if(temp<10){
                    txtCusID.setText("C00"+temp);
                }else if(temp<100){
                    txtCusID.setText("C0"+temp);
                }else{
                    txtCusID.setText("C"+temp);
                }

            }else{
                txtCusID.setText("C001");
            }
        } catch (ClassNotFoundException ex) {
            new Alert(Alert.AlertType.WARNING,"DRIVER NOT FOUND").show();
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR,ex.getMessage()).show();
        }
    }

    void load(){
        try {
            String sql= "select * from customer";
            ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery(sql);

            List<Customer> list= new ArrayList<>();
            while(rst.next()){
                Customer customer= new Customer(
                        rst.getString("customerid"),
                        rst.getString("firstname"),
                        rst.getString("lastname"),
                        rst.getString("contact"),
                        rst.getString("address"),
                        rst.getString("city")
                );
                list.add(customer);
            }
            tblCustomer.setItems(FXCollections.observableArrayList(list));

        } catch (ClassNotFoundException ex) {
            new Alert(Alert.AlertType.WARNING,"DRIVER NOT FOUND").show();
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR,ex.getMessage()).show();
        }
    }

    @FXML
    private AnchorPane CustomerPane;


    @FXML
    private TextField txtCusID;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtCity;

    @FXML
    private TextField txtSearchCustomer;


    @FXML
    private TextArea txtAddress;

    @FXML
    private TableView<Customer> tblCustomer;

    private ObservableList<ArrayList<Customer>> customersearchlist = FXCollections.observableArrayList();

    @FXML
    void saveCustomer(MouseEvent event) {
        try {

            if(isNull()){
                new Alert(Alert.AlertType.ERROR,"Empty Feilds...").show();
                return;
            }

            String sql= "insert into customer values(?,?,?,?,?,?)";
            PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
            stm.setString(1,txtCusID.getText());
            stm.setString(2,txtFirstName.getText());
            stm.setString(3,txtLastName.getText());
            stm.setString(4,txtContact.getText());
            stm.setString(5,txtAddress.getText());
            stm.setString(6,txtCity.getText());


            boolean isSaved =stm.executeUpdate()>0;
            if (isSaved) {
                load();
                new Alert(Alert.AlertType.CONFIRMATION, "Added").show();
            } else {
                new Alert(Alert.AlertType.CONFIRMATION, "Failed").show();
            }
        } catch (ClassNotFoundException ex) {
            new Alert(Alert.AlertType.WARNING,"DRIVER NOT FOUND").show();
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR,ex.getMessage()).show();
        }
    }

    private boolean isNull(){
        return txtCusID.getText().equalsIgnoreCase("") || txtFirstName.getText().equalsIgnoreCase("") || txtLastName.getText().equalsIgnoreCase("") || txtContact.getText().equalsIgnoreCase("") || txtCity.getText().equalsIgnoreCase("") || txtAddress.getText().equalsIgnoreCase("") ;
    }

    @FXML
    void tblClicked(MouseEvent event) {
        txtCusID.setText(tblCustomer.getSelectionModel().selectedItemProperty().get().getCustomerid());
        txtFirstName.setText(tblCustomer.getSelectionModel().selectedItemProperty().get().getFirstname());
        txtLastName.setText(tblCustomer.getSelectionModel().selectedItemProperty().get().getLastname());
        txtContact.setText(tblCustomer.getSelectionModel().selectedItemProperty().get().getContact());
        txtAddress.setText(tblCustomer.getSelectionModel().selectedItemProperty().get().getAddress());
        txtCity.setText(tblCustomer.getSelectionModel().selectedItemProperty().get().getCity());
    }

    private void clerll() {
        txtCusID.setText("");
        txtFirstName.setText("");
        txtLastName.setText("");
        txtContact.setText("");
        txtCity.setText("");
        txtAddress.setText("");
    }
    @FXML
    void clearFeilds(MouseEvent event) {
        clerll();
    }

    @FXML
    void deleteCustomer(MouseEvent event) {
        String SQL = "DELETE FROM customer WHERE customerid = '"+txtCusID.getText()+"'";
        try {
            boolean isdeleted =DBConnection.getInstance().getConnection().createStatement().executeUpdate(SQL)>0;
            if(isdeleted){
                load();
                new Alert(Alert.AlertType.CONFIRMATION,"customer has been deleted").show();
            }else{
                new Alert(Alert.AlertType.ERROR,"no customer found").show();
            }
        } catch (SQLException throwables) {
            new Alert(Alert.AlertType.ERROR,throwables.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING,"DRIVER NOT FOUND").show();
        }
    }

    @FXML
    void searchCusID(ActionEvent event) {
        try {
            String SQL = "SELECT * FROM customer WHERE customerid='" + txtCusID.getText() + "'";
            ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery(SQL);
            if(rst.next()){
                txtCusID.setText(rst.getString(1));
                txtFirstName.setText(rst.getString(2));
                txtLastName.setText(rst.getString(3));
                txtContact.setText(rst.getString(4));
                txtAddress.setText(rst.getString(5));
                txtCity.setText(rst.getString(6));
            }else{
                new Alert(Alert.AlertType.WARNING,"no customer found").show();
            }
        } catch (SQLException throwables) {
            new Alert(Alert.AlertType.ERROR,throwables.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING,"DRIVER NOT FOUND").show();
        }
    }

    @FXML
    void updateCustomer(MouseEvent event) {
        try {
            String SQL = "UPDATE customer SET firstname=?, lastname=?, contact=?, address=?, city=? WHERE customerid=?";
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(SQL);
            pstm.setString(1,txtFirstName.getText());
            pstm.setString(2,txtLastName.getText());
            pstm.setString(3,txtContact.getText());
            pstm.setString(4,txtAddress.getText());
            pstm.setString(5,txtCity.getText());
            pstm.setString(6,txtCusID.getText());
            boolean isUpdated = pstm.executeUpdate() > 0;

            if(isUpdated){
                load();
                new Alert(Alert.AlertType.CONFIRMATION,"updated successfully").show();
            }else{
                new Alert(Alert.AlertType.ERROR,"cannot update. try again..").show();
            }
        } catch (SQLException throwables) {
            new Alert(Alert.AlertType.ERROR,throwables.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING,"DRIVER NOT FOUND").show();
        }
    }


    public void searchCus(javafx.scene.input.KeyEvent keyEvent) {
        tblCustomer.getItems().clear();

        String text = "%"+txtSearchCustomer.getText()+"%";

        try {
            String SQL = "SELECT * FROM customer WHERE customerid LIKE ? || firstname LIKE ? || lastname LIKE ? || contact LIKE ? || address LIKE ? || city LIKE ? ";
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(SQL);
            pstm.setObject(1, text);
            pstm.setObject(2, text);
            pstm.setObject(3, text);
            pstm.setObject(4, text);
            pstm.setObject(5, text);
            pstm.setObject(6, text);
            ResultSet rst = pstm.executeQuery();
            ArrayList<Customer> list = new ArrayList<>();

            while(rst.next()){
                Customer customer= new Customer(
                        rst.getString("customerid"),
                        rst.getString("firstname"),
                        rst.getString("lastname"),
                        rst.getString("contact"),
                        rst.getString("address"),
                        rst.getString("city")
                );
                list.add(customer);
            }
            tblCustomer.setItems(FXCollections.observableArrayList(list));

        } catch (SQLException throwables) {
            new Alert(Alert.AlertType.ERROR,throwables.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING,"DRIVER NOT FOUND").show();
        }
    }

}
