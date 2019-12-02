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
import lk.ijse.dep.MostWantedCabs.Entity.Issue;
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
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class returnVehicles {
    public AnchorPane apeReturnVehicles;
    public JFXTextField txtCustomerName;
    public JFXTextField txtVehicleModel;
    public TableView<ReturnInfoDTO> tblReturn;
    public JFXDatePicker dtpDate;
    public JFXComboBox<String> cmbIssueID;
    public JFXTextField txtTotalKilometers;
    public JFXTextField txtCostForDamages;
    public JFXTextField txtTotal;
    public JFXTextField txtIssueDate;
    public JFXButton btnReturn;

    private ReturnBO returnBO= BOFactory.getInstance().getBO(BOType.RETURN);
    private IssueBO issueBO=BOFactory.getInstance().getBO(BOType.ISSUE);
    private VehicleBO vehicleBO=BOFactory.getInstance().getBO(BOType.VEHICLE);
    private CustomerBO customerBO=BOFactory.getInstance().getBO(BOType.CUSTOMER);
    private VehicleCategoryBO vehicleCategoryBO=BOFactory.getInstance().getBO(BOType.VEHICLE_CATEGORY);
    String statues="Issued";
    double rentforday= 0.0;
    double rentfokm=0.0;
    int kilometerperday=0;
    int aditionaldictance=0;

    public void initialize(){

        dtpDate.setDisable(true);
        cmbIssueID.setDisable(true);
        txtIssueDate.setDisable(true);
        txtCustomerName.setDisable(true);
        txtVehicleModel.setDisable(true);
        txtTotalKilometers.setDisable(true);
        txtCostForDamages.setDisable(true);
        txtTotal.setDisable(true);
        btnReturn.setDisable(true);

        tblReturn.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("issueDate"));
        tblReturn.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        tblReturn.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("issueId"));
        tblReturn.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("additionalKilometers"));
        tblReturn.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("costOfDamage"));
        tblReturn.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("vehicleModel"));
        tblReturn.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("customerModel"));
        tblReturn.getColumns().get(7).setCellValueFactory(new PropertyValueFactory<>("total"));
        tblReturn.getItems().clear();

        try {
            List<ReturnInfoDTO>  infoDTOS=returnBO.getReturnInfo();

            ObservableList<ReturnInfoDTO> tblReturnItems = tblReturn.getItems();
            for (ReturnInfoDTO infoDTO : infoDTOS) {
                tblReturnItems.add(new ReturnInfoDTO(infoDTO.getIssueDate(),infoDTO.getReturnDate(),infoDTO.getIssueId(),infoDTO.getAdditionalKilometers(),infoDTO.getCostOfDamage(),infoDTO.getVehicleModel(),infoDTO.getCustomerModel(),
                        infoDTO.getTotal()));
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"Something went wrong,please contact service team", ButtonType.OK).show();
            Logger.getLogger("lk.ijse.dep.MostWantedCabs.Controller").log(Level.SEVERE,null,e);
        }

        try {
            cmbIssueID.getItems().clear();
            List<String>issueid=issueBO.findAllNotReturnId(statues);
            ObservableList<String> cmbIssueIDItems = cmbIssueID.getItems();
            for (String ids : issueid) {
                cmbIssueIDItems.add(ids);
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"Something went wrong,please contact service team", ButtonType.OK).show();
            Logger.getLogger("lk.ijse.dep.MostWantedCabs.Controller").log(Level.SEVERE,null,e);
        }


        txtCostForDamages.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue==null){
                    return;
                }
                txtTotalKilometers.setDisable(false);
            }
        });

        cmbIssueID.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue==null){
                    return;
                }
                try {
                    IssueDTO issue=issueBO.findIssue(newValue);
                    VehicleDTO vehicleDTO=vehicleBO.findVehicle(issue.getVehicleId().getId());
                    CustomerDTO customerDTO=customerBO.findCustomer(issue.getCustomerId().getId());
                    VehicleCategoryDTO vehicleCategoryDTO=vehicleCategoryBO.findVehicleCategory(vehicleDTO.getCategoryId().getId());

                    txtIssueDate.setText(String.valueOf(issue.getDate()));
                    txtCustomerName.setText(customerDTO.getName());
                    txtVehicleModel.setText(vehicleDTO.getModelName());
                    rentforday=vehicleCategoryDTO.getRentalForDay();
                    rentfokm=vehicleCategoryDTO.getRenatlForKM();
                    kilometerperday=vehicleCategoryDTO.getKilometerPerDay();


                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR,"Something went wrong,please contact service team", ButtonType.OK).show();
                    Logger.getLogger("lk.ijse.dep.MostWantedCabs.Controller").log(Level.SEVERE,null,e);
                }
            }
        });

        txtTotalKilometers.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(txtTotalKilometers.getText()==null||txtTotalKilometers.getText().equals("")){
                    return;
                }
                LocalDate returned = dtpDate.getValue();
                LocalDate issued =  LocalDate.parse(txtIssueDate.getText());

                Date date1 = Date.valueOf(issued);
                Date date2 = Date.valueOf(returned);


                long diff = date2.getTime() - date1.getTime();
                int diffDays = (int) (diff / (24 * 60 * 60 * 1000));

                double rentperday=rentforday;
                int damage= Integer.parseInt(txtCostForDamages.getText());
                if(diffDays>0){
                    rentperday=rentperday*diffDays;
                }
                int totalkm= Integer.parseInt(txtTotalKilometers.getText());
                double totalvalue=0.0;
                if(totalkm>kilometerperday){
                    aditionaldictance=totalkm-kilometerperday;

                    totalvalue=(aditionaldictance*rentfokm)+damage+rentperday;
                }else {
                    totalvalue=damage+rentperday;
                }
                txtTotal.setText(String.valueOf(totalvalue));
            }
        });


    }

    public void btnNewOnAction(ActionEvent actionEvent) {
        initialize();
        txtCostForDamages.setDisable(false);
        dtpDate.setDisable(false);
        cmbIssueID.setDisable(false);
        btnReturn.setDisable(false);
        cmbIssueID.getSelectionModel().clearSelection();
        txtIssueDate.clear();
        dtpDate.setValue(null);
        txtVehicleModel.clear();
        txtCustomerName.clear();
        txtTotalKilometers.clear();
        txtTotal.clear();
        txtCostForDamages.clear();

    }

    public void btnReturnOnAction(ActionEvent actionEvent) {
        if(dtpDate.getValue()==null){
            new Alert(Alert.AlertType.ERROR,"Please select a date", ButtonType.OK).show();
            dtpDate.requestFocus();
            return;
        }else if(cmbIssueID.getItems().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Please select a issue id", ButtonType.OK).show();
            cmbIssueID.requestFocus();
            return;
        }else if(!txtCostForDamages.getText().matches("^[0-9]+[.]?([0-9]*)?$")){
            new Alert(Alert.AlertType.ERROR,"Invalid input for cost fro damage field , or empty", ButtonType.OK).show();
            txtCostForDamages.requestFocus();
            return;
        }else if(!txtTotalKilometers.getText().matches("^[1-9][0-9]+$")){
            new Alert(Alert.AlertType.ERROR,"Invalid input for total kilometers field , or empty", ButtonType.OK).show();
            txtTotalKilometers.requestFocus();
            return;
        }

        try {
            IssueDTO issue = issueBO.findIssue(cmbIssueID.getValue());
            Issue issue1=new Issue(issue.getId(),issue.getDate(),issue.getVehicleId(),issue.getCustomerId(),issue.getStatues());

            returnBO.saveReturn(new ReturnDTO(issue1,Date.valueOf(dtpDate.getValue()),aditionaldictance,Double.parseDouble(txtCostForDamages.getText()),Double.parseDouble(txtTotal.getText())));
            new Alert(Alert.AlertType.INFORMATION,"Vehicle return successfully", ButtonType.OK).show();


            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/lk/ijse/dep/MostWantedCabs/Reports/invoice.jasper"));
            Map<String, Object> params = new HashMap<>();
            params.put("id",cmbIssueID.getValue());
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

            btnNewOnAction(actionEvent);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"Something went wrong,please contact service team", ButtonType.OK).show();
            Logger.getLogger("lk.ijse.dep.MostWantedCabs.Controller").log(Level.SEVERE,null,e);
        }

    }

    public void btnHomeOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/lk/ijse/dep/MostWantedCabs/View/dashBoard.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage)(this.apeReturnVehicles.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    public void btnReportOnAction(ActionEvent actionEvent) {
        try {
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/lk/ijse/dep/MostWantedCabs/Reports/ReturnVehicle.jasper"));
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
