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
import lk.ijse.dep.MostWantedCabs.Business.custom.CustomerBO;
import lk.ijse.dep.MostWantedCabs.DB.HibernateUtil;
import lk.ijse.dep.MostWantedCabs.DTO.CustomerDTO;
import lk.ijse.dep.MostWantedCabs.Util.CustomerTM;
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

public class customers {
    public AnchorPane apeCustomer;
    public JFXTextField txtId;
    public JFXTextField txtFullName;
    public JFXTextField txtContactNumber;
    public JFXTextField txtAddress;
    public JFXButton btnSave;
    public TableView<CustomerTM> tblCustomer;
    public JFXTextField txtLicenseCardNumber;
    public JFXButton btnDelete;

    public CustomerBO customerBo=BOFactory.getInstance().getBO(BOType.CUSTOMER);

    public void initialize(){

        tblCustomer.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblCustomer.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblCustomer.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblCustomer.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("licenseNo"));
        tblCustomer.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("contactNo"));
        tblCustomer.getItems().clear();

        try {
            List<CustomerDTO>customerDTOS=customerBo.findAllCustomers();
            ObservableList<CustomerTM>customerTMS =tblCustomer.getItems();
            for (CustomerDTO customerDTO : customerDTOS) {
                customerTMS.add(new CustomerTM(customerDTO.getId(),customerDTO.getName(),customerDTO.getAddress(),customerDTO.getLicenseNo(),customerDTO.getContactNo()));
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"Something went wrong,please contact service team", ButtonType.OK).show();
            Logger.getLogger("lk.ijse.dep.MostWantedCabs.Controller").log(Level.SEVERE,null,e);
        }

        txtId.setDisable(true);
        txtFullName.setDisable(true);
        txtAddress.setDisable(true);
        txtLicenseCardNumber.setDisable(true);
        txtContactNumber.setDisable(true);
        btnDelete.setDisable(true);
        btnSave.setDisable(true);
        tblCustomer.getSelectionModel().clearSelection();

        tblCustomer.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CustomerTM>() {
            @Override
            public void changed(ObservableValue<? extends CustomerTM> observable, CustomerTM oldValue, CustomerTM newValue) {
                CustomerTM selectedItem = tblCustomer.getSelectionModel().getSelectedItem();
                if(selectedItem==null){
                    btnSave.setText("Save");
                    btnDelete.setDisable(true);
                    return;
                }
                btnSave.setText("Update");
                btnDelete.setDisable(false);
                txtAddress.setDisable(false);
                txtFullName.setDisable(false);
                txtContactNumber.setDisable(false);
                txtLicenseCardNumber.setDisable(false);
                btnSave.setDisable(false);
                txtId.setText(selectedItem.getId());
                txtFullName.setText(selectedItem.getName());
                txtAddress.setText(selectedItem.getAddress());
                txtLicenseCardNumber.setText(selectedItem.getLicenseNo());
                txtContactNumber.setText(String.valueOf(selectedItem.getContactNo()));
            }
        });
    }

    public void btnNewOnAction(ActionEvent actionEvent) {
        initialize();
        txtFullName.clear();
        txtAddress.clear();
        txtContactNumber.clear();
        txtLicenseCardNumber.clear();
        tblCustomer.getSelectionModel().clearSelection();
        btnSave.setDisable(false);
        txtAddress.setDisable(false);
        txtFullName.setDisable(false);
        txtContactNumber.setDisable(false);
        txtLicenseCardNumber.setDisable(false);
        txtFullName.requestFocus();
        int maxId = 0;

        try {
            String lastCustomerId=customerBo.getLastCustomerId();
            if (lastCustomerId == null) {
                maxId = 0;
            } else {
                maxId = Integer.parseInt(lastCustomerId.replace("C", ""));
            }

            maxId = maxId + 1;
            String id = "";
            if (maxId < 10) {
                id = "C00" + maxId;
            } else if (maxId < 100) {
                id = "C0" + maxId;
            } else {
                id = "C" + maxId;
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
            new Alert(Alert.AlertType.ERROR,"Invalid customer name or empty",ButtonType.OK).show();
            return;
        } else if (!txtAddress.getText().matches("^.+$")){
            txtAddress.requestFocus();
            new Alert(Alert.AlertType.ERROR,"Address field  is empty ",ButtonType.OK).show();
            return;
        } else if(!txtContactNumber.getText().matches("^[0-9]{10}$")){
            txtContactNumber.requestFocus();
            new Alert(Alert.AlertType.ERROR,"Contact number field  is empty or invalid ",ButtonType.OK).show();
            return;
        }else if (!txtLicenseCardNumber.getText().matches("^[A-Za-z0-9]+$")){
            txtLicenseCardNumber.requestFocus();
            new Alert(Alert.AlertType.ERROR,"License number field  is empty or invalid ",ButtonType.OK).show();
            return;
        }

        if(btnSave.getText().equals("Save")){

            CustomerDTO customerDTO=new CustomerDTO(txtId.getText(),txtFullName.getText(),txtAddress.getText(),txtLicenseCardNumber.getText(),txtContactNumber.getText());
            System.out.println(txtId.getText());

            try {
                customerBo.saveCustomer(customerDTO);
                ObservableList<CustomerTM>customerTMS=tblCustomer.getItems();

                customerTMS.add(new CustomerTM(txtId.getText(),txtFullName.getText(),txtAddress.getText(),txtLicenseCardNumber.getText(),txtContactNumber.getText()));
                new Alert(Alert.AlertType.INFORMATION,"New Customer saved successfully !", ButtonType.OK).show();
                btnNewOnAction(actionEvent);
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR,"Something went wrong,please contact service team", ButtonType.OK).show();
                Logger.getLogger("lk.ijse.dep.MostWantedCabs.Controller").log(Level.SEVERE,null,e);
            }

        }else {
            CustomerTM selectedItem = tblCustomer.getSelectionModel().getSelectedItem();

            try {
                customerBo.updateCustomer(new CustomerDTO(selectedItem.getId(),txtFullName.getText(),txtAddress.getText(),txtLicenseCardNumber.getText(),txtContactNumber.getText()));
                selectedItem.setName(txtFullName.getText());
                selectedItem.setAddress(txtAddress.getText());
                selectedItem.setLicenseNo(txtLicenseCardNumber.getText());
                selectedItem.setContactNo(txtContactNumber.getText());
                tblCustomer.refresh();
                new Alert(Alert.AlertType.INFORMATION,"Customer details update successfully !", ButtonType.OK).show();
                btnNewOnAction(actionEvent);

            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR,"Something went wrong,please contact service team", ButtonType.OK).show();
                Logger.getLogger("lk.ijse.dep.MostWantedCabs.Controller").log(Level.SEVERE,null,e);
            }

        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure whether you want to delete this customer?",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if(buttonType.get()==ButtonType.YES) {
            CustomerTM selectedItem = tblCustomer.getSelectionModel().getSelectedItem();
            try {
                customerBo.deleteCustomer(selectedItem.getId());
                new Alert(Alert.AlertType.INFORMATION,"Customer delete successfully !", ButtonType.OK).show();
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
        Stage primaryStage = (Stage)(this.apeCustomer.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    public void btnReportOnAction(ActionEvent actionEvent) {

        try {
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/lk/ijse/dep/MostWantedCabs/Reports/CustomersRPT.jasper"));
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
