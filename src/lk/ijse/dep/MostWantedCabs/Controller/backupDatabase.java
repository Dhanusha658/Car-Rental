package lk.ijse.dep.MostWantedCabs.Controller;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import lk.ijse.dep.MostWantedCabs.DB.HibernateUtil;
import static lk.ijse.dep.MostWantedCabs.DB.HibernateUtil.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class backupDatabase {
    public ProgressIndicator pgb;
    public Label lblstatues;
    public Pane pneBackup;

    public void initialize(){
        pgb.setVisible(false);
    }

    public void btnBackup_OnAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save the DB Backup");
        fileChooser.getExtensionFilters().
                add(new FileChooser.ExtensionFilter("SQL File", "*.sql"));
        File file = fileChooser.showSaveDialog(this.pneBackup.getScene().getWindow());
        if (file != null) {

            // Now, we have to backup the DB...
            // Long running task == We have to backup
            this.pneBackup.getScene().setCursor(Cursor.WAIT);
            this.pgb.setVisible(true);
            lblstatues.setText("Please wait a moment ");

            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {

                    String[] commands;
                    if (getPassword().length() > 0){
                        commands = new String[]{"mysqldump", "-h", getHost(), "-u", getUsername(),
                                "-p" + getPassword(),"--port",getPort(), getDb(), "--result-file", file.getAbsolutePath() + ((file.getAbsolutePath().endsWith(".sql")) ? "" : ".sql")};
                    }else{
                        commands = new String[]{"mysqldump", "-h", getHost(), "-u", getUsername(), "--port",getPort(),
                                getDb(), "--result-file", file.getAbsolutePath() + ((file.getAbsolutePath().endsWith(".sql")) ? "" : ".sql")};
                    }

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
                this.pneBackup.getScene().setCursor(Cursor.DEFAULT);
                new Alert(Alert.AlertType.INFORMATION,"Backup process has been done successfully").show();
            });

            task.setOnFailed(event -> {
                lblstatues.setText("");
                this.pgb.setVisible(false);
                this.pneBackup.getScene().setCursor(Cursor.DEFAULT);
                new Alert(Alert.AlertType.ERROR,"Failed to back up. Please contact service team").show();
            });

            new Thread(task).start();
        }
    }
}
