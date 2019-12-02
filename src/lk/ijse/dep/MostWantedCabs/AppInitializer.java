package lk.ijse.dep.MostWantedCabs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import lk.ijse.dep.MostWantedCabs.DB.HibernateUtil;

import java.net.URL;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class AppInitializer extends Application {

    public static boolean spladhlode=false;

    public static void main(String[] args) {
        launch(args);

        HibernateUtil.getSessionFactory().close();
    }

    @Override
    public void start(Stage primaryStage)  {
        try {
            Logger rootLogger= Logger.getLogger("");
            FileHandler fileHandler=new FileHandler("exception.log",true);
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.FINEST);
            rootLogger.setLevel(Level.FINEST);
            rootLogger.addHandler(fileHandler);

            URL resource = this.getClass().getResource("/lk/ijse/dep/MostWantedCabs/View/dashBoard.fxml");
            Parent root = FXMLLoader.load(resource);
            Scene mainScene = new Scene(root);
            primaryStage.setScene(mainScene);
            primaryStage.setTitle("Most wanted Cabs");
            primaryStage.centerOnScreen();
            primaryStage.setResizable(false);
           // primaryStage.centerOnScreen();
            primaryStage.show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"Somrthing went wrong,please contact service team", ButtonType.OK).show();
            Logger.getLogger("lk.ijse.dep.MostWantedCabs").log(Level.SEVERE,null,e);
        }




    }
}
