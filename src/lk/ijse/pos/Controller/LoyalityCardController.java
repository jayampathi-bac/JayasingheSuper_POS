package lk.ijse.pos.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.pos.DB.DBConnection;
import lk.ijse.pos.Model.Customer;
import lk.ijse.pos.Model.LoyalityCard;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LoyalityCardController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tblLoyality.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("lcid"));
        tblLoyality.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("customerid"));
        tblLoyality.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("firstName"));
        tblLoyality.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("lastName"));
        tblLoyality.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("points"));
        tblLoyality.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("remarks"));
        load();
        loadCustomerID();
    }

    void load(){
        try {
            String sql= "select * from loyality_card";
            ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery(sql);

            List<LoyalityCard> list= new ArrayList<>();
            while(rst.next()){
                LoyalityCard lc = new LoyalityCard(
                        rst.getString("lcid"),
                        rst.getString("customerid"),
                        rst.getString("firstName"),
                        rst.getString("lastName"),
                        rst.getDouble("points"),
                        rst.getString("remarks")
                );
                list.add(lc);
            }
            tblLoyality.setItems(FXCollections.observableArrayList(list));

        } catch (ClassNotFoundException ex) {
            new Alert(Alert.AlertType.WARNING,"DRIVER NOT FOUND").show();
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR,ex.getMessage()).show();
        }
    }

    @FXML
    private TextField txtsearchbar;

    @FXML
    private TextField txtlcid;

    @FXML
    private TextField txtfname;

    @FXML
    private TextField txtpoints;

    @FXML
    private TextField txtlname;

    @FXML
    private TextArea txtRemarks;

    @FXML
    private TableView<LoyalityCard> tblLoyality;

    @FXML
    private ComboBox<String> cmbCustomer;


    @FXML
    void clearFeilds(MouseEvent event) {
        clearll();
    }
    private void clearll() {
        txtlcid.setText("");
        cmbCustomer.setValue("customerID");
        txtfname.setText("");
        txtlname.setText("");
        txtpoints.setText("");
        txtRemarks.setText("");
    }

    @FXML
    void deleteCustomer(MouseEvent event) {
        String SQL = "DELETE FROM loyality_card WHERE lcid = '"+txtlcid.getText()+"'";
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
    void saveCustomer(MouseEvent event) {
        try {
            String sql= "insert into loyality_card values(?,?,?,?,?,?)";
            PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
            stm.setString(1,txtlcid.getText());
            stm.setString(2,cmbCustomer.getValue());
            stm.setString(3,txtfname.getText());
            stm.setString(4,txtlname.getText());
            stm.setString(5,txtpoints.getText());
            stm.setString(6,txtRemarks.getText());
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

    @FXML
    void tblClicked(MouseEvent event) {
        txtlcid.setText(tblLoyality.getSelectionModel().selectedItemProperty().get().getLcid());
        cmbCustomer.setValue(tblLoyality.getSelectionModel().selectedItemProperty().get().getCustomerid());
        txtfname.setText(tblLoyality.getSelectionModel().selectedItemProperty().get().getFirstName());
        txtlname.setText(tblLoyality.getSelectionModel().selectedItemProperty().get().getLastName());
        txtpoints.setText(tblLoyality.getSelectionModel().selectedItemProperty().get().getPoints().toString());
        txtRemarks.setText(tblLoyality.getSelectionModel().selectedItemProperty().get().getRemarks());
    }

    @FXML
    void updateCustomer(MouseEvent event) {
        try {
            String SQL = "UPDATE loyality_card SET customerid=?, firstName=?, lastName=?, points=?, remarks=? WHERE lcid=?";
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(SQL);
            pstm.setString(1,cmbCustomer.getValue());
            pstm.setString(2,txtfname.getText());
            pstm.setString(3,txtlname.getText());
            pstm.setString(4,txtpoints.getText());
            pstm.setString(5,txtRemarks.getText());
            pstm.setString(6,txtlcid.getText());
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

    public void loadCustomerID(){
        try{
            String sql="SELECT customerid FROM customer";
            ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery(sql);
            ObservableList<String> list = FXCollections.observableArrayList();
            while (rst.next()) {
                list.add(rst.getString(1));
            }
            cmbCustomer.setItems(list);

        } catch (SQLException throwables) {
            new Alert(Alert.AlertType.ERROR,throwables.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING,"DRIVER NOT FOUND").show();
        }
    }

    public void selectCustomer(ActionEvent actionEvent) {
        String text = cmbCustomer.getValue();
        //System.out.println(text);
        try{
            String SQL = "SELECT * FROM customer WHERE customerid='"+text+"'";
            ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery(SQL);
            if(rst.next()){
                txtfname.setText(rst.getString(2));
                txtlname.setText(rst.getString(3));
            }

        } catch (SQLException throwables) {
            new Alert(Alert.AlertType.ERROR,throwables.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING,"DRIVER NOT FOUND").show();
        }
    }
}
