package lk.ijse.pos.Controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    private AnchorPane login;

    @FXML
    private Button btnlogin;

    @FXML
    private JFXTextField txtUserName;

    @FXML
    private JFXPasswordField txtPassword;

    public void login(ActionEvent actionEvent) {

            String uname = txtUserName.getText();
            String upass = txtPassword.getText();


            if (uname.equalsIgnoreCase("cj") && upass.equals("ijse") || uname.equalsIgnoreCase("mutti") && upass.equals("ijse")) {
                try {
                    Parent p = FXMLLoader.load(getClass().getResource("../view/MainView.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(p);
                    stage.setScene(scene);
                    //stage.setResizable(false);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "You Have Entered Wrong User Name Or Password. And Try Again !", ButtonType.OK).show();
            }


    }
}
