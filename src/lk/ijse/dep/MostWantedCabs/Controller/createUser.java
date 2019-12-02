package lk.ijse.dep.MostWantedCabs.Controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import lk.ijse.dep.MostWantedCabs.Business.BOFactory;
import lk.ijse.dep.MostWantedCabs.Business.BOType;
import lk.ijse.dep.MostWantedCabs.Business.custom.UserBO;
import lk.ijse.dep.MostWantedCabs.DTO.UserDTO;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class createUser {
    public JFXTextField txtUserName;
    public JFXTextField txtPassword;
    public JFXTextField txtAddress;
    public JFXTextField txtContactNumber;
    public Pane settingsPane;

    private UserBO userBO = BOFactory.getInstance().getBO(BOType.USER);

    public void initialize(){
        txtUserName.setDisable(true);
        txtPassword.setDisable(true);
        txtAddress.setDisable(true);
        txtContactNumber.setDisable(true);
    }

    public void btnSignInOnAction(ActionEvent actionEvent) {

        try {
            if(userBO.existUser(txtPassword.getText())){
                new Alert(Alert.AlertType.ERROR,"Enter unique password", ButtonType.OK).show();
                txtPassword.requestFocus();
                return;
            }else if(!txtPassword.getText().matches("^[A-Za-z0-9]+$")){
                new Alert(Alert.AlertType.ERROR,"Invalid  password or empty !", ButtonType.OK).show();
                txtPassword.requestFocus();
                return;
            }else if(!txtUserName.getText().matches("^[A-Za-z0-9]+$")){
                new Alert(Alert.AlertType.ERROR,"Invalid  Username or empty !", ButtonType.OK).show();
                txtUserName.requestFocus();
                return;
            }else if(!txtAddress.getText().matches("^.+$")) {
                new Alert(Alert.AlertType.ERROR, "Invalid  address or empty !", ButtonType.OK).show();
                txtAddress.requestFocus();
                return;
            }else if(!txtContactNumber.getText().matches("^[0-9]{10}$")) {
                new Alert(Alert.AlertType.ERROR, "Invalid  address or empty !", ButtonType.OK).show();
                txtContactNumber.requestFocus();
                return;
            }

            userBO.save(new UserDTO(txtUserName.getText(),txtPassword.getText(),txtAddress.getText(),txtContactNumber.getText()));
            new Alert(Alert.AlertType.INFORMATION, "New User add successfully !", ButtonType.OK).show();
            btnNewInOnAction(actionEvent);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"Something went wrong,please contact service team", ButtonType.OK).show();
            Logger.getLogger("lk.ijse.dep.MostWantedCabs.Controller").log(Level.SEVERE,null,e);
        }

    }

    public void btnNewInOnAction(ActionEvent actionEvent) {
        txtUserName.setDisable(false);
        txtPassword.setDisable(false);
        txtAddress.setDisable(false);
        txtContactNumber.setDisable(false);
        txtUserName.clear();
        txtPassword.clear();
        txtAddress.clear();
        txtContactNumber.clear();

    }
}
