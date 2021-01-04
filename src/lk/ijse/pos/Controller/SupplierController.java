package lk.ijse.pos.Controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.pos.DB.DBConnection;
import lk.ijse.pos.Model.Item;
import lk.ijse.pos.Model.Supplier;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SupplierController implements Initializable {


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tblSupplier.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("supplierID"));
        tblSupplier.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("firstName"));
        tblSupplier.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("lastName"));
        tblSupplier.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("company"));
        tblSupplier.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("contact"));
        tblSupplier.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("city"));
        tblSupplier.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("address"));
        load();
    }

    private void load() {
        try{
            ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM supplier");
            ArrayList<Supplier> list = new ArrayList<>();
            while (rst.next()){
                Supplier supplier = new Supplier(
                        rst.getString("supplierID"),
                        rst.getString("firstName"),
                        rst.getString("lastName"),
                        rst.getString("company"),
                        rst.getInt("contact"),
                        rst.getString("city"),
                        rst.getString("address")
                );
                list.add(supplier);
            }
            tblSupplier.setItems(FXCollections.observableArrayList(list));

        } catch (SQLException throwables) {
            new Alert(Alert.AlertType.ERROR,throwables.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING,"DRIVER NOT FOUND").show();
        }
    }

    @FXML
    private AnchorPane supplierPane;

    @FXML
    private TextField txtSearchBar;

    @FXML
    private TextField txtsupid;

    @FXML
    private TextField txtfname;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtlname;

    @FXML
    private TextField txtCity;

    @FXML
    private TextArea txtAddress;

    @FXML
    private TextField txtcompany;

    @FXML
    private TableView<Supplier> tblSupplier;

    @FXML
    void clearFeilds(MouseEvent event) {
        cleaAll();
    }

    private void cleaAll() {
        txtsupid.setText("");
        txtfname.setText("");
        txtlname.setText("");
        txtcompany.setText("");
        txtContact.setText("");
        txtCity.setText("");
        txtAddress.setText("");
    }

    @FXML
    void saveSupplier(MouseEvent event) {
        try{
            String SQL = "INSERT INTO supplier VALUES(?,?,?,?,?,?,?)";
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(SQL);
            pstm.setObject(1,txtsupid.getText());
            pstm.setObject(2,txtfname.getText());
            pstm.setObject(3,txtlname.getText());
            pstm.setObject(4,txtcompany.getText());
            pstm.setObject(5,Integer.parseInt(txtContact.getText()));
            pstm.setObject(6,txtCity.getText());
            pstm.setObject(7,txtAddress.getText());
            boolean isSaved = pstm.executeUpdate() > 0;

            if(isSaved){
                load();
                new Alert(Alert.AlertType.CONFIRMATION,"supplier is added...").show();
            }else{
                new Alert(Alert.AlertType.ERROR,"try again..").show();
            }
        } catch (SQLException throwables) {
            new Alert(Alert.AlertType.ERROR,throwables.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING,"DRIVER NOT FOUND").show();
        }
    }

    @FXML
    void udpateSupplier(MouseEvent event) {
        try{
            String SQL = "UPDATE supplier SET firstname=?,lastname=?,company=?,contact=?,city=?,address=? WHERE supplierID=?";
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(SQL);
            pstm.setObject(1,txtfname.getText());
            pstm.setObject(2,txtlname.getText());
            pstm.setObject(3,txtcompany.getText());
            pstm.setObject(4,txtContact.getText());
            pstm.setObject(5,txtCity.getText());
            pstm.setObject(6,txtAddress.getText());
            pstm.setObject(7,txtsupid.getText());
            boolean isUpdated = pstm.executeUpdate() > 0;

            if(isUpdated){
                load();
                new Alert(Alert.AlertType.CONFIRMATION,"Updated Successfully...").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"try again...").show();
            }
        } catch (SQLException throwables) {
            new Alert(Alert.AlertType.ERROR,throwables.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING,"DRIVER NOT FOUND").show();
        }
    }

    @FXML
    void deleteSupplier(MouseEvent event) {
        try{
            String SQL = "DELETE FROM supplier WHERE supplierID='"+txtsupid.getText()+"'";
            boolean isDeleted = DBConnection.getInstance().getConnection().createStatement().executeUpdate(SQL) > 0;

            if(isDeleted){
                load();
                new Alert(Alert.AlertType.CONFIRMATION,"Deleted Successfully...").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"try again...").show();
            }
        } catch (SQLException throwables) {
            new Alert(Alert.AlertType.ERROR,throwables.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING,"DRIVER NOT FOUND").show();
        }
    }

    @FXML
    void tblClicked(MouseEvent event) {
        txtsupid.setText(tblSupplier.getSelectionModel().selectedItemProperty().get().getSupplierID());
        txtfname.setText(tblSupplier.getSelectionModel().selectedItemProperty().get().getFirstName());
        txtlname.setText(tblSupplier.getSelectionModel().selectedItemProperty().get().getLastName());
        txtcompany.setText(tblSupplier.getSelectionModel().selectedItemProperty().get().getCompany());
        txtContact.setText(String.valueOf(tblSupplier.getSelectionModel().selectedItemProperty().get().getContact()));
        txtCity.setText(tblSupplier.getSelectionModel().selectedItemProperty().get().getCity());
        txtAddress.setText(tblSupplier.getSelectionModel().selectedItemProperty().get().getAddress());
    }


    public void searchSupplier(KeyEvent keyEvent) {
        tblSupplier.getItems().clear();

        String text = "%"+txtSearchBar.getText()+"%";

        try {
            String SQL = "SELECT * FROM supplier WHERE supplierid LIKE ? || firstname LIKE ? || lastname LIKE ? || company LIKE ? || contact LIKE ? || city LIKE ? || address LIKE ?";
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(SQL);
            pstm.setObject(1, text);
            pstm.setObject(2, text);
            pstm.setObject(3, text);
            pstm.setObject(4, text);
            pstm.setObject(5, text);
            pstm.setObject(6, text);
            pstm.setObject(7, text);
            ResultSet rst = pstm.executeQuery();
            ArrayList<Supplier> list = new ArrayList<>();

            while(rst.next()){
                Supplier supplier = new Supplier(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getString(4),
                        rst.getInt(5),
                        rst.getString(6),
                        rst.getString(7)
                );
                list.add(supplier);
            }
            tblSupplier.setItems(FXCollections.observableArrayList(list));

        } catch (SQLException throwables) {
            new Alert(Alert.AlertType.ERROR,throwables.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING,"DRIVER NOT FOUND").show();
        }
    }
}
