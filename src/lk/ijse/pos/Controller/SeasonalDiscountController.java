package lk.ijse.pos.Controller;

import com.sun.org.apache.bcel.internal.generic.ALOAD;
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
import lk.ijse.pos.Model.Discount;
import lk.ijse.pos.Model.Supplier;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.DoubleBinaryOperator;

public class SeasonalDiscountController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadTbl();
    }

    private void loadTbl() {
        col_discountID.setCellValueFactory(new PropertyValueFactory<>("sdid"));
        col_StartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        col_EndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        col_LowerLimit.setCellValueFactory(new PropertyValueFactory<>("lowerLimit"));
        col_UpperLimit.setCellValueFactory(new PropertyValueFactory<>("upperlimit"));
        col_Amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        col_Percentage.setCellValueFactory(new PropertyValueFactory<>("percentage"));
        col_Remarks.setCellValueFactory(new PropertyValueFactory<>("remarks"));

        try{
            String SQL = "SELECT * FROM seasonal_discount";
            ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery(SQL);
            ObservableList<Discount> list = FXCollections.observableArrayList();
            while (rst.next()){
                list.add(new Discount(
                        rst.getString(1),
                        rst.getDate(2),
                        rst.getDate(3),
                        rst.getDouble(4),
                        rst.getDouble(5),
                        rst.getDouble(6),
                        rst.getDouble(7),
                        rst.getString(8)
                ));
            }
            tblDiscount.setItems(list);

        } catch (ClassNotFoundException ex) {
            new Alert(Alert.AlertType.WARNING,"DRIVER NOT FOUND").show();
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR,ex.getMessage()).show();
        }
    }

    @FXML
    private AnchorPane DiscountPane;

    @FXML
    private TextField txtsearchbar;

    @FXML
    private TextField txtDiscountID;

    @FXML
    private TextField txtAmountPercentage;

    @FXML
    private TextArea txtRemarks;

    @FXML
    private TableView<Discount> tblDiscount;

    @FXML
    private TableColumn<Discount, String> col_discountID;

    @FXML
    private TableColumn<Discount, String> col_StartDate;

    @FXML
    private TableColumn<Discount, String> col_EndDate;

    @FXML
    private TableColumn<Discount, Double> col_LowerLimit;

    @FXML
    private TableColumn<Discount, Double> col_UpperLimit;

    @FXML
    private TableColumn<Discount, Double> col_Percentage;

    @FXML
    private TableColumn<Discount, Double> col_Amount;

    @FXML
    private TableColumn<Discount, String> col_Remarks;

    @FXML
    private DatePicker dtpStartDate;

    @FXML
    private DatePicker dtpEndDate;

    @FXML
    private RadioButton rdbAmount;

    @FXML
    private ToggleGroup DiscountType;

    @FXML
    private RadioButton rdbPercentage;

    @FXML
    private TextField txtLowerLimit;

    @FXML
    private TextField txtUpperLimit;


    @FXML
    void deleteDiscount(MouseEvent event) {
        try{
            String SQL = "DELETE FROM seasonal_discount WHERE sd_id='"+txtDiscountID.getText()+"'";
            boolean isDeleted = DBConnection.getInstance().getConnection().createStatement().executeUpdate(SQL) > 0;

            if (isDeleted){
                loadTbl();
                new Alert(Alert.AlertType.CONFIRMATION,"DELETED Successfully").show();
            }else{
                new Alert(Alert.AlertType.ERROR,"ID is incorrect. NO record found. try again").show();
            }
        } catch (SQLException throwables) {
            new Alert(Alert.AlertType.ERROR,throwables.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING,"DRIVER NOT FOUND").show();
        }
    }

    @FXML
    void saveDiscount(MouseEvent event) {
        Double discount = Double.parseDouble(txtAmountPercentage.getText());

        try{
            String percentage="";
            String amount="";
            if(discount==0){
                new Alert(Alert.AlertType.ERROR,"Not valid discount").show();
                return;
            }
            else if(rdbAmount.isSelected()){
                amount +=txtAmountPercentage.getText();
                percentage="0";
            }else if(rdbPercentage.isSelected()) {
                percentage += txtAmountPercentage.getText();
                amount = "0";
            }
            String SQL = "INSERT INTO seasonal_discount VALUES(?,?,?,?,?,?,?,?)";
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(SQL);
            pstm.setObject(1,txtDiscountID.getText());
            pstm.setObject(2,dtpStartDate.getValue());
            pstm.setObject(3,dtpEndDate.getValue());
            pstm.setObject(4,txtLowerLimit.getText());
            pstm.setObject(5,txtUpperLimit.getText());
            pstm.setObject(6,amount);
            pstm.setObject(7,percentage);
            pstm.setObject(8,txtRemarks.getText());

            Boolean isAdded =pstm.executeUpdate()>0;

            if (isAdded){
                loadTbl();
                new Alert(Alert.AlertType.CONFIRMATION,"Added Successfully").show();
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
    void searchBarAction(KeyEvent event) {
        tblDiscount.getItems().clear();

        String text = "%"+txtsearchbar.getText()+"%";

        try {
            String SQL = "SELECT * FROM seasonal_discount WHERE sd_id LIKE ? || startdate LIKE ? || enddate LIKE ? || lowerLimit LIKE ? || upperLimit LIKE ? || amount LIKE ? || percentage LIKE ? || remarks LIKE ?";
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
            ObservableList<Discount> list = FXCollections.observableArrayList();

            while(rst.next()){
                list.add(new Discount(
                        rst.getString(1),
                        rst.getDate(2),
                        rst.getDate(3),
                        rst.getDouble(4),
                        rst.getDouble(5),
                        rst.getDouble(6),
                        rst.getDouble(7),
                        rst.getString(8)
                ));
            }
            tblDiscount.setItems(list);

        } catch (SQLException throwables) {
            new Alert(Alert.AlertType.ERROR,throwables.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING,"DRIVER NOT FOUND").show();
        }
    }

    @FXML
    void tblClicked(MouseEvent event) {
        txtDiscountID.setText(tblDiscount.getSelectionModel().selectedItemProperty().get().getSdid());
        //dtpStartDate.setValue(LocalDate.parse((CharSequence) tblDiscount.getSelectionModel().selectedItemProperty().get().getStartDate()));
        //dtpEndDate.setValue(LocalDate.parse((CharSequence) tblDiscount.getSelectionModel().selectedItemProperty().get().getEndDate()));
        txtLowerLimit.setText(String.valueOf(tblDiscount.getSelectionModel().selectedItemProperty().get().getLowerLimit()));
        txtUpperLimit.setText(String.valueOf(tblDiscount.getSelectionModel().selectedItemProperty().get().getUpperlimit()));
        double percentage = tblDiscount.getSelectionModel().selectedItemProperty().get().getPercentage();
        double amount = tblDiscount.getSelectionModel().selectedItemProperty().get().getAmount();
        if (amount!=0){
            rdbAmount.fire();
            txtAmountPercentage.setText(String.valueOf(amount));
        }else{
            rdbPercentage.fire();
            txtAmountPercentage.setText(String.valueOf(percentage));
        }
        txtRemarks.setText(tblDiscount.getSelectionModel().selectedItemProperty().get().getRemarks());
    }

    @FXML
    void updateDiscount(MouseEvent event) {
        try{
            String percentage="";
            String amount="";
            if(rdbAmount.isSelected()){
                amount +=txtAmountPercentage.getText();
                percentage="0";
            }else if(rdbPercentage.isSelected()){
                percentage +=txtAmountPercentage.getText();
                amount="0";
            }

            String SQL = "UPDATE seasonal_discount SET startdate=?, enddate=?, lowerLimit=?, upperLimit=?,amount=?,percentage=?,remarks=? WHERE sd_id=?";
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(SQL);
            pstm.setObject(1,dtpStartDate.getValue());
            pstm.setObject(2,dtpEndDate.getValue());
            pstm.setObject(3,txtLowerLimit.getText());
            pstm.setObject(4,txtUpperLimit.getText());
            pstm.setObject(5,amount);
            pstm.setObject(6,percentage);
            pstm.setObject(7,txtRemarks.getText());
            pstm.setObject(8,txtDiscountID.getText());
            boolean isUpdated = pstm.executeUpdate() > 0;

            if (isUpdated){
                loadTbl();
                clear();
                new Alert(Alert.AlertType.CONFIRMATION,"UPDATED SUCESSFULLY...").show();
            }else{
                new Alert(Alert.AlertType.ERROR,"TRY AGAIN....").show();
            }

        } catch (SQLException throwables) {
            new Alert(Alert.AlertType.ERROR,throwables.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING,"DRIVER NOT FOUND").show();
        }
    }

    private void clear(){
        txtDiscountID.setText("");
        txtLowerLimit.setText("");
        txtUpperLimit.setText("");
        txtAmountPercentage.setText("");
        txtRemarks.setText("");
        dtpStartDate.getEditor().clear();
        dtpEndDate.getEditor().clear();
    }

    @FXML
    void clearFeilds(MouseEvent event) {
        clear();
    }
}
