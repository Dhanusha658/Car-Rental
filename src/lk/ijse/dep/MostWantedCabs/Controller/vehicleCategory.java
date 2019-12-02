package lk.ijse.dep.MostWantedCabs.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dep.MostWantedCabs.Business.BOFactory;
import lk.ijse.dep.MostWantedCabs.Business.BOType;
import lk.ijse.dep.MostWantedCabs.Business.custom.VehicleCategoryBO;
import lk.ijse.dep.MostWantedCabs.DB.HibernateUtil;
import lk.ijse.dep.MostWantedCabs.DTO.VehicleCategoryDTO;
import lk.ijse.dep.MostWantedCabs.Util.VehicleCategoryTM;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.hibernate.Session;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class vehicleCategory {
    public JFXTextField txtId;
    public JFXTextField txtCategoryName;
    public JFXTextField txtRentalPerDay;
    public JFXTextField txtRentalPerKilometer;
    public JFXButton btnSave;
    public AnchorPane apeCategory;
    public JFXTextField txtKilometresPerDay;
    public TableView<VehicleCategoryTM> tblCategory;
    public JFXButton btnDelete;

    private VehicleCategoryBO vehicleCategoryBO = BOFactory.getInstance().getBO(BOType.VEHICLE_CATEGORY);

    public void initialize() {
        tblCategory.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblCategory.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblCategory.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("rentalForDay"));
        tblCategory.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("renatlForKM"));
        tblCategory.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("kilometerPerDay"));
        tblCategory.getItems().clear();

        try {
            List<VehicleCategoryDTO> vehicleCategoryDTOS = vehicleCategoryBO.findAllVehicleCategories();
            ObservableList<VehicleCategoryTM> vehicleCategoryTMS = tblCategory.getItems();
            for (VehicleCategoryDTO vehicleCategoryDTO : vehicleCategoryDTOS) {
                vehicleCategoryTMS.add(new VehicleCategoryTM(vehicleCategoryDTO.getId(), vehicleCategoryDTO.getName(), vehicleCategoryDTO.getRentalForDay(), vehicleCategoryDTO.getRenatlForKM(),vehicleCategoryDTO.getKilometerPerDay()));
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong,please contact service team", ButtonType.OK).show();
            Logger.getLogger("lk.ijse.dep.MostWantedCabs.Controller").log(Level.SEVERE, null, e);
        }

        txtId.setDisable(true);
        txtCategoryName.setDisable(true);
        txtKilometresPerDay.setDisable(true);
        txtRentalPerKilometer.setDisable(true);
        txtRentalPerDay.setDisable(true);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);

        tblCategory.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<VehicleCategoryTM>() {
            @Override
            public void changed(ObservableValue<? extends VehicleCategoryTM> observable, VehicleCategoryTM oldValue, VehicleCategoryTM newValue) {
                VehicleCategoryTM selectedItem = tblCategory.getSelectionModel().getSelectedItem();
                if (selectedItem == null) {
                    btnSave.setText("Save");
                    btnDelete.setDisable(true);
                    return;
                }
                btnSave.setText("Update");
                txtCategoryName.setDisable(false);
                txtKilometresPerDay.setDisable(false);
                txtRentalPerKilometer.setDisable(false);
                txtRentalPerDay.setDisable(false);
                btnSave.setDisable(false);
                btnDelete.setDisable(false);
                txtCategoryName.setText(selectedItem.getName());
                txtRentalPerDay.setText(String.valueOf(selectedItem.getRentalForDay()));
                txtRentalPerKilometer.setText(String.valueOf(selectedItem.getRenatlForKM()));
                txtKilometresPerDay.setText(String.valueOf(selectedItem.getKilometerPerDay()));

            }
        });

    }

    public void btnNewOnAction(ActionEvent actionEvent) {
        initialize();
        txtCategoryName.setDisable(false);
        txtKilometresPerDay.setDisable(false);
        txtRentalPerKilometer.setDisable(false);
        btnSave.setDisable(false);
        txtRentalPerDay.setDisable(false);
        btnDelete.setDisable(true);
        txtCategoryName.clear();
        txtKilometresPerDay.clear();
        txtRentalPerKilometer.clear();
        txtRentalPerDay.clear();
        txtCategoryName.requestFocus();

        int maxId = 0;

        try {
            String lastCustomerId = vehicleCategoryBO.getLastVehicleCategoryId();
            if (lastCustomerId == null) {
                maxId = 0;
            } else {
                maxId = Integer.parseInt(lastCustomerId.replace("VC", ""));
            }

            maxId = maxId + 1;
            String id = "";
            if (maxId < 10) {
                id = "VC00" + maxId;
            } else if (maxId < 100) {
                id = "VC0" + maxId;
            } else {
                id = "VC" + maxId;
            }
            txtId.setText(id);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong,please contact service team", ButtonType.OK).show();
            Logger.getLogger("lk.ijse.dep.MostWantedCabs.Controller").log(Level.SEVERE, null, e);
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        if (!txtCategoryName.getText().matches("^([A-Za-z]+\\s?)+$")) {
            txtCategoryName.requestFocus();
            new Alert(Alert.AlertType.ERROR, "Invalid Category name or empty", ButtonType.OK).show();
            return;
        } else if (!txtKilometresPerDay.getText().matches("^[0-9]+$")) {
            txtKilometresPerDay.requestFocus();
            new Alert(Alert.AlertType.ERROR, "Invalid  kilometer count or empty", ButtonType.OK).show();
            return;
        } else if (!txtRentalPerKilometer.getText().matches("^[0-9]+[.]?([0-9]*)?$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid value for rental per kilometer , or empty", ButtonType.OK).show();
            return;
        } else if (!txtRentalPerDay.getText().matches("^[0-9]+[.]?([0-9]*)?$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid value for rental per day,  or empty", ButtonType.OK).show();
            return;
        }
        if (btnSave.getText().equals("Save")) {
            try {
                vehicleCategoryBO.saveVehicleCategory(new VehicleCategoryDTO(txtId.getText(), txtCategoryName.getText(), Double.parseDouble(txtRentalPerDay.getText()), Double.parseDouble(txtRentalPerKilometer.getText()),Integer.parseInt(txtKilometresPerDay.getText())));
                ObservableList<VehicleCategoryTM> vehicleCategoryTMS = tblCategory.getItems();
                vehicleCategoryTMS.add(new VehicleCategoryTM(txtId.getText(), txtCategoryName.getText(), Double.parseDouble(txtRentalPerDay.getText()), Double.parseDouble(txtRentalPerKilometer.getText()),Integer.parseInt(txtKilometresPerDay.getText())));
                new Alert(Alert.AlertType.INFORMATION, "Vehicle Category save successfully !", ButtonType.OK).show();
                btnNewOnAction(actionEvent);
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Something went wrong,please contact service team", ButtonType.OK).show();
                Logger.getLogger("lk.ijse.dep.MostWantedCabs.Controller").log(Level.SEVERE, null, e);
            }

        } else {
            VehicleCategoryTM selectedItem = tblCategory.getSelectionModel().getSelectedItem();
            try {
                vehicleCategoryBO.updateVehicleCategory(new VehicleCategoryDTO(selectedItem.getId(), txtCategoryName.getText(), Double.parseDouble(txtRentalPerDay.getText()), Double.parseDouble(txtRentalPerKilometer.getText()),Integer.parseInt(txtKilometresPerDay.getText())));
                selectedItem.setName(txtCategoryName.getText());
                selectedItem.setRentalForDay(Double.parseDouble(txtRentalPerDay.getText()));
                selectedItem.setRenatlForKM(Double.parseDouble(txtRentalPerKilometer.getText()));
                selectedItem.setKilometerPerDay(Integer.parseInt(txtKilometresPerDay.getText()));
                tblCategory.refresh();
                new Alert(Alert.AlertType.INFORMATION, "Vehicle Category details update successfully !", ButtonType.OK).show();
                btnNewOnAction(actionEvent);
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Something went wrong,please contact service team", ButtonType.OK).show();
                Logger.getLogger("lk.ijse.dep.MostWantedCabs.Controller").log(Level.SEVERE, null, e);
            }
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure whether you want to delete this vehicle category?",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if(buttonType.get()==ButtonType.YES){
            VehicleCategoryTM selectedItem = tblCategory.getSelectionModel().getSelectedItem();
            try {
                vehicleCategoryBO.deleteVehicleCategory(selectedItem.getId());
                new Alert(Alert.AlertType.INFORMATION, "Vehicle Category details delete successfully !", ButtonType.OK).show();
                btnNewOnAction(actionEvent);
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Something went wrong,please contact service team", ButtonType.OK).show();
                Logger.getLogger("lk.ijse.dep.MostWantedCabs.Controller").log(Level.SEVERE, null, e);
            }
        }
    }

    public void btnHomeOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/lk/ijse/dep/MostWantedCabs/View/dashBoard.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.apeCategory.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    public void btnReportOnAction(ActionEvent actionEvent) {
        try {
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/lk/ijse/dep/MostWantedCabs/Reports/VehicleCategorys.jasper"));
            Map<String, Object> params = new HashMap<>();
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.doWork(connection -> {
                JasperPrint jasperPrint = null;
                try {
                    jasperPrint = JasperFillManager.fillReport(jasperReport, params, connection);
                } catch (JRException e) {
                    e.printStackTrace();
                }
                JasperViewer.viewReport(jasperPrint, false);
            });
        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong,please contact service team", ButtonType.OK).show();
            Logger.getLogger("lk.ijse.dep.MostWantedCabs.Controller").log(Level.SEVERE, null, e);
        }
    }
}
