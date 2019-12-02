package lk.ijse.dep.MostWantedCabs.Controller;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.dep.MostWantedCabs.AppInitializer;
import lk.ijse.dep.MostWantedCabs.DB.HibernateUtil;
import org.hibernate.Session;

import java.io.IOException;
import java.net.URL;

public class dashBoard {
    public AnchorPane anpDashBoard;
    public ImageView imgVehicles;
    public ImageView imgDrivers;
    public ImageView imgIssuVehicle;
    public ImageView imgCustomer;
    public ImageView imgCategory;
    public ImageView imgOwner;
    public ImageView imgSearchVehicles;
    public ImageView imgReturnVehicles;

    public void initialize() throws IOException {
        if(!AppInitializer.spladhlode){
            loadSplashScreen();
        }

        FadeTransition fadeIn = new FadeTransition(Duration.millis(2000));
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }


    @FXML
    private void playMouseExitAnimation(MouseEvent event) {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();
            icon.setEffect(null);
        }
    }

    @FXML
    private void playMouseEnterAnimation(MouseEvent event) {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();

            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1.1);
            scaleT.setToY(1.1);
            scaleT.play();

            DropShadow glow = new DropShadow();
            glow.setColor(Color.DARKGRAY);
            glow.setWidth(20);
            glow.setHeight(20);
            glow.setRadius(20);
            icon.setEffect(glow);
        }
    }


    @FXML
    private void navigate(MouseEvent event) throws IOException {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();

            Parent root = null;

            FXMLLoader fxmlLoader = null;
            switch (icon.getId()) {
                case "imgVehicles":
                    root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/MostWantedCabs/View/vehicles.fxml"));
                    break;
                case "imgCategory":
                    root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/MostWantedCabs/View/vehicleCategorys.fxml"));
                    break;
                case "imgOwner":
                    root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/MostWantedCabs/View/owners.fxml"));
                    break;
                case "imgCustomer":
                    root=FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/MostWantedCabs/View/customers.fxml"));
                    break;
                case "imgIssuVehicle":
                    root=FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/MostWantedCabs/View/issueVehicles.fxml"));
                    break;
                case "imgReturnVehicles":
                    root=FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/MostWantedCabs/View/returnVehicles.fxml"));
                    break;
                case "imgSearchVehicles":
                    root=FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/MostWantedCabs/View/search.fxml"));
                    break;
                case "imgDrivers":
                    fxmlLoader = new FXMLLoader(this.getClass().getResource("/lk/ijse/dep/MostWantedCabs/View/drivers.fxml"));
                    root = fxmlLoader.load();
                    break;
            }

            if (root != null) {
                Scene subScene = new Scene(root);
                Stage primaryStage = (Stage) this.anpDashBoard.getScene().getWindow();

                primaryStage.setScene(subScene);
                primaryStage.centerOnScreen();

                TranslateTransition tt = new TranslateTransition(Duration.millis(350), subScene.getRoot());
                tt.setFromX(-subScene.getWidth());
                tt.setToX(0);
                tt.play();

            }
        }
    }

    public void loadSplashScreen() throws IOException {

        AppInitializer.spladhlode=true;

        AnchorPane root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/MostWantedCabs/View/splash.fxml"));
        anpDashBoard.getChildren().setAll(root);

        FadeTransition fadein=new FadeTransition(Duration.seconds(2),root);
        fadein.setFromValue(0);
        fadein.setToValue(1);
        fadein.setCycleCount(1);

        FadeTransition fadeout=new FadeTransition(Duration.seconds(2),root);
        fadeout.setFromValue(1);
        fadeout.setToValue(0);
        fadeout.setCycleCount(1);

        fadein.play();

        fadein.setOnFinished((event -> {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.doWork(connection -> {
                fadeout.play();
            });
            
        }));

        fadeout.setOnFinished(event -> {
            try {
                URL resource = this.getClass().getResource("/lk/ijse/dep/MostWantedCabs/View/logging.fxml");
                Parent root1 = FXMLLoader.load(resource);
                Scene scene = new Scene(root1);
                Stage primaryStage = (Stage)(this.anpDashBoard.getScene().getWindow());
                primaryStage.setScene(scene);
                primaryStage.centerOnScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    public void btnSettingsOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/lk/ijse/dep/MostWantedCabs/View/settings.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene mainScene = new Scene(root);
        Stage primaryStage=new Stage();
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Most wanted Cabs");
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
}
