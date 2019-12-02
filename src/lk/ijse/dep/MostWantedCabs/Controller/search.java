package lk.ijse.dep.MostWantedCabs.Controller;

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
import lk.ijse.dep.MostWantedCabs.Business.custom.VehicleBO;
import lk.ijse.dep.MostWantedCabs.DB.HibernateUtil;
import lk.ijse.dep.MostWantedCabs.DTO.VehicleDTO;
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
import java.util.logging.Level;
import java.util.logging.Logger;

public class search {
    public AnchorPane apeSearch;
    public JFXTextField txtSearch;
    public TableView<VehicleTM> tblSearch;

    private VehicleBO vehicleBO= BOFactory.getInstance().getBO(BOType.VEHICLE);

    public  void initialize(){

        tblSearch.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblSearch.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("registerNo"));
        tblSearch.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("categoryId"));
        tblSearch.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("modelName"));
        tblSearch.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("statues"));
        tblSearch.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("ownerId"));
        tblSearch.getItems().clear();

        try {
            List<VehicleDTO>vehicleDTOS=vehicleBO.findAllVehicles();
            ObservableList<VehicleTM> tblSearchItems = tblSearch.getItems();
            for (VehicleDTO vehicleDTO : vehicleDTOS) {
                tblSearchItems.add(new VehicleTM(vehicleDTO.getId(),vehicleDTO.getRegisterNo(),vehicleDTO.getCategoryId().getId(),vehicleDTO.getModelName(),vehicleDTO.getStatues(),vehicleDTO.getOwnerId().getId()));
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"Something went wrong,please contact service team", ButtonType.OK).show();
            Logger.getLogger("lk.ijse.dep.MostWantedCabs.Controller").log(Level.SEVERE,null,e);
        }

        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                tblSearch.getItems().clear();
                String searchText=txtSearch.getText();

                if(txtSearch.getText()==null||txtSearch.getText().equals("")){
                    try {

                        List<VehicleDTO>vehicleDTOS=vehicleBO.findAllVehicles();
                        ObservableList<VehicleTM> tblSearchItems = tblSearch.getItems();
                        for (VehicleDTO vehicleDTO : vehicleDTOS) {
                            tblSearchItems.add(new VehicleTM(vehicleDTO.getId(),vehicleDTO.getRegisterNo(),vehicleDTO.getCategoryId().getId(),vehicleDTO.getModelName(),vehicleDTO.getStatues(),vehicleDTO.getOwnerId().getId()));
                        }
                    } catch (Exception e) {
                        new Alert(Alert.AlertType.ERROR,"Something went wrong,please contact service team", ButtonType.OK).show();
                        Logger.getLogger("lk.ijse.dep.MostWantedCabs.Controller").log(Level.SEVERE,null,e);
                    }
                  return;
                }
                String search="%"+searchText+"%";

                try {
                    List<VehicleDTO>vehicleDTOS=vehicleBO.searchVehicle(search);
                    ObservableList<VehicleTM> tblSearchItems = tblSearch.getItems();
                    for (VehicleDTO vehicleDTO : vehicleDTOS) {
                        tblSearchItems.add(new VehicleTM(vehicleDTO.getId(),vehicleDTO.getRegisterNo(),vehicleDTO.getCategoryId().getId(),vehicleDTO.getModelName(),vehicleDTO.getStatues(),vehicleDTO.getOwnerId().getId()));
                    }
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR,"Something went wrong,please contact service team", ButtonType.OK).show();
                    Logger.getLogger("lk.ijse.dep.MostWantedCabs.Controller").log(Level.SEVERE,null,e);
                }


            }
        });

    }

    public void btnHomeOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/lk/ijse/dep/MostWantedCabs/View/dashBoard.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage)(this.apeSearch.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    public void btnReportOnAction(ActionEvent actionEvent) {
        String search="%"+txtSearch.getText()+"%";
        try {
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("/lk/ijse/dep/MostWantedCabs/Reports/SearchVehicles.jasper"));
            Map<String, Object> params = new HashMap<>();
            params.put("search",search);
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
