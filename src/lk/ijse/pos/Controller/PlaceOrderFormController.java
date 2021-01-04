package lk.ijse.pos.Controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import com.sun.org.apache.xpath.internal.operations.Or;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.pos.DB.DBConnection;
import lk.ijse.pos.Model.*;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class PlaceOrderFormController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadCustomerID();
        loadItemID();
        loadPlaceOrderID();
        loadDate();
        loadTbl();
    }

    private void loadTbl() {
        col_ItemCode.setCellValueFactory(new PropertyValueFactory<>("itemcode"));
        col_ItemName.setCellValueFactory(new PropertyValueFactory<>("itemname"));
        col_UnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitprice"));
        col_Qty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        col_Total.setCellValueFactory(new PropertyValueFactory<>("total"));


    }

    private void loadDate() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
        String datetxt = simpleDateFormat.format(date);
        txtDate.setText(datetxt);
    }

    private String loadTime(){Date time = new Date();
        SimpleDateFormat sd2 = new SimpleDateFormat("HH:mm:ss");
        String orderTime = sd2.format(time);
        return orderTime;
    }

    private void loadPlaceOrderID() {
        try{
            String SQL ="SELECT orderid FROM orders ORDER BY orderid DESC limit 1";
            ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery(SQL);
            if(rst.next()){
                String id =rst.getString(1);
                String[] temparr = id.split("D");
                int temp = Integer.parseInt(temparr[1]);
                temp+=1;

                if(temp<10){
                    txtOrderID.setText("D00"+temp);
                }else if(temp<100){
                    txtOrderID.setText("D0"+temp);
                }else{
                    txtOrderID.setText("D"+temp);
                }

            }else{
                txtOrderID.setText("D001");
            }
        } catch (ClassNotFoundException ex) {
            new Alert(Alert.AlertType.WARNING,"DRIVER NOT FOUND").show();
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR,ex.getMessage()).show();
        }
    }

    private void loadCustomerID(){
        try{
            String sql="SELECT customerid FROM customer";
            ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery(sql);
            ObservableList<String> list = FXCollections.observableArrayList();
            while (rst.next()) {
                list.add(rst.getString(1));
            }
            cmbCustomerID.setItems(list);

        } catch (SQLException throwables) {
            new Alert(Alert.AlertType.ERROR,throwables.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING,"DRIVER NOT FOUND").show();
        }
    }
    private void loadItemID(){
        try{
            String sql="SELECT itemid FROM item";
            ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery(sql);
            ObservableList<String> list = FXCollections.observableArrayList();
            while (rst.next()) {
                list.add(rst.getString(1));
            }
            cmbItemCode.setItems(list);
            txtCustomerName.setText(cmbCustomerID.getValue());

        } catch (ClassNotFoundException ex) {
            new Alert(Alert.AlertType.WARNING,"DRIVER NOT FOUND").show();
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR,ex.getMessage()).show();
        }
    }

    @FXML
    private AnchorPane PlaceOrderPane;

    @FXML
    private TableView<PlaceOrder> tblPlaceOrder;

    @FXML
    private TableColumn<PlaceOrder, String> col_ItemCode;

    @FXML
    private TableColumn<PlaceOrder, String> col_ItemName;

    @FXML
    private TableColumn<PlaceOrder, Double> col_UnitPrice;

    @FXML
    private TableColumn<PlaceOrder, Integer> col_Qty;

    @FXML
    private TableColumn<PlaceOrder, Double> col_Total;

    @FXML
    private Label lblGrossPrice;

    @FXML
    private Label lblNetPrice;

    @FXML
    private Label lblDiscount;

    @FXML
    private Label lblPoint;

    @FXML
    private Label lblPointUse;

    @FXML
    private Label lblItemCount;

    @FXML
    private TextField txtPointUse;

    @FXML
    private JFXTextField txtOrderID;

    @FXML
    private JFXComboBox<String> cmbCustomerID;

    @FXML
    private JFXTextField txtCustomerName;

    @FXML
    private JFXTextField txtDate;

    @FXML
    private JFXComboBox<String> cmbItemCode;

    @FXML
    private JFXTextField txtItemName;

    @FXML
    private JFXTextField txtQtyOnHand;

    @FXML
    private JFXTextField txtUnitPrice;

    @FXML
    private JFXTextField txtQty;

    @FXML
    void addPoints(MouseEvent event) {

        if(pointsNull()){
            new Alert(Alert.AlertType.ERROR,"Invalied Loyality Point Amount").show();
            return;
        }
        else if(Double.parseDouble(txtPointUse.getText())>Double.parseDouble(lblPoint.getText())){
            new Alert(Alert.AlertType.ERROR,"Invalied Loyality Point Amount").show();
            return;
        }else{
            lblPointUse.setText(txtPointUse.getText());
            calCulateTotal();
        }
    }

    @FXML
    void placeOrder(MouseEvent event) {
        try{
            String orderId = txtOrderID.getText();
            String customerid = cmbCustomerID.getValue();
            String orderDate = txtDate.getText();
            String orderTime = loadTime();
            ObservableList<OrderDetail> orderDetails = FXCollections.observableArrayList();

            for(int i=0; i<tblPlaceOrder.getItems().size(); i++) {
                String itemCode = list.get(i).getItemcode();
                int qty = list.get(i).getQty();
                double unitPrice = list.get(i).getUnitprice();
                orderDetails.add(new OrderDetail(orderId, itemCode,unitPrice, qty));
            }

            String paymentId = new PaymentController().generateOrderId();
            double grossPrice = Double.parseDouble(lblGrossPrice.getText());
            double points = Double.parseDouble(lblPointUse.getText());
            double totalDiscount = Double.parseDouble(lblDiscount.getText())+points;
            double netPrice = grossPrice-totalDiscount;
            String lcid = getLCID();
            Payment payment = new Payment(paymentId,orderId,grossPrice,points,totalDiscount,netPrice,lcid);

            Orders orders = new Orders(orderId,customerid,orderDate,orderTime,orderDetails,payment);


            boolean isAdded = addOrder(orders);
            if (isAdded){
                clearAfterOrder();
                loadPlaceOrderID();
                new Alert(Alert.AlertType.CONFIRMATION,"Added Successfully...").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Added failed...").show();
            }
        } catch (SQLException throwables) {
            new Alert(Alert.AlertType.ERROR,throwables.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING,"DRIVER NOT FOUND").show();
        }
    }

    public boolean addOrder(Orders orders) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO orders VALUES(?,?,?,?)";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        try{
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            pstm.setObject(1,orders.getOrderid());
            pstm.setObject(2,orders.getCustomerid());
            pstm.setObject(3,orders.getDate());
            pstm.setObject(4,orders.getTime());
            boolean isOrderAdded = pstm.executeUpdate() > 0;
            if (isOrderAdded) {
                boolean addAllItemDetails = new OrderDetailController().addOrderDetail(orders.getOrderDetail());
                if (addAllItemDetails) {
                    boolean isUpdateItemStock = new OrderDetailController().updateStock(orders.getOrderDetail());
                    if (isUpdateItemStock) {
                        boolean addPayment = new PaymentController().addPayment(orders.getPayment());
                        if (addPayment){
                            if(checkLoyeality()>0){
                                new PaymentController().updatePoints(orders.getPayment());
                            }
                            //loadDashBoard();
                            DBConnection.getInstance().getConnection().commit();
                            return true;
                        }
                    }
                }
            }
            DBConnection.getInstance().getConnection().rollback();
            return false;
        } finally {

            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }


    @FXML
    void removeItem(MouseEvent event) {

        int row = tblPlaceOrder.getSelectionModel().selectedIndexProperty().getValue();

        Double total = Double.parseDouble(lblGrossPrice.getText()) - list.get(row).getTotal();
        int qty = Integer.parseInt(lblItemCount.getText()) - list.get(row).getQty();
        lblGrossPrice.setText(String.valueOf(total));
        lblItemCount.setText(String.valueOf(qty));
        list.remove(row);
        tblPlaceOrder.getItems().setAll(list);
    }

    public void loadCustomerName(ActionEvent actionEvent) {
        String text = cmbCustomerID.getValue();
        //System.out.println(text);
        try{
            String SQL = "SELECT firstname,lastname FROM customer WHERE customerid='"+text+"'";
            ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery(SQL);
            if(rst.next()){
                txtCustomerName.setText(rst.getString(1)+" "+rst.getString(2));
            }
            loadPoints();

        } catch (SQLException throwables) {
            new Alert(Alert.AlertType.ERROR,throwables.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING,"DRIVER NOT FOUND").show();
        }
    }


    public void loadItemName(ActionEvent actionEvent) {
        String text = cmbItemCode.getValue();
        //System.out.println(text);
        try{
            String SQL = "SELECT name, qtyOnHand,buyingPrice FROM item WHERE itemid='"+text+"'";
            ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery(SQL);
            if(rst.next()){
                txtItemName.setText(rst.getString(1));
                txtQtyOnHand.setText(rst.getString(2));
                txtUnitPrice.setText(rst.getString(3));
            }

        } catch (SQLException throwables) {
            new Alert(Alert.AlertType.ERROR,throwables.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING,"DRIVER NOT FOUND").show();
        }
    }

    ObservableList<PlaceOrder> list = FXCollections.observableArrayList();

    public void addItem(MouseEvent mouseEvent) {
        tblPlaceOrder.setEditable(true);

        if(Isnull()){
            new Alert(Alert.AlertType.ERROR,"Empty Feilds...").show();
            return;
        }else if (Integer.parseInt(txtQtyOnHand.getText())<Integer.parseInt(txtQty.getText())){
            new Alert(Alert.AlertType.ERROR,"stock empty").show();
            return;
        }

        int qty = Integer.parseInt(txtQty.getText());
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        double total= unitPrice *  qty;

        int rowIndex = isAlreadyExists(cmbItemCode.getValue());

        if(rowIndex==-1){
           list.add(new PlaceOrder(
                   cmbItemCode.getValue(),
                   txtItemName.getText(),
                   Double.parseDouble(txtUnitPrice.getText()),
                   qty,
                   total
           ));
            tblPlaceOrder.getItems().setAll(list);
            clearFields();
        }else{
            qty+= Integer.parseInt(tblPlaceOrder.getColumns().get(3).getCellObservableValue(rowIndex).getValue().toString());
            total = qty*unitPrice;

            PlaceOrder placeOrder = new PlaceOrder(
                   cmbItemCode.getValue(),
                   txtItemName.getText(),
                   Double.parseDouble(txtUnitPrice.getText()),
                   qty,
                   total
            );

            list.add(placeOrder);
            list.remove(rowIndex);
            tblPlaceOrder.getItems().setAll(list);

            clearFields();

        }
        itemCount();
        calCulateTotal();
        setDiscountPlaceOrder();
    }
    private Discount discountEligibility(){
        try{
            double amount = Double.parseDouble(lblGrossPrice.getText());
            String SQL = "SELECT * FROM seasonal_discount WHERE lowerLimit <=? AND upperLimit >=?";
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(SQL);
            pstm.setObject(1,amount);
            pstm.setObject(2,amount);
            ResultSet rst = pstm.executeQuery();
            if (rst.next()){
                return new Discount(
                        rst.getString(1),
                        rst.getDate(2),
                        rst.getDate(3),
                        rst.getDouble(4),
                        rst.getDouble(5),
                        rst.getDouble(6),
                        rst.getDouble(7),
                        rst.getString(8)
                        );
            }

        } catch (SQLException throwables) {
            new Alert(Alert.AlertType.ERROR,throwables.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING,"DRIVER NOT FOUND").show();
        }
        return null;
    }
    public void setDiscountPlaceOrder(){
        Discount discount = discountEligibility();
        if (discount!=null){
            Date startDate=discountEligibility().getStartDate();
            Date endDate = discountEligibility().getEndDate();
            Date currentDate = new Date();

            Boolean inRange = isWithinRange(currentDate,startDate,endDate);
            if (inRange){
                double amount=discount.getAmount();
                double percentage = discount.getPercentage();
                if (amount!=0){
                    setDiscountAmount(amount);
                }else{
                    setDiscountPercentage(percentage);
                }
            }

        }else{
            return;
        }
    }
    private boolean isWithinRange(Date testDate,Date startDate, Date endDate) {
        return testDate.getTime() >= startDate.getTime() &&
                testDate.getTime() <= endDate.getTime();
    }
    private void setDiscountPercentage(double percetage){
        double gross = Double.parseDouble(lblGrossPrice.getText());
        double discount = gross*percetage/(double) 100;
        double net = gross*(100-percetage)/(double) 100;
        lblDiscount.setText(String.valueOf(discount));
        lblNetPrice.setText(String.valueOf(net));
    }
    private void setDiscountAmount(double amount){
        double gross = Double.parseDouble(lblGrossPrice.getText());
        double net = gross-amount;
        lblNetPrice.setText(String.valueOf(net));
        lblDiscount.setText(String.valueOf(amount));
    }

    private void itemCount() {
        int itemcount=0;
        for (int i=0; i<tblPlaceOrder.getItems().size();i++){
            itemcount+=Integer.parseInt(tblPlaceOrder.getColumns().get(3).getCellObservableValue(i).getValue().toString());
        }
        lblItemCount.setText(String.valueOf(itemcount));
    }

    private void clearAfterOrder(){
        tblPlaceOrder.getItems().clear();
        lblGrossPrice.setText("00.00");
        lblDiscount.setText("00.00");
        lblPoint.setText("00.00");
        lblPointUse.setText("00.00");
        lblItemCount.setText("00");
        lblNetPrice.setText("00.00");
        cmbCustomerID.setValue("");
        txtCustomerName.setText("");
        txtPointUse.setText("");
    }
    private void clearFields() {
        cmbItemCode.setValue("");
        txtItemName.setText("");
        txtUnitPrice.setText("");
        txtQty.setText("");
        txtQtyOnHand.setText("");
    }

    private void calCulateTotal() {
        double grossTotal = 0;
        for (int i = 0; i < tblPlaceOrder.getItems().size(); i++) {
            grossTotal += Double.parseDouble(tblPlaceOrder.getColumns().get(4).getCellObservableValue(i).getValue().toString());
        }
        lblGrossPrice.setText(String.valueOf(grossTotal));
        calCulateNetPrice();
    }

    private void calCulateNetPrice() {
        double netPrice = Double.parseDouble(lblGrossPrice.getText())-Double.parseDouble(lblDiscount.getText())-Double.parseDouble(lblPointUse.getText());
        lblNetPrice.setText(String.valueOf(netPrice));
    }


    private int isAlreadyExists(String code) {
        for (int i = 0; i < tblPlaceOrder.getItems().size(); i++) {
            String temp =tblPlaceOrder.getColumns().get(0).getCellObservableValue(i).getValue().toString();
            if (temp.equalsIgnoreCase(code)) {
                return i;
            }
        }
        return -1;
    }

    private boolean Isnull() {
        try{
            String s=cmbItemCode.getValue();
            int qty = Integer.parseInt(txtQty.getText());

        }catch (Exception e){
            return true;
        }
        return txtUnitPrice.getText().equalsIgnoreCase("") || txtItemName.getText().equalsIgnoreCase("") ;
    }

    public boolean pointsNull(){
        return txtPointUse.getText().equalsIgnoreCase("");
    }

    private int checkLoyeality(){
        int count=0;
        try{
            String SQL = "select count(lcid) from loyality_card where customerid='"+cmbCustomerID.getValue()+"'";
            ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery(SQL);
            rst.next();
            count = rst.getInt(1);
        } catch (SQLException throwables) {
            new Alert(Alert.AlertType.ERROR,throwables.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR,"DRIVER NOT FOUND").show();
        }
        return count;
    }
    private void loadPoints(){
        if (checkLoyeality()!=0){
            try {
                String SQL = "select points from loyality_card where customerid='"+cmbCustomerID.getValue()+"'";
                ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery(SQL);
                rst.next();
                double points = rst.getDouble(1);
                lblPoint.setText(String.valueOf(points));

            } catch (SQLException throwables) {
                new Alert(Alert.AlertType.ERROR,throwables.getMessage()).show();
            } catch (ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR,"DRIVER NOT FOUND").show();
            }
        }else {
            lblPoint.setText("00.00");
            return;
        }

    }
    private String getLCID(){
        try{
            if (checkLoyeality()!=0){
                String SQL = "SELECT lcid FROM loyality_card WHERE customerid='"+cmbCustomerID.getValue()+"'";
                ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery(SQL);
                if (rst.next()){
                    return rst.getString(1);
                }
            }
        } catch (SQLException throwables) {
            new Alert(Alert.AlertType.ERROR,throwables.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING,"DRIVER NOT FOUND").show();
        }
        return "";
    }

    public void loadDashBoard(){
        FXMLLoader fx = new FXMLLoader(getClass().getResource("../View/MainView.fxml"));
        MainViewController m = (MainViewController) fx.getController();
        m.loadCustomerCount();
        m.loadSupplierCount();
        m.loadLessthan10();
    }

}
