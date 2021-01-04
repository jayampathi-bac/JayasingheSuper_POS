package lk.ijse.pos.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.pos.DB.DBConnection;
import lk.ijse.pos.Model.Users;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadUsersTbl();
    }

    @FXML
    private AnchorPane UserPane;

    @FXML
    private TextField txtUserID;

    @FXML
    private TextField txtfname;

    @FXML
    private TextField txtCurrentPassword;

    @FXML
    private TextField txtlname;

    @FXML
    private TextField txtNewPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    private TableView<?> tblLoginInfo;

    @FXML
    private TableColumn<Users, String> col_LogUserID;

    @FXML
    private TableColumn<Users, String> col_LogUserName;

    @FXML
    private TableColumn<Users, String> col_LogName;

    @FXML
    private TableColumn<Users, String> col_LogDate;

    @FXML
    private TableColumn<Users, String> col_LogTime;

    @FXML
    private TableView<Users> tblUsers;

    @FXML
    private TableColumn<?, ?> col_userID;

    @FXML
    private TableColumn<?, ?> col_UserName;

    @FXML
    private TableColumn<?, ?> col_FName;

    @FXML
    private TableColumn<?, ?> col_LName;

    @FXML
    void UpdateUser(MouseEvent event) {

    }

    @FXML
    void generateUserInfo(MouseEvent event) {

    }

    private void loadUsersTbl() {
        col_userID.setCellValueFactory(new PropertyValueFactory<>("userid"));
        col_UserName.setCellValueFactory(new PropertyValueFactory<>("username"));
        col_FName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        col_LName.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        ObservableList<Users> list = FXCollections.observableArrayList();
        try{
            String SQL = "SELECT * FROM users";
            ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery(SQL);
            while (rst.next()){
                list.add(new Users(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getString(4)
                ));
            }
            tblUsers.setItems(list);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
