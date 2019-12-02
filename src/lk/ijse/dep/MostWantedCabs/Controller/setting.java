package lk.ijse.dep.MostWantedCabs.Controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class setting {
    public AnchorPane apeSignIn;
    public Pane settingsPane;


    public void initialize() throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/MostWantedCabs/View/createUser.fxml"));
        settingsPane.getChildren().setAll(root);
    }

    public void createUserOnAction(ActionEvent actionEvent) throws IOException {
        initialize();
    }

    public void resetPasswordOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/MostWantedCabs/View/resetPassword.fxml"));
        settingsPane.getChildren().setAll(root);
}

    public void backupDatabaseOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/MostWantedCabs/View/backup.fxml"));
        settingsPane.getChildren().setAll(root);

    }

    public void restoreDatabaseOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/MostWantedCabs/View/restore.fxml"));
        settingsPane.getChildren().setAll(root);
    }
}
