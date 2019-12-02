package lk.ijse.dep.MostWantedCabs.Controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import lk.ijse.dep.MostWantedCabs.Business.BOFactory;
import lk.ijse.dep.MostWantedCabs.Business.BOType;
import lk.ijse.dep.MostWantedCabs.Business.custom.UserBO;

public class resetPassword {
    public JFXTextField txtOldPassword;
    public JFXTextField txtNewPassword;
    public Pane resetPassword;

    private UserBO userBO= BOFactory.getInstance().getBO(BOType.USER);
    public void btnSaveOnAction(ActionEvent actionEvent) {
        try {
            if(userBO.existUser(txtOldPassword.getText())){

                if(userBO.existUser(txtNewPassword.getText())){
                    new Alert(Alert.AlertType.ERROR,"Enter unique password", ButtonType.OK).show();
                    txtNewPassword.requestFocus();
                }else if(!txtNewPassword.getText().matches("^[A-Za-z0-9]+$")){
                    new Alert(Alert.AlertType.ERROR,"Invalid new password or empty", ButtonType.OK).show();
                    txtNewPassword.requestFocus();
                }else {
                    userBO.resetPassword(txtNewPassword.getText(), txtOldPassword.getText());
                    new Alert(Alert.AlertType.INFORMATION, "Password reset Successfully !", ButtonType.OK).show();
                    txtNewPassword.clear();
                    txtOldPassword.clear();
                }
            }else {
                new Alert(Alert.AlertType.ERROR, "Invalid old password or empty !", ButtonType.OK).show();
                txtOldPassword.requestFocus();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
