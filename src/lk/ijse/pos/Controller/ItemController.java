package lk.ijse.pos.Controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.pos.DB.DBConnection;
import lk.ijse.pos.Model.Customer;
import lk.ijse.pos.Model.Item;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ItemController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tblItem.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("itemid"));
        tblItem.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("Name"));
        tblItem.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("Brand"));
        tblItem.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("QtyOnHand"));
        tblItem.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("BuyingPrice"));
        tblItem.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("SellingPrice"));
        tblItem.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("Category"));
        tblItem.getColumns().get(7).setCellValueFactory(new PropertyValueFactory<>("description"));

        load();
    }

    private void load() {
        try{
            String SQL = "SELECT * FROM item";
            ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery(SQL);
            ArrayList<Item> list = new ArrayList<>();
            while (rst.next()){
                Item item = new Item(
                        rst.getString("itemid"),
                        rst.getString("name"),
                        rst.getString("brand"),
                        rst.getInt("qtyOnHand"),
                        rst.getDouble("buyingPrice"),
                        rst.getDouble("sellingPrice"),
                        rst.getString("category"),
                        rst.getString("description")
                );
                list.add(item);
            }
            tblItem.setItems(FXCollections.observableArrayList(list));

        } catch (SQLException throwables) {
            new Alert(Alert.AlertType.ERROR,throwables.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING,"DRIVER NOT FOUND").show();
        }

    }

    private void clearall() {
        txtItemID.setText("");
        txtName.setText("");
        txtBrand.setText("");
        txtQtyOnHand.setText("");
        txtByingPrice.setText("");
        txtSellingPrice.setText("");
        txtCatagory.setText("");
        txtDescription.setText("");
    }

    @FXML
    private AnchorPane ItemPane;

    @FXML
    private TextField txtsearchbar;

    @FXML
    private TextField txtItemID;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtByingPrice;

    @FXML
    private TextField txtQtyOnHand;

    @FXML
    private TextField txtSellingPrice;

    @FXML
    private TableView<Item> tblItem;

    @FXML
    private TextField txtCatagory;

    @FXML
    private TextField txtBrand;

    @FXML
    private TextArea txtDescription;

    @FXML
    void clearFeilds(MouseEvent event) { clearall(); }

    @FXML
    void deleteItem(ActionEvent event) {
        try {
            String SQL = "DELETE FROM item WHERE itemid='"+txtItemID.getText()+"'";
            boolean isDeleted = DBConnection.getInstance().getConnection().createStatement().executeUpdate(SQL) > 0;
            if(isDeleted){
                load();
                new Alert(Alert.AlertType.CONFIRMATION,"Deleted Successfully...");
            }else{
                new Alert(Alert.AlertType.ERROR,"Record not Found");
            }

        } catch (SQLException throwables) {
            new Alert(Alert.AlertType.ERROR,throwables.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING,"DRIVER NOT FOUND").show();
        }
    }

    @FXML
    void saveItem(ActionEvent event) {
        try {
            String SQL = "INSERT INTO item VALUES(?,?,?,?,?,?,?,?)";
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(SQL);
            pstm.setObject(1,txtItemID.getText());
            pstm.setObject(2,txtName.getText());
            pstm.setObject(3,txtBrand.getText());
            pstm.setObject(4,Integer.parseInt(txtQtyOnHand.getText()));
            pstm.setObject(5,Double.parseDouble(txtByingPrice.getText()));
            pstm.setObject(6,Double.parseDouble(txtSellingPrice.getText()));
            pstm.setObject(7,txtCatagory.getText());
            pstm.setObject(8,txtDescription.getText());
            boolean isSaved = pstm.executeUpdate() > 0;

            if(isSaved){
                load();
                new Alert(Alert.AlertType.CONFIRMATION,"Item Saved Successfully..");
            }else{
                new Alert(Alert.AlertType.ERROR,"Fields are incorrect.");
            }

        } catch (SQLException throwables) {
            new Alert(Alert.AlertType.ERROR,throwables.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING,"DRIVER NOT FOUND").show();
        }
    }

    @FXML
    void searchItemID(ActionEvent event) {
        try{
            String SQL = "SELECT * FROM item WHERE itemid='"+txtItemID.getText()+"'";
            ResultSet set = DBConnection.getInstance().getConnection().createStatement().executeQuery(SQL);
            if(set.next()){
                txtItemID.setText(set.getString(1));
                txtName.setText(set.getString(2));
                txtBrand.setText(set.getString(3));
                txtQtyOnHand.setText(set.getString(4));
                txtByingPrice.setText(set.getString(5));
                txtSellingPrice.setText(set.getString(6));
                txtCatagory.setText(set.getString(7));
                txtDescription.setText(set.getString(8));
            }else {
                new Alert(Alert.AlertType.ERROR,"No record found under the given ID...").show();
            }

        } catch (SQLException throwables) {
            new Alert(Alert.AlertType.ERROR,throwables.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING,"DRIVER NOT FOUND").show();
        }
    }

    @FXML
    void tblClicked(MouseEvent event) {
        txtItemID.setText(tblItem.getSelectionModel().selectedItemProperty().get().getItemid());
        txtName.setText(tblItem.getSelectionModel().selectedItemProperty().get().getName());
        txtBrand.setText(tblItem.getSelectionModel().selectedItemProperty().get().getBrand());
        txtQtyOnHand.setText(String.valueOf(tblItem.getSelectionModel().selectedItemProperty().get().getQtyOnHand()));
        txtByingPrice.setText(String.valueOf(tblItem.getSelectionModel().selectedItemProperty().get().getBuyingPrice()));
        txtSellingPrice.setText(String.valueOf(tblItem.getSelectionModel().selectedItemProperty().get().getSellingPrice()));
        txtCatagory.setText(tblItem.getSelectionModel().selectedItemProperty().get().getCategory());
        txtDescription.setText(tblItem.getSelectionModel().selectedItemProperty().get().getDescription());
    }

    @FXML
    void updateItem(ActionEvent event) {
        try{
            String SQL = "UPDATE item SET name=?,brand=?, qtyOnHand=?, buyingPrice=?, sellingPrice=?, category=?, description=? WHERE itemid=? ";
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(SQL);
            pstm.setObject(1,txtName.getText());
            pstm.setObject(2,txtBrand.getText());
            pstm.setObject(3,txtQtyOnHand.getText());
            pstm.setObject(4,txtByingPrice.getText());
            pstm.setObject(5,txtSellingPrice.getText());
            pstm.setObject(6,txtCatagory.getText());
            pstm.setObject(7,txtDescription.getText());
            pstm.setObject(8,txtItemID.getText());
            boolean isUpdated = pstm.executeUpdate() > 0;

            if(isUpdated){
                load();
                new Alert(Alert.AlertType.CONFIRMATION,"Updated Successfully").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"couldn't update. try again...").show();
            }

        } catch (SQLException throwables) {
            new Alert(Alert.AlertType.ERROR,throwables.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING,"DRIVER NOT FOUND").show();
        }
    }

    public void SearchItemBar(KeyEvent keyEvent) {
        tblItem.getItems().clear();

        String text = "%"+txtsearchbar.getText()+"%";

        try {
            String SQL = "SELECT * FROM item WHERE itemid LIKE ? || name LIKE ? || brand LIKE ? || qtyOnHand LIKE ? || buyingPrice LIKE ? || sellingPrice LIKE ? || category LIKE ? || description LIKE ?";
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(SQL);
            pstm.setObject(1, text);
            pstm.setObject(2, text);
            pstm.setObject(3, text);
            pstm.setObject(4, text);
            pstm.setObject(5, text);
            pstm.setObject(6, text);
            pstm.setObject(7, text);
            pstm.setObject(8, text);
            ResultSet rst = pstm.executeQuery();
            ArrayList<Item> list = new ArrayList<>();

            while(rst.next()){
                Item item= new Item(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getInt(4),
                        rst.getDouble(5),
                        rst.getDouble(6),
                        rst.getString(7),
                        rst.getString(8)
                );
                list.add(item);
            }
            tblItem.setItems(FXCollections.observableArrayList(list));

        } catch (SQLException throwables) {
            new Alert(Alert.AlertType.ERROR,throwables.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING,"DRIVER NOT FOUND").show();
        }
    }
}
