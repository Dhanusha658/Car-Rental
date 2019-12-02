package lk.ijse.dep.MostWantedCabs.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import lk.ijse.dep.MostWantedCabs.Business.custom.OwnerBO;
import lk.ijse.dep.MostWantedCabs.Business.custom.VehicleBO;
import lk.ijse.dep.MostWantedCabs.Business.custom.VehicleCategoryBO;
import lk.ijse.dep.MostWantedCabs.DB.HibernateUtil;
import lk.ijse.dep.MostWantedCabs.DTO.OwnerDTO;
import lk.ijse.dep.MostWantedCabs.DTO.VehicleCategoryDTO;
import lk.ijse.dep.MostWantedCabs.DTO.VehicleDTO;
import lk.ijse.dep.MostWantedCabs.Entity.Owner;
import lk.ijse.dep.MostWantedCabs.Entity.VehicleCategory;
import lk.ijse.dep.MostWantedCabs.Util.VehicleTM;
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

public class vehicles {
    public JFXTextField txtRegistationNumber;
    public JFXTextField txtModelName;
    public JFXTextField txtStatues;
    public JFXComboBox<String> cmbCategoryId;
    public JFXComboBox<String> cmbOwnerId;
    public TableView<VehicleTM> tblVehicles;
    public AnchorPane apeVehicles;
    public JFXTextField txtId;
    public JFXButton btnSave;
    public JFXButton btnDelete;
    public JFXTextField txtCategoryName;
    public JFXTextField txtOwnerName;

    private VehicleBO vehicleBO= BOFactory.getInstance().getBO(BOType.VEHICLE);
    private VehicleCategoryBO vehicleCategoryBO=BOFactory.getInstance().getBO(BOType.VEHICLE_CATEGORY);
    private OwnerBO ownerBO=BOFactory.getInstance().getBO(BOType.OWNER);

