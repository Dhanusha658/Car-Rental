package lk.ijse.dep.MostWantedCabs.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
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
import lk.ijse.dep.MostWantedCabs.Business.custom.*;
import lk.ijse.dep.MostWantedCabs.DB.HibernateUtil;
import lk.ijse.dep.MostWantedCabs.DTO.*;
import lk.ijse.dep.MostWantedCabs.Entity.Customer;
import lk.ijse.dep.MostWantedCabs.Entity.Driver;
import lk.ijse.dep.MostWantedCabs.Entity.Vehicle;
import lk.ijse.dep.MostWantedCabs.Util.IssueTM;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.hibernate.Session;


import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class issueVehicles {
    public AnchorPane apeIssueVehicles;
    public JFXTextField txtIssueId;
    public JFXTextField txtCustomerName;
    public JFXTextField txtVehicleModel;
    public TableView<IssueTM> tblIssue;
    public JFXDatePicker dtpDate;
    public JFXComboBox<String> cmbCustomerID;
    public JFXComboBox<String> cmbVehicleID;
    public JFXComboBox<String> cmbDriverStatues;
    public JFXComboBox<String> cmbDriverID;
    public JFXButton btnIssue;

    private IssueBO issueBO= BOFactory.getInstance().getBO(BOType.ISSUE);
    private CustomerBO customerBO=BOFactory.getInstance().getBO(BOType.CUSTOMER);
    private VehicleBO vehicleBO=BOFactory.getInstance().getBO(BOType.VEHICLE);
    private DriverBO driverBO=BOFactory.getInstance().getBO(BOType.DRIVER);

    public void initialize(){

        txtIssueId.setDisable(true);
        dtpDate.setDisable(true);
        cmbCustomerID.setDisable(true);
        txtCustomerName.setDisable(true);
        cmbVehicleID.setDisable(true);
        txtVehicleModel.setDisable(true);
        cmbDriverStatues.setDisable(true);
        cmbDriverID.setDisable(true);
        btnIssue.setDisable(true);

        tblIssue.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblIssue.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("date"));
        tblIssue.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("customerId"));
        tblIssue.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("customerName"));
        tblIssue.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
        tblIssue.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("vehicleModel"));
        tblIssue.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("driverStatues"));
        tblIssue.getColumns().get(7).setCellValueFactory(new PropertyValueFactory<>("statues"));
        tblIssue.getItems().clear();

        try {
            List<IssueInfoDTO>issueInfoDTOS=issueBO.getIssueInfo();

            ObservableList<IssueTM> tblIssueItems = tblIssue.getItems();
            for (IssueInfoDTO issueInfoDTO : issueInfoDTOS) {
                String statues=issueInfoDTO.getDriverStatues();
                if(statues==null){
                    statues="Without Driver";
                }
                tblIssueItems.add(new IssueTM(issueInfoDTO.getId(),issueInfoDTO.getDate(),issueInfoDTO.getCustomerId(),issueInfoDTO.getCustomerName(),issueInfoDTO.getVehicleId(),
                        issueInfoDTO.getVehicleModel(),statues,issueInfoDTO.getStatues()));
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong,please contact service team", ButtonType.OK).show();
            Logger.getLogger("lk.ijse.dep.MostWantedCabs.Controller").log(Level.SEVERE, null, e);
        }

        cmbDriverStatues.getItems().clear();

        ObservableList<String>statues=cmbDriverStatues.getItems();
        statues.add("With Driver");
        statues.add("Without Driver");

        cmbDriverStatues.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue==null){
                    return;
                }else if(newValue.equals("With Driver")){
                    cmbDriverID.setDisable(false);
                }
            }
        });

        try {
            cmbVehicleID.getItems().clear();
            List<String>vehicleIds=vehicleBO.getAllAvailableVehicleIds();
            ObservableList<String> cmbVehicleIDItems = cmbVehicleID.getItems();
            for (String vehicleId : vehicleIds) {
                cmbVehicleIDItems.add(vehicleId);
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong,please contact service team", ButtonType.OK).show();
            Logger.getLogger("lk.ijse.dep.MostWantedCabs.Controller").log(Level.SEVERE, null, e);
        }

        cmbVehicleID.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue==null){
                    return;
                }
                try {
                    VehicleDTO vehicleDTOS=vehicleBO.findVehicle(newValue);
                    txtVehicleModel.setText(vehicleDTOS.getModelName());
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Something went wrong,please contact service team", ButtonType.OK).show();
                    Logger.getLogger("lk.ijse.dep.MostWantedCabs.Controller").log(Level.SEVERE, null, e);
                }
            }
        });

        try {
            cmbCustomerID.getItems().clear();
            List<String>customerIds=customerBO.getAllCustomerIds();
            ObservableList<String> cmbCustomerIDItems = cmbCustomerID.getItems();
            for (String cutomerids : customerIds) {
                cmbCustomerIDItems.add(cutomerids);
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong,please contact service team", ButtonType.OK).show();
            Logger.getLogger("lk.ijse.dep.MostWantedCabs.Controller").log(Level.SEVERE, null, e);
        }
        cmbCustomerID.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue==null){
                    return;
                }
                try {
                    CustomerDTO customerDTO=customerBO.findCustomer(newValue);
                    txtCustomerName.setText(customerDTO.getName());
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Something went wrong,please contact service team", ButtonType.OK).show();
                    Logger.getLogger("lk.ijse.dep.MostWantedCabs.Controller").log(Level.SEVERE, null, e);
                }
            }
        });

        try {
            cmbDriverID.getItems().clear();
            List<String>driverid=driverBO.getAllAvailableDrivers();
            ObservableList<String> cmbDriverIDItems = cmbDriverID.getItems();
            for (String id : driverid) {
                cmbDriverIDItems.add(id);
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong,please contact service team", ButtonType.OK).show();
            Logger.getLogger("lk.ijse.dep.MostWantedCabs.Controller").log(Level.SEVERE, null, e);
        }


    }

    public void btnNewOnAction(ActionEvent actionEvent) {
        initialize();
        dtpDate.setDisable(false);
        cmbCustomerID.setDisable(false);
        cmbVehicleID.setDisable(false);
        cmbDriverStatues.setDisable(false);
        dtpDate.setValue(null);
        txtVehicleModel.clear();
        txtCustomerName.clear();
        btnIssue.setDisable(false);

        int maxId = 0;

        try {
            String lastVehicleIdrId = issueBO.getLastIssueId();
            if (lastVehicleIdrId == null) {
                maxId = 0;
            } else {
                maxId = Integer.parseInt(lastVehicleIdrId.replace("I", ""));
            }

            maxId = maxId + 1;
            String id = "";
            if (maxId < 10) {
                id = "I00" + maxId;
            } else if (maxId < 100) {
                id = "I0" + maxId;
            } else {
                id = "I" + maxId;
            }
            txtIssueId.setText(id);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong,please contact service team", ButtonType.OK).show();
            Logger.getLogger("lk.ijse.dep.MostWantedCabs.Controller").log(Level.SEVERE, null, e);
        }
    }

    public void btnIssueOnAction(ActionEvent actionEvent) {
        if(dtpDate.getValue()==null){
            new Alert(Alert.AlertType.ERROR,"Date filed is empty",ButtonType.OK).show();
           return;
        }else if(cmbCustomerID.getSelectionModel().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Please select customer Id",ButtonType.OK).show();
            return;
        }else if(cmbVehicleID.getSelectionModel().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Please select vehicle Id",ButtonType.OK).show();
            return;
        }else if(cmbDriverStatues.getSelectionModel().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Please select driver statues",ButtonType.OK).show();
            return;
        }else if(cmbDriverStatues.getSelectionModel().getSelectedItem().equals("With Driver")){
            if(cmbDriverID.getSelectionModel().isEmpty()){
                new Alert(Alert.AlertType.ERROR,"Please select driver id",ButtonType.OK).show();
                return;
            }
        }
        String statues="Issued";

//        if(cmbDriverStatues.getValue().equals("With Driver")){

            try {
                CustomerDTO customer = customerBO.findCustomer(cmbCustomerID.getValue());
                Customer customer1=new Customer(customer.getId(),customer.getName(),customer.getAddress(),customer.getLicenseNo(),customer.getContactNo());

                VehicleDTO vehicle = vehicleBO.findVehicle(cmbVehicleID.getId());
                Vehicle vehicle1=new Vehicle(vehicle.getId(),vehicle.getRegisterNo(),vehicle.getCategoryId(),vehicle.getModelName(),vehicle.getStatues(),vehicle.getOwnerId());

                issueBO.issueVehicle(new IssueDTO(txtIssueId.getText(),Date.valueOf(dtpDate.getValue()),vehicle1,customer1,statues));
                new Alert(Alert.AlertType.INFORMATION,"Vehicle issued successfully",ButtonType.OK).show();
                btnNewOnAction(actionEvent);
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Something went wrong,please contact service team", ButtonType.OK).show();
                Logger.getLogger("lk.ijse.dep.MostWantedCabs.Controller").log(Level.SEVERE, null, e);
            }
//
//        }else {
//            try {
//                issueBO.issueVehicle(new IssueDTO(txtIssueId.getText(),Date.valueOf(dtpDate.getValue()),cmbVehicleID.getValue(),cmbCustomerID.getValue(),statues,withoutDriver));
//                new Alert(Alert.AlertType.INFORMATION,"Vehicle issued successfully",ButtonType.OK).show();
//                btnNewOnAction(actionEvent);
//            } catch (Exception e) {
//                new Alert(Alert.AlertType.ERROR, "Something went wrong,please contact service team", ButtonType.OK).show();
//                Logger.getLogger("lk.ijse.dep.MostWantedCabs.Controller").log(Level.SEVERE, null, e);
//            }
//
//        }

    }

    public void btnHomeOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/lk/ijse/dep/MostWantedCabs/View/dashBoard.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage)(this.apeIssueVehicles.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    public void btnReportOnAction(ActionEvent actionEvent) {
        try {
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/lk/ijse/dep/MostWantedCabs/Reports/IssuVehicle.jasper"));
            Map<String, Object> params = new HashMap<>();
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.doWork(connection -> {
                JasperPrint jasperPrint = null;
                try {
                    jasperPrint = JasperFillManager.fillReport(jasperReport, params, connection);
                } catch (JRException e) {
                    new Alert(Alert.AlertType.ERROR, "Something went wrong,please contact service team", ButtonType.OK).show();
                    Logger.getLogger("lk.ijse.dep.MostWantedCabs.Controller").log(Level.SEVERE, null, e);
                }
                JasperViewer.viewReport(jasperPrint, false);
            });

        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong,please contact service team", ButtonType.OK).show();
            Logger.getLogger("lk.ijse.dep.MostWantedCabs.Controller").log(Level.SEVERE, null, e);
        }
    }
}
