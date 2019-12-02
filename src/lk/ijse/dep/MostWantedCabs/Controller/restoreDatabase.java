package lk.ijse.dep.MostWantedCabs.Controller;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import static lk.ijse.dep.MostWantedCabs.DB.HibernateUtil.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class restoreDatabase {
    public Pane settingsPane;
    public ProgressIndicator pgb;
    public Label lblstatues;

    public void initialize(){
        pgb.setVisible(false);
    }

    public void btnRestore_OnAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Let's restore the backup");
        fileChooser.getExtensionFilters().
                add(new FileChooser.ExtensionFilter("SQL File", "*.sql"));
        File file = fileChooser.showOpenDialog(this.settingsPane.getScene().getWindow());
        if (file != null) {

            String[] commands;
            if (getPassword().length() > 0){
                commands = new String[]{"mysql", "-h", getHost(), "-u", getUsername(),
                        "-p" + getPassword(),"--port",getPassword(), getDb(), "-e", "source " + file.getAbsolutePath()};
            }else{
                commands = new String[]{"mysql", "-h", getHost(), "-u", getUsername(),"--port",getPort(),
                        getDb(), "-e", "source " + file.getAbsolutePath()};
            }

            // Long running task == Restore
            this.settingsPane.getScene().setCursor(Cursor.WAIT);
            this.pgb.setVisible(true);
            lblstatues.setText("Please wait a moment");

            Task task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    Process process = Runtime.getRuntime().exec(commands);
                    int exitCode = process.waitFor();
                    if (exitCode != 0) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                        br.lines().forEach(System.out::println);
                        br.close();
                        throw new RuntimeException("Some things wrong , Please contact service team");
                    } else {
                        return null;
                    }
                }
            };

            task.setOnSucceeded(event -> {
                lblstatues.setText("");
                this.pgb.setVisible(false);
                this.settingsPane.getScene().setCursor(Cursor.DEFAULT);
                new Alert(Alert.AlertType.INFORMATION, "Restore process has been successfully done").show();
            });
            task.setOnFailed(event -> {
                lblstatues.setText("");
                this.pgb.setVisible(false);
                this.settingsPane.getScene().setCursor(Cursor.DEFAULT);
                new Alert(Alert.AlertType.ERROR, "Failed to restore the backup. Please contact service team").show();
            } );

            new Thread(task).start();
        }

    }
}
