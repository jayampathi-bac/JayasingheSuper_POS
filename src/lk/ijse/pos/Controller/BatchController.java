package lk.ijse.pos.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.pos.DB.DBConnection;
import lk.ijse.pos.Model.Batch;
import lk.ijse.pos.Model.BatchDetail;

import java.net.URL;
import java.sql.ClientInfoStatus;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class BatchController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadtblBatch();
        loadtblBatchDetail();
        loadDate();
    }

    private void loadtblBatchDetail() {
        tblBatchDetail.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("batchid"));
        tblBatchDetail.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("itemid"));
        tblBatchDetail.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("itemName"));
        tblBatchDetail.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblBatchDetail.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("buyingPrice"));
        tblBatchDetail.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));

        loadBatchID();
        loadItemID();
        try {
            String sql= "select * from batchDetail";
            ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery(sql);

            List<BatchDetail> list2= new ArrayList<>();
            //ObservableList<BatchDetail> list2 = FXCollections.observableArrayList();
            while(rst.next()){
                BatchDetail batchDetail = new BatchDetail(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getInt(4),
                        rst.getDouble(5),
                        rst.getDouble(6)
                );
                list2.add(batchDetail);
            }

            tblBatchDetail.setItems(FXCollections.observableArrayList(list2));
            //tblBatchDetail.getItems().addAll(list2);

        } catch (ClassNotFoundException ex) {
            new Alert(Alert.AlertType.WARNING,"DRIVER NOT FOUND").show();
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR,ex.getMessage()).show();
        }

    }

    private void loadtblBatch() {
        tblBatch.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("batchid"));
        tblBatch.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("supplierid"));
        tblBatch.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        tblBatch.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("company"));
        tblBatch.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        tblBatch.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("date"));

        loadSupplierID();

        try {
            ObservableList<Batch> list = FXCollections.observableArrayList();
            String sql= "select * from batch";
            ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery(sql);
            while (rst.next()){
                list.add(new Batch(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getString(4),
                        rst.getDouble(5),
                        rst.getString(6)
                ));
            }
            tblBatch.setItems(list);
        } catch (ClassNotFoundException ex) {
            new Alert(Alert.AlertType.WARNING,"DRIVER NOT FOUND").show();
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR,ex.getMessage()).show();
        }

    }

    public void loadSupplierID(){
        try{
            String sql="SELECT supplierID FROM supplier";
            ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery(sql);
            ObservableList<String> list = FXCollections.observableArrayList();
            while (rst.next()) {
                list.add(rst.getString(1));
            }
            cmbSupplierID.setItems(list);

        } catch (SQLException throwables) {
            new Alert(Alert.AlertType.ERROR,throwables.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING,"DRIVER NOT FOUND").show();
        }
    }

    public void loadBatchID(){
        try{
            String sql="SELECT batchid FROM batch";
            ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery(sql);
            ObservableList<String> list = FXCollections.observableArrayList();
            while (rst.next()) {
                list.add(rst.getString(1));
            }
            cmbBatchID.setItems(list);

        } catch (SQLException throwables) {
            new Alert(Alert.AlertType.ERROR,throwables.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING,"DRIVER NOT FOUND").show();
        }
    }

    public void loadItemID(){
        try{
            String sql="SELECT itemid FROM item";
            ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery(sql);
            ObservableList<String> list = FXCollections.observableArrayList();
            while (rst.next()) {
                list.add(rst.getString(1));
            }
            cmbItemID.setItems(list);

        } catch (SQLException throwables) {
            new Alert(Alert.AlertType.ERROR,throwables.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING,"DRIVER NOT FOUND").show();
        }
    }

    @FXML
    private AnchorPane BatchPane;

    @FXML
    private TextField txtBatchID;

    @FXML
    private TextField txtBuyingPrice;

    @FXML
    private TextField txtdate;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtSelllingPrice;

    @FXML
    private TableView<BatchDetail> tblBatchDetail;

    @FXML
    private TextField txtCost;

    @FXML
    private TextField txtSupplierName;

    @FXML
    private TableView<Batch> tblBatch;

    @FXML
    private TextField txtsearchbar;

    @FXML
    private ComboBox<String> cmbSupplierID;

    @FXML
    private ComboBox<String> cmbBatchID;

    @FXML
    private ComboBox<String> cmbItemID;

    @FXML
    private TextField txtItemName;

    @FXML
    private TextField txtCompany;

    @FXML
    void SearchItem(KeyEvent event) {
        tblBatchDetail.getItems().clear();

        String text = "%"+txtsearchbar.getText()+"%";

        try {
            String SQL = "SELECT * FROM batchDetail WHERE batchid LIKE ? || itemid LIKE ? || qty LIKE ? || buyingPrice LIKE ? || sellingPrice LIKE ? || itemName LIKE ? ";
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(SQL);
            pstm.setObject(1, text);
            pstm.setObject(2, text);
            pstm.setObject(3, text);
            pstm.setObject(4, text);
            pstm.setObject(5, text);
            pstm.setObject(6, text);
            ResultSet rst = pstm.executeQuery();
            ObservableList<BatchDetail> list = FXCollections.observableArrayList();

            while(rst.next()){
                BatchDetail batchDetail = new BatchDetail(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getInt(4),
                        rst.getDouble(5),
                        rst.getDouble(6)
                );
                list.add(batchDetail);
            }
            tblBatchDetail.getItems().addAll(list);

        } catch (SQLException throwables) {
            new Alert(Alert.AlertType.ERROR,throwables.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING,"DRIVER NOT FOUND").show();
        }
    }

    @FXML
    void batchDelete(MouseEvent event) {
        String SQL = "DELETE FROM batch WHERE batchid = '"+txtBatchID.getText()+"'";
        try {
            boolean isdeleted =DBConnection.getInstance().getConnection().createStatement().executeUpdate(SQL)>0;
            if(isdeleted){
                loadBatchID();
                loadtblBatch();
                new Alert(Alert.AlertType.CONFIRMATION,"Batch has been deleted").show();
            }else{
                new Alert(Alert.AlertType.ERROR,"no Batch found").show();
            }
        } catch (SQLException throwables) {
            new Alert(Alert.AlertType.ERROR,throwables.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING,"DRIVER NOT FOUND").show();
        }
    }

    @FXML
    void batchSave(MouseEvent event) {
        try {
            String sql= "insert into batch values(?,?,?,?,?,?)";
            PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
            stm.setString(1,txtBatchID.getText());
            stm.setString(2,cmbSupplierID.getValue());
            stm.setString(3,txtSupplierName.getText());
            stm.setString(4,txtCompany.getText());
            stm.setString(5,txtCost.getText());
            stm.setString(6,txtdate.getText());


            boolean isSaved =stm.executeUpdate()>0;
            if (isSaved) {
                loadBatchID();
                loadtblBatch();
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
    void batchUpdate(MouseEvent event) {
        try {
            String SQL = "UPDATE batch SET supplierid=?, suppliername=?, company=?, totalcost=?, date=? WHERE batchid=?";
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(SQL);
            pstm.setString(1,cmbSupplierID.getValue());
            pstm.setString(2,txtSupplierName.getText());
            pstm.setString(3,txtCompany.getText());
            pstm.setString(4,txtCost.getText());
            pstm.setString(5,txtdate.getText());
            pstm.setString(6,txtBatchID.getText());
            boolean isUpdated = pstm.executeUpdate() > 0;

            if(isUpdated){
                loadtblBatch();
                loadBatchID();
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

    @FXML
    void clear(MouseEvent event) {
        txtBatchID.setText("");
        cmbSupplierID.setValue("Supplier ID");
        txtSupplierName.setText("");
        txtCost.setText("");
        txtCompany.setText("");
        txtdate.setText("");
    }

    @FXML
    void clear2(MouseEvent event) {
        txtsearchbar.setText("");
        cmbBatchID.setValue("Batch ID");
        cmbItemID.setValue("Item ID");
        txtItemName.setText("");
        txtQty.setText("");
        txtBuyingPrice.setText("");
        txtSelllingPrice.setText("");
    }

    @FXML
    void itemDelete(MouseEvent event) {
        String SQL = "DELETE FROM batchDetail WHERE batchid = '"+cmbBatchID.getValue()+"' AND itemid='"+cmbItemID.getValue()+"'";
        try {
            boolean isdeleted =DBConnection.getInstance().getConnection().createStatement().executeUpdate(SQL)>0;
            if(isdeleted){
                selectBatchMethod();
                new Alert(Alert.AlertType.CONFIRMATION,"Item has been deleted").show();
            }else{
                new Alert(Alert.AlertType.ERROR,"no Item found").show();
            }
        } catch (SQLException throwables) {
            new Alert(Alert.AlertType.ERROR,throwables.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING,"DRIVER NOT FOUND").show();
        }
    }

    @FXML
    void itemSave(MouseEvent event) {
        try {
            String sql= "insert into batchDetail values(?,?,?,?,?,?)";
            PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);
            stm.setString(1,cmbBatchID.getValue());
            stm.setString(2,cmbItemID.getValue());
            stm.setString(3,txtItemName.getText());
            stm.setString(4,txtQty.getText());
            stm.setString(5,txtBuyingPrice.getText());
            stm.setString(6,txtSelllingPrice.getText());


            boolean isSaved =stm.executeUpdate()>0;
            if (isSaved) {
                selectBatchMethod();
                Boolean isStockUpdated = updateStock();
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

    private Boolean updateStock() {
        if(Isnull()){
            new Alert(Alert.AlertType.ERROR,"Empty Feilds...").show();
            return false;
        }
        try{
            String SQL = "UPDATE item set qtyOnHand=qtyOnHand+? WHERE itemid =?";
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(SQL);
            pstm.setObject(1,txtQty.getText());
            pstm.setObject(2,cmbItemID.getValue());
            return pstm.executeUpdate()>0;

        } catch (ClassNotFoundException ex) {
            new Alert(Alert.AlertType.WARNING,"DRIVER NOT FOUND").show();
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR,ex.getMessage()).show();
        }
        return false;
    }

    public boolean Isnull() {
        try{
            String s=cmbBatchID.getValue();
            String s1 = cmbItemID.getValue();
            String s2 = cmbSupplierID.getValue();
            int qty = Integer.parseInt(txtQty.getText());

        }catch (Exception e){
            return true;
        }
        return txtQty.getText().equalsIgnoreCase("") || txtBuyingPrice.getText().equalsIgnoreCase("") || txtSelllingPrice.getText().equalsIgnoreCase("");
    }
    @FXML
    void itemUpdate(MouseEvent event) {
        if(Isnull()){
            new Alert(Alert.AlertType.ERROR,"Empty Feilds...").show();
            return;
        }
        try {
            String SQL = "UPDATE batchDetail SET qty=?, buyingPrice=?, sellingPrice=?,  WHERE batchid=? AND itemid=?";
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(SQL);
            pstm.setString(1,txtQty.getText());
            pstm.setString(2,txtBuyingPrice.getText());
            pstm.setString(3,txtSelllingPrice.getText());
            pstm.setString(4,cmbBatchID.getValue());
            pstm.setString(5,cmbItemID.getValue());
            //pstm.setString(6,txtBatchID.getText());
            boolean isUpdated = pstm.executeUpdate() > 0;

            if(isUpdated){
                selectBatchMethod();
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

    @FXML
    void tblBatchClicked(MouseEvent event) {
        txtBatchID.setText(tblBatch.getSelectionModel().selectedItemProperty().get().getBatchid());
        cmbSupplierID.setValue(tblBatch.getSelectionModel().selectedItemProperty().get().getSupplierid());
        txtSupplierName.setText(tblBatch.getSelectionModel().selectedItemProperty().get().getSupplierName());
        txtCompany.setText(tblBatch.getSelectionModel().selectedItemProperty().get().getCompany());
        txtCost.setText(String.valueOf(tblBatch.getSelectionModel().selectedItemProperty().get().getTotalCost()));
        txtdate.setText(tblBatch.getSelectionModel().selectedItemProperty().get().getDate());
    }

    @FXML
    void tblBatchDeteailClicked(MouseEvent event) {
        if(Isnull()){
            txtBatchID.setText(tblBatchDetail.getSelectionModel().selectedItemProperty().get().getBatchid());
        }
        txtBatchID.setText(tblBatchDetail.getSelectionModel().selectedItemProperty().get().getBatchid());
        cmbItemID.setValue(tblBatchDetail.getSelectionModel().selectedItemProperty().get().getItemid());
        txtItemName.setText(tblBatchDetail.getSelectionModel().selectedItemProperty().get().getItemName());
        txtQty.setText(String.valueOf(tblBatchDetail.getSelectionModel().selectedItemProperty().get().getQty()));
        txtBuyingPrice.setText(String.valueOf(tblBatchDetail.getSelectionModel().selectedItemProperty().get().getBuyingPrice()));
        txtSelllingPrice.setText(String.valueOf(tblBatchDetail.getSelectionModel().selectedItemProperty().get().getSellingPrice()));
    }

    public void selectSupplier(ActionEvent actionEvent) {
        String text = cmbSupplierID.getValue();
        //System.out.println(text);
        try{
            String SQL = "SELECT * FROM supplier WHERE supplierID='"+text+"'";
            ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery(SQL);
            if(rst.next()){
                txtSupplierName.setText(rst.getString(2)+" "+rst.getString(3));
                txtCompany.setText(rst.getString(4));
            }

        } catch (SQLException throwables) {
            new Alert(Alert.AlertType.ERROR,throwables.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING,"DRIVER NOT FOUND").show();
        }
    }

    public void selectItem(ActionEvent actionEvent) {
        String text = cmbItemID.getValue();
        //System.out.println(text);
        try{
            String SQL = "SELECT * FROM item WHERE itemid='"+text+"'";
            ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery(SQL);
            if(rst.next()){
                txtItemName.setText(rst.getString(2));
            }

        } catch (SQLException throwables) {
            new Alert(Alert.AlertType.ERROR,throwables.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING,"DRIVER NOT FOUND").show();
        }
    }

    public void selectBatch(ActionEvent actionEvent) {
        selectBatchMethod();
    }
    private void selectBatchMethod(){
        String text = cmbBatchID.getValue();
        //System.out.println(text);
        try{
            String SQL = "SELECT * FROM batchDetail WHERE batchid='"+text+"'";
            ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery(SQL);
            List<BatchDetail> list2= new ArrayList<>();
            //ObservableList<BatchDetail> list2 = FXCollections.observableArrayList();
            while(rst.next()){
                BatchDetail batchDetail = new BatchDetail(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getInt(4),
                        rst.getDouble(5),
                        rst.getDouble(6)
                );
                list2.add(batchDetail);
            }

            tblBatchDetail.setItems(FXCollections.observableArrayList(list2));

        } catch (SQLException throwables) {
            new Alert(Alert.AlertType.ERROR,throwables.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING,"DRIVER NOT FOUND").show();
        }


    }
    private void loadDate() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
        String datetxt = simpleDateFormat.format(date);
        txtdate.setText(datetxt);
    }

}
