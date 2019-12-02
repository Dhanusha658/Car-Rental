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
import lk.ijse.dep.MostWantedCabs.Business.custom.OwnerBO;
import lk.ijse.dep.MostWantedCabs.DB.HibernateUtil;
import lk.ijse.dep.MostWantedCabs.DTO.OwnerDTO;
import lk.ijse.dep.MostWantedCabs.Util.OwnerTM;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.hibernate.Session;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class owners {
    public AnchorPane apeOwner;
    public JFXTextField txtId;
    public JFXTextField txtFullName;
    public JFXTextField txtContactNumber;
    public JFXTextField txtAddress;
    public JFXButton btnSave;
    public JFXButton btnDelete;
    public TableView<OwnerTM> tblOwner;
    private OwnerBO ownerBO= BOFactory.getInstance().getBO(BOType.OWNER);

    public void initialize(){

        tblOwner.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblOwner.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblOwner.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("contactNo"));
        tblOwner.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblOwner.getItems().clear();

        try {
            List<OwnerDTO>ownerDTOS=ownerBO.findAllOwners();
            ObservableList<OwnerTM>ownerTMS=tblOwner.getItems();
            for (OwnerDTO ownerDTO : ownerDTOS) {
                ownerTMS.add(new OwnerTM(ownerDTO.getId(),ownerDTO.getName(),ownerDTO.getContactNo(),ownerDTO.getAddress()));
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong,please contact service team", ButtonType.OK).show();
            Logger.getLogger("lk.ijse.dep.MostWantedCabs.Controller").log(Level.SEVERE, null, e);
        }

        txtId.setDisable(true);
        txtFullName.setDisable(true);
        txtContactNumber.setDisable(true);
        txtAddress.setDisable(true);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);

        tblOwner.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<OwnerTM>() {
            @Override
            public void changed(ObservableValue<? extends OwnerTM> observable, OwnerTM oldValue, OwnerTM newValue) {
                OwnerTM selectedItem = tblOwner.getSelectionModel().getSelectedItem();
                if(selectedItem==null){
                    btnSave.setText("Save");
                    btnDelete.setDisable(true);
                    return;
                }
                btnSave.setText("Update");
                txtFullName.setDisable(false);
                txtContactNumber.setDisable(false);
                txtAddress.setDisable(false);
                btnSave.setDisable(false);
                btnDelete.setDisable(false);
                txtFullName.setText(selectedItem.getName());
                txtContactNumber.setText(String.valueOf(selectedItem.getContactNo()));
                txtAddress.setText(selectedItem.getAddress());
            }
        });

    }

    public void btnNewOnAction(ActionEvent actionEvent) {
        initialize();
        btnDelete.setDisable(true);
        txtFullName.setDisable(false);
        txtContactNumber.setDisable(false);
        txtAddress.setDisable(false);
        btnSave.setDisable(false);
        txtFullName.clear();
        txtContactNumber.clear();
        txtAddress.clear();
        int maxId = 0;

        try {
            String lastOwnerIdId = ownerBO.getLastOwnerId();
            if (lastOwnerIdId == null) {
                maxId = 0;
            } else {
                maxId = Integer.parseInt(lastOwnerIdId.replace("OW", ""));
            }

            maxId = maxId + 1;
            String id = "";
            if (maxId < 10) {
                id = "OW00" + maxId;
            } else if (maxId < 100) {
                id = "OW0" + maxId;
            } else {
                id = "OW" + maxId;
            }
            txtId.setText(id);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong,please contact service team", ButtonType.OK).show();
            Logger.getLogger("lk.ijse.dep.MostWantedCabs.Controller").log(Level.SEVERE, null, e);
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        if(!txtFullName.getText().matches("^([A-Za-z]+\\s?)+$")){
            txtFullName.requestFocus();
            new Alert(Alert.AlertType.ERROR,"Invalid owner name or empty",ButtonType.OK).show();
            return;
        }else if(!txtContactNumber.getText().matches("^[0-9]{10}$")){
            txtContactNumber.requestFocus();
            new Alert(Alert.AlertType.ERROR,"Contact number field  is empty or invalid ",ButtonType.OK).show();
            return;
        }else if (!txtAddress.getText().matches("^.+$")){
            txtAddress.requestFocus();
            new Alert(Alert.AlertType.ERROR,"Address field  is empty ",ButtonType.OK).show();
            return;
        }
        if (btnSave.getText().equals("Save")){

            try {
                ownerBO.saveOwner(new OwnerDTO(txtId.getText(),txtFullName.getText(),txtContactNumber.getText(),txtAddress.getText()));
                ObservableList<OwnerTM>ownerTMS=tblOwner.getItems();
                ownerTMS.add(new OwnerTM(txtId.getText(),txtFullName.getText(),txtContactNumber.getText(),txtAddress.getText()));
                new Alert(Alert.AlertType.INFORMATION,"New Owner saved successfully !", ButtonType.OK).show();
                btnNewOnAction(actionEvent);
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Something went wrong,please contact service team", ButtonType.OK).show();
                Logger.getLogger("lk.ijse.dep.MostWantedCabs.Controller").log(Level.SEVERE, null, e);
            }
        }else {
            OwnerTM selectedItem = tblOwner.getSelectionModel().getSelectedItem();

            try {
                ownerBO.updateOwner(new OwnerDTO(selectedItem.getId(),txtFullName.getText(),txtContactNumber.getText(),txtAddress.getText()));
                selectedItem.setAddress(txtAddress.getText());
                selectedItem.setName(txtFullName.getText());
                selectedItem.setContactNo(txtContactNumber.getText());
                tblOwner.refresh();
                new Alert(Alert.AlertType.INFORMATION," Owner Detail update successfully !", ButtonType.OK).show();
                btnNewOnAction(actionEvent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure whether you want to delete this owner?",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if(buttonType.get()==ButtonType.YES) {
            OwnerTM selectedItem = tblOwner.getSelectionModel().getSelectedItem();
            try {
                ownerBO.deleteOwner(selectedItem.getId());
                new Alert(Alert.AlertType.INFORMATION,"Owner delete successfully !", ButtonType.OK).show();
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
        Stage primaryStage = (Stage)(this.apeOwner.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    public void btnReportOnAction(ActionEvent actionEvent) {
        try {
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/lk/ijse/dep/MostWantedCabs/Reports/Ownerss.jasper"));
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
