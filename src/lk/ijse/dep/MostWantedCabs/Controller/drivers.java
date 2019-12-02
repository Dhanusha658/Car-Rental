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
import lk.ijse.dep.MostWantedCabs.Business.custom.DriverBO;
import lk.ijse.dep.MostWantedCabs.DTO.DriverDTO;
import lk.ijse.dep.MostWantedCabs.Util.DriverTM;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class drivers {
    public AnchorPane apeDrivers;
    public JFXTextField txtId;
    public JFXTextField txtFullName;
    public JFXTextField txtContactNumber;
    public JFXTextField txtAddress;
    public JFXButton btnSave;
    public TableView<DriverTM> tblDriver;
    public JFXTextField txtSalaryPerDay;
    public JFXTextField txtLicenseCardNumber;
    public JFXTextField txtStatues;
    public JFXButton btnDelete;

    private DriverBO driverBO= BOFactory.getInstance().getBO(BOType.DRIVER);

    public void initialize(){

        txtId.setDisable(true);
        txtFullName.setDisable(true);
        txtAddress.setDisable(true);
        txtContactNumber.setDisable(true);
        txtLicenseCardNumber.setDisable(true);
        txtSalaryPerDay.setDisable(true);
        txtStatues.setDisable(true);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);

        tblDriver.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblDriver.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblDriver.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblDriver.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("contactNo"));
        tblDriver.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("licenseNo"));
        tblDriver.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("salaryPerDay"));
        tblDriver.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("statues"));
        tblDriver.getItems().clear();

        try {
            List<DriverDTO>driverDTOS= driverBO.findAllDrivers();
            ObservableList<DriverTM> driverTMS = tblDriver.getItems();
            for (DriverDTO driverDTO : driverDTOS) {
                driverTMS.add(new DriverTM(driverDTO.getId(),driverDTO.getName(),driverDTO.getAddress(),driverDTO.getContactNo(),driverDTO.getLicenseNo(),driverDTO.getSalaryPerDay(),driverDTO.getStatues()));
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"Something went wrong,please contact service team", ButtonType.OK).show();
            Logger.getLogger("lk.ijse.dep.MostWantedCabs.Controller").log(Level.SEVERE,null,e);
        }
        tblDriver.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<DriverTM>() {
            @Override
            public void changed(ObservableValue<? extends DriverTM> observable, DriverTM oldValue, DriverTM newValue) {
                DriverTM selectedItem = tblDriver.getSelectionModel().getSelectedItem();
                if(selectedItem==null){
                    btnSave.setText("Save");
                    btnDelete.setDisable(true);
                    return;
                }
                btnSave.setText("Update");
                txtFullName.setDisable(false);
                txtAddress.setDisable(false);
                txtContactNumber.setDisable(false);
                txtLicenseCardNumber.setDisable(false);
                txtSalaryPerDay.setDisable(false);
                btnSave.setDisable(false);
                btnDelete.setDisable(false);
                txtId.setText(selectedItem.getId());
                txtFullName.setText(selectedItem.getName());
                txtAddress.setText(selectedItem.getAddress());
                txtContactNumber.setText(selectedItem.getContactNo());
                txtLicenseCardNumber.setText(selectedItem.getLicenseNo());
                txtSalaryPerDay.setText(String.valueOf(selectedItem.getSalaryPerDay()));
                txtStatues.setText(selectedItem.getStatues());
            }
        });

    }
    public void btnNewOnAction(ActionEvent actionEvent) {
        initialize();
        txtFullName.setDisable(false);
        txtAddress.setDisable(false);
        txtContactNumber.setDisable(false);
        txtLicenseCardNumber.setDisable(false);
        txtSalaryPerDay.setDisable(false);
        btnSave.setDisable(false);
        txtFullName.clear();
        txtAddress.clear();
        txtContactNumber.clear();
        txtLicenseCardNumber.clear();
        txtSalaryPerDay.clear();
        txtStatues.setText("Available");
        int maxId = 0;

        try {
            String lastdriverId=driverBO.getLastDriverId();
            if (lastdriverId == null) {
                maxId = 0;
            } else {
                maxId = Integer.parseInt(lastdriverId.replace("D", ""));
            }

            maxId = maxId + 1;
            String id = "";
            if (maxId < 10) {
                id = "D00" + maxId;
            } else if (maxId < 100) {
                id = "D0" + maxId;
            } else {
                id = "D" + maxId;
            }

            txtId.setText(id);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"Something went wrong,please contact service team", ButtonType.OK).show();
            Logger.getLogger("lk.ijse.dep.MostWantedCabs.Controller").log(Level.SEVERE,null,e);
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        if(!txtFullName.getText().matches("^([A-Za-z]+\\s?)+$")){
            txtFullName.requestFocus();
            new Alert(Alert.AlertType.ERROR,"Invalid Driver name or empty",ButtonType.OK).show();
            return;
        }else if(!txtAddress.getText().matches("^.+$")){
            txtAddress.requestFocus();
            new Alert(Alert.AlertType.ERROR,"Address field  is empty ",ButtonType.OK).show();
            return;
        }else if(!txtContactNumber.getText().matches("^[0-9]{10}$")){
            txtContactNumber.requestFocus();
            new Alert(Alert.AlertType.ERROR,"Contact number field  is empty or invalid ",ButtonType.OK).show();
            return;
        }else if(!txtLicenseCardNumber.getText().matches("^[A-Za-z0-9]+$")){
            txtLicenseCardNumber.requestFocus();
            new Alert(Alert.AlertType.ERROR,"License number field  is empty or invalid ",ButtonType.OK).show();
            return;
        }else if(!txtSalaryPerDay.getText().matches("^[0-9]+[.]?([0-9]*)?$")){
            txtSalaryPerDay.requestFocus();
            new Alert(Alert.AlertType.ERROR,"Salary field  is empty or invalid ",ButtonType.OK).show();
            return;
        }
        if(btnSave.getText().equals("Save")){
            try {
                driverBO.saveDriver(new DriverDTO(txtId.getText(),txtFullName.getText(),txtAddress.getText(),txtContactNumber.getText(),txtLicenseCardNumber.getText(),Double.parseDouble(txtSalaryPerDay.getText()),txtStatues.getText()));
                ObservableList<DriverTM> driverTMS = tblDriver.getItems();
                driverTMS.add(new DriverTM(txtId.getText(),txtFullName.getText(),txtAddress.getText(),txtContactNumber.getText(),txtLicenseCardNumber.getText(),Double.parseDouble(txtSalaryPerDay.getText()),txtStatues.getText()));
                btnNewOnAction(actionEvent);
                new Alert(Alert.AlertType.INFORMATION,"New driver saved successfully !", ButtonType.OK).show();
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR,"Something went wrong,please contact service team", ButtonType.OK).show();
                Logger.getLogger("lk.ijse.dep.MostWantedCabs.Controller").log(Level.SEVERE,null,e);
            }
        }else{
            DriverTM selectedItem = tblDriver.getSelectionModel().getSelectedItem();
            try {
                driverBO.updateDriver(new DriverDTO(selectedItem.getId(),txtFullName.getText(),txtAddress.getText(),txtContactNumber.getText(),txtLicenseCardNumber.getText(),Double.parseDouble(txtSalaryPerDay.getText()),txtStatues.getText()));
                selectedItem.setName(txtFullName.getText());
                selectedItem.setAddress(txtAddress.getText());
                selectedItem.setContactNo(txtContactNumber.getText());
                selectedItem.setLicenseNo(txtLicenseCardNumber.getText());
                selectedItem.setSalaryPerDay(Double.parseDouble(txtSalaryPerDay.getText()));
                tblDriver.refresh();
                new Alert(Alert.AlertType.INFORMATION,"Driver update successfully !", ButtonType.OK).show();
                btnNewOnAction(actionEvent);
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR,"Something went wrong,please contact service team", ButtonType.OK).show();
                Logger.getLogger("lk.ijse.dep.MostWantedCabs.Controller").log(Level.SEVERE,null,e);
            }
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure whether you want to delete this customer?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if(buttonType.get()==ButtonType.YES) {
            DriverTM selectedItem = tblDriver.getSelectionModel().getSelectedItem();
            try {
                driverBO.deleteDriver(selectedItem.getId());
                new Alert(Alert.AlertType.INFORMATION,"Driver delete successfully !", ButtonType.OK).show();
                btnNewOnAction(actionEvent);
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR,"Something went wrong,please contact service team", ButtonType.OK).show();
                Logger.getLogger("lk.ijse.dep.MostWantedCabs.Controller").log(Level.SEVERE,null,e);
            }

        }
    }

    public void btnHomeOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/lk/ijse/dep/MostWantedCabs/View/dashBoard.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage)(this.apeDrivers.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    public void btnReportOnAction(ActionEvent actionEvent) {
    }
}
