package lk.ijse.dep.MostWantedCabs.Controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.dep.MostWantedCabs.Business.BOFactory;
import lk.ijse.dep.MostWantedCabs.Business.BOType;
import lk.ijse.dep.MostWantedCabs.Business.custom.UserBO;
import lk.ijse.dep.MostWantedCabs.DTO.UserDTO;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class logging {
    public AnchorPane apeLogging;
    public JFXTextField txtUserName;


    public UserBO userBO= BOFactory.getInstance().getBO(BOType.USER);
    public JFXPasswordField txtPassword;


    public void initialize(){
        FadeTransition fadeIn = new FadeTransition(Duration.millis(2000));
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }

    public void btnSignUpOnAction(ActionEvent actionEvent) throws IOException {
        if(txtPassword.getText().equals("")||txtUserName.getText().equals("")){
            new Alert(Alert.AlertType.ERROR,"User name and Password empty !", ButtonType.OK).show();
            return;
        }else {
            try {
                UserDTO userDTO=new UserDTO();
                try {
                    userDTO = userBO.findUser(txtPassword.getText());
                }catch (NullPointerException e){
                    new Alert(Alert.AlertType.ERROR,"Password is wrong , Please try again", ButtonType.OK).show();
                    txtPassword.requestFocus();
                    return;
                }
                 if(txtUserName.getText().equals(userDTO.getUserName())){
                    new Alert(Alert.AlertType.ERROR,"User name is wrong , Please try again", ButtonType.OK).show();
                    txtUserName.requestFocus();
                    return;
                }

                URL resource = this.getClass().getResource("/lk/ijse/dep/MostWantedCabs/View/dashBoard.fxml");
                Parent root = FXMLLoader.load(resource);
                Scene scene = new Scene(root);
                Stage primaryStage = (Stage) (this.apeLogging.getScene().getWindow());
                primaryStage.setScene(scene);
                primaryStage.centerOnScreen();


            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR,"Something went wrong,please contact service team", ButtonType.OK).show();
                Logger.getLogger("lk.ijse.dep.MostWantedCabs.Controller").log(Level.SEVERE,null,e);
            }
        }
    }

    public void btnSignInOnAction(ActionEvent actionEvent) {
    }
}
