package lk.ijse.pos.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.pos.DB.DBConnection;

import javax.naming.TimeLimitExceededException;
import java.io.IOException;
import java.net.URL;
import java.security.Key;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        laodDateTime();
        loadCustomerCount();
        loadLessthan10();
        loadSupplierCount();
    }


    private void laodDateTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
        String datetxt = simpleDateFormat.format(date);
        txtdate.setText(datetxt);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                Calendar time = Calendar.getInstance();
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                                txttime.setText(simpleDateFormat.format(time.getTime()));
                            }
                        }
                ),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    @FXML
    private Text txtdate;

    @FXML
    private Text txttime;

    @FXML
    private Text itemsSold;

    @FXML
    private Text CustomerCount;

    @FXML
    private Text SupplierCount;

    @FXML
    private Text emptyItemCount;

    @FXML
    private Text emptyItems;

    @FXML
    private Text dailyIncome;

    @FXML
    private AnchorPane mainwindow;




    public void placeOrder(ActionEvent actionEvent) {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("../view/PlaceOrderForm.fxml"));
            mainwindow.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void manageCustomer(MouseEvent mouseEvent) {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("../view/Customer.fxml"));
            mainwindow.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void ManageSupplier(MouseEvent event) {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("../view/Supplier.fxml"));
            mainwindow.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void manageBatch(MouseEvent event) {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("../view/Batch.fxml"));
            mainwindow.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void manageItem(MouseEvent event) {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("../view/Item.fxml"));
            mainwindow.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void manageLoyalityCard(MouseEvent event) {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("../view/LoyalityCard.fxml"));
            mainwindow.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ManageSeasonalDiscount(MouseEvent event) {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("../view/SeasonalDiscount.fxml"));
            mainwindow.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void manageUser(MouseEvent event) {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("../view/User.fxml"));
            mainwindow.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadCustomerCount() {
        try {
            ResultSet set = DBConnection.getInstance().getConnection().createStatement().executeQuery("select count(orderid) from orders WHERE date='"+txtdate.getText()+"'");
            if (set.next()) {
                int tempcount = set.getInt(1);
                if (tempcount<10){
                    CustomerCount.setText("0"+tempcount);
                }else{
                    CustomerCount.setText(String.valueOf(tempcount));
                }
            } else {
                CustomerCount.setText("00");
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR,"DRIVER NOT FOUND").show();
        }
    }

    public void loadLessthan10() {
        try {
            ResultSet set = DBConnection.getInstance().getConnection().createStatement().executeQuery("select count(itemid) from item where qtyOnHand<10");

            if (set.next()) {
                int itemCount = set.getInt(1);
                //System.out.println(itemCount);
                if (itemCount<10){
                    itemsSold.setText("0"+itemCount);
                }else{
                    itemsSold.setText(String.valueOf(itemCount));
                }
            }else {
                itemsSold.setText("00");
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR,"DRIVER NOT FOUND").show();
        }
    }

    public void loadSupplierCount() {

        try {
            ResultSet set = DBConnection.getInstance().getConnection().createStatement().executeQuery("select count(batchid) from batch WHERE date='"+txtdate.getText()+"'");
            if (set.next()) {
                int tempcount = set.getInt(1);
                if (tempcount<10){
                    SupplierCount.setText("0"+tempcount);
                }else{
                    SupplierCount.setText(String.valueOf(tempcount));
                }
            } else {
                SupplierCount.setText("0");
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR,"DRIVER NOT FOUND").show();
        }
    }

    public void loadItemCount() {
        try {
            ResultSet set = DBConnection.getInstance().getConnection().createStatement().executeQuery("select count(itemid) from stock");
            if (set.next()) {
                int tempcount = set.getInt(1);
                itemsSold.setText(tempcount + "");
            } else {
                itemsSold.setText("0");
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR,"DRIVER NOT FOUND").show();
        }
    }
}
