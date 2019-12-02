package lk.ijse.dep.MostWantedCabs.Controller;

import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class splash implements Initializable {
    public Circle c1;
    public Circle c2;
    public Circle c3;
    public AnchorPane apeSplash;
    public Label lblLoding;
    int rotate=0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(2000));
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        setRotate(c1,true,360,10);
        setRotate(c2,true,180,18);
        setRotate(c3,true,145,24);
    }


     public void setRotate(Circle c,boolean reverse,int angle,int duration){

         RotateTransition rotateTransition = new RotateTransition(Duration.seconds(duration), c);

         rotateTransition.setAutoReverse(reverse);
         rotateTransition.setByAngle(angle);
         rotateTransition.setDelay(Duration.seconds(0));
         rotateTransition.setRate(3);
         rotateTransition.setCycleCount(18);
         rotateTransition.play();

     }

     public Stage loadDashbord() throws IOException {
         URL resource = this.getClass().getResource("/lk/ijse/dep/MostWantedCabs/View/dashBoard.fxml");
         Parent root = FXMLLoader.load(resource);
         Scene scene = new Scene(root);
         Stage primaryStage = (Stage)(this.apeSplash.getScene().getWindow());
         primaryStage.setScene(scene);
         primaryStage.centerOnScreen();
         return primaryStage;
     }
}