    public void initialize(){

        try {
            cmbCategoryId.getItems().clear();
            ObservableList<String> cmbCategoryIdItems = cmbCategoryId.getItems();
            List<String> categoryId=vehicleCategoryBO.getAllVehicleCategoryIds();
            for (String id : categoryId) {
                cmbCategoryIdItems.add(id);
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong,please contact service team", ButtonType.OK).show();
            Logger.getLogger("lk.ijse.dep.MostWantedCabs.Controller").log(Level.SEVERE, null, e);
        }

        try {
            cmbOwnerId.getItems().clear();
            ObservableList<String> cmbOwnerIdItems = cmbOwnerId.getItems();
            List<String>id=ownerBO.getAllOwnerIds();
            for (String ownerIds : id) {
                cmbOwnerIdItems.add(ownerIds);
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong,please contact service team", ButtonType.OK).show();
            Logger.getLogger("lk.ijse.dep.MostWantedCabs.Controller").log(Level.SEVERE, null, e);
        }

        tblVehicles.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblVehicles.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("registerNo"));
        tblVehicles.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("categoryId"));
        tblVehicles.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("ownerId"));
        tblVehicles.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("modelName"));
        tblVehicles.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("statues"));
        tblVehicles.getItems().clear();


        try {
            ObservableList<VehicleTM> tblVehiclesItems = tblVehicles.getItems();
            List<VehicleDTO>vehicleDTOS=vehicleBO.findAllVehicles();
            for (VehicleDTO vehicleDTO : vehicleDTOS) {
                tblVehiclesItems.add(new VehicleTM(vehicleDTO.getId(),vehicleDTO.getRegisterNo(),vehicleDTO.getCategoryId().getId(),vehicleDTO.getOwnerId().getId(),vehicleDTO.getModelName(),vehicleDTO.getStatues()));
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong,please contact service team", ButtonType.OK).show();
            Logger.getLogger("lk.ijse.dep.MostWantedCabs.Controller").log(Level.SEVERE, null, e);
        }

        txtId.setDisable(true);
        txtRegistationNumber.setDisable(true);
        cmbCategoryId.setDisable(true);
        cmbOwnerId.setDisable(true);
        txtModelName.setDisable(true);
        txtStatues.setDisable(true);
        txtCategoryName.setDisable(true);
        txtOwnerName.setDisable(true);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);

        tblVehicles.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<VehicleTM>() {
            @Override
            public void changed(ObservableValue<? extends VehicleTM> observable, VehicleTM oldValue, VehicleTM newValue) {
                VehicleTM selectedItem = tblVehicles.getSelectionModel().getSelectedItem();
                if(selectedItem==null){
                    btnSave.setText("Save");
                    btnDelete.setDisable(true);
                    return;
                }

                btnSave.setText("Update");
                txtRegistationNumber.setDisable(false);
                cmbCategoryId.setDisable(false);
                cmbOwnerId.setDisable(false);
                txtModelName.setDisable(false);
                btnSave.setDisable(false);
                btnDelete.setDisable(false);
                txtId.setText(selectedItem.getId());
                txtRegistationNumber.setText(selectedItem.getRegisterNo());
                cmbCategoryId.setValue(selectedItem.getCategoryId());
                cmbOwnerId.setValue(selectedItem.getOwnerId());
                txtModelName.setText(selectedItem.getModelName());
                txtStatues.setText(selectedItem.getStatues());
            }
        });

        cmbOwnerId.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                if(newValue==null){
                    return;
                }
                try {
                    OwnerDTO ownerDTO=ownerBO.findOwner(newValue);
                    txtOwnerName.setText(ownerDTO.getName());
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Something went wrong,please contact service team", ButtonType.OK).show();
                    Logger.getLogger("lk.ijse.dep.MostWantedCabs.Controller").log(Level.SEVERE, null, e);
                }
            }
        });

        cmbCategoryId.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue==null){
                    return;
                }

                try {
                    VehicleCategoryDTO vehicleCategoryDTO =vehicleCategoryBO.findVehicleCategory(newValue);
                    txtCategoryName.setText(vehicleCategoryDTO.getName());
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Something went wrong,please contact service team", ButtonType.OK).show();
                    Logger.getLogger("lk.ijse.dep.MostWantedCabs.Controller").log(Level.SEVERE, null, e);
                }
            }
        });
    }

    public void btnNewOnAction(ActionEvent actionEvent) {
        cmbOwnerId.getSelectionModel().clearSelection();
        cmbCategoryId.getSelectionModel().clearSelection();
        initialize();
        txtRegistationNumber.setDisable(false);
        cmbCategoryId.setDisable(false);
        cmbOwnerId.setDisable(false);
        txtModelName.setDisable(false);
        btnSave.setDisable(false);
        btnDelete.setDisable(false);
        txtStatues.setText("Available");
        txtModelName.clear();
        txtRegistationNumber.clear();
        txtOwnerName.clear();
        txtCategoryName.clear();


        int maxId = 0;

        try {
            String lastVehicleIdrId = vehicleBO.getLastVehicleId();
            if (lastVehicleIdrId == null) {
                maxId = 0;
            } else {
                maxId = Integer.parseInt(lastVehicleIdrId.replace("V", ""));
            }

            maxId = maxId + 1;
            String id = "";
            if (maxId < 10) {
                id = "V00" + maxId;
            } else if (maxId < 100) {
                id = "V0" + maxId;
            } else {
                id = "V" + maxId;
            }
            txtId.setText(id);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong,please contact service team", ButtonType.OK).show();
            Logger.getLogger("lk.ijse.dep.MostWantedCabs.Controller").log(Level.SEVERE, null, e);
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        if(!txtRegistationNumber.getText().matches("^[A-Z0-9]+$")){
            new Alert(Alert.AlertType.ERROR,"Invalid registration number or empty",ButtonType.OK).show();
            txtRegistationNumber.requestFocus();
            return;
        }else if(cmbCategoryId.getSelectionModel().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Please select category ID",ButtonType.OK).show();
            return;
        }else if(cmbOwnerId.getSelectionModel().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Please select owner ID",ButtonType.OK).show();
            return;
        }else if(!txtModelName.getText().matches("^[A-Za-z0-9 ]+$")){
            txtModelName.requestFocus();
            new Alert(Alert.AlertType.ERROR,"Invalid model name or empty",ButtonType.OK).show();
            return;
        }
        if(btnSave.getText().equals("Save")){
            try {
                VehicleCategoryDTO vehicleCategory = vehicleCategoryBO.findVehicleCategory(cmbCategoryId.getValue());
                VehicleCategory vehicleCategory1=new VehicleCategory(vehicleCategory.getId(),vehicleCategory.getName(),vehicleCategory.getRentalForDay(),vehicleCategory.getRenatlForKM(),vehicleCategory.getKilometerPerDay());

                OwnerDTO owner = ownerBO.findOwner(cmbOwnerId.getValue());
                Owner owner1=new Owner(owner.getId(),owner.getName(),owner.getContactNo(),owner.getAddress());

                vehicleBO.saveVehicle(new VehicleDTO(txtId.getText(),txtRegistationNumber.getText(),vehicleCategory1,txtModelName.getText(),txtStatues.getText(),owner1));
                ObservableList<VehicleTM> vehicleTMS = tblVehicles.getItems();
                vehicleTMS.add(new VehicleTM(txtId.getText(),txtRegistationNumber.getText(),cmbCategoryId.getSelectionModel().getSelectedItem(),cmbOwnerId.getSelectionModel().getSelectedItem(),txtModelName.getText(),txtStatues.getText()));
                new Alert(Alert.AlertType.INFORMATION,"New Vehicle saved successfully !", ButtonType.OK).show();
                btnNewOnAction(actionEvent);
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Something went wrong,please contact service team", ButtonType.OK).show();
                Logger.getLogger("lk.ijse.dep.MostWantedCabs.Controller").log(Level.SEVERE, null, e);
            }
        }else {
            VehicleTM selectedItem = tblVehicles.getSelectionModel().getSelectedItem();

            try {
                VehicleCategoryDTO vehicleCategory = vehicleCategoryBO.findVehicleCategory(cmbCategoryId.getValue());
                VehicleCategory vehicleCategory1=new VehicleCategory(vehicleCategory.getId(),vehicleCategory.getName(),vehicleCategory.getRentalForDay(),vehicleCategory.getRenatlForKM(),vehicleCategory.getKilometerPerDay());

                OwnerDTO owner = ownerBO.findOwner(cmbOwnerId.getValue());
                Owner owner1=new Owner(owner.getId(),owner.getName(),owner.getContactNo(),owner.getAddress());

                vehicleBO.updateVehicle(new VehicleDTO(selectedItem.getId(),txtRegistationNumber.getText(),vehicleCategory1,txtModelName.getText(),txtStatues.getText(),owner1));
                selectedItem.setRegisterNo(txtRegistationNumber.getText());
                selectedItem.setCategoryId(cmbCategoryId.getSelectionModel().getSelectedItem());
                selectedItem.setOwnerId(cmbOwnerId.getSelectionModel().getSelectedItem());
                selectedItem.setModelName(txtModelName.getText());
                tblVehicles.refresh();
                new Alert(Alert.AlertType.INFORMATION,"Vehicle details update successfully !", ButtonType.OK).show();
                btnNewOnAction(actionEvent);
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Something went wrong,please contact service team", ButtonType.OK).show();
                Logger.getLogger("lk.ijse.dep.MostWantedCabs.Controller").log(Level.SEVERE, null, e);
            }
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure whether you want to delete this vehicle?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if(buttonType.get()==ButtonType.YES) {
            VehicleTM selectedItem = tblVehicles.getSelectionModel().getSelectedItem();
            try {
                vehicleBO.deleteVehicle(selectedItem.getId());
                new Alert(Alert.AlertType.INFORMATION,"Vehicle was deleted successfully !", ButtonType.OK).show();
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
        Stage primaryStage = (Stage)(this.apeVehicles.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    public void btnReportOnAction(ActionEvent actionEvent) {

        try {
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/lk/ijse/dep/MostWantedCabs/Reports/Vehicle.jasper"));
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
