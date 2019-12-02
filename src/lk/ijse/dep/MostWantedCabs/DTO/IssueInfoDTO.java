package lk.ijse.dep.MostWantedCabs.DTO;

import java.sql.Date;

public class IssueInfoDTO {
    private String id;
    private Date date;
    private String customerId;
    private String customerName;
    private String vehicleId;
    private String vehicleModel;
    private String driverStatues;
    private String statues;

    public IssueInfoDTO(String id, Date date, String customerId, String customerName, String vehicleId, String vehicleModel, String driverStatues, String statues) {
        this.setId(id);
        this.setDate(date);
        this.setCustomerId(customerId);
        this.setCustomerName(customerName);
        this.setVehicleId(vehicleId);
        this.setVehicleModel(vehicleModel);
        this.setDriverStatues(driverStatues);
        this.setStatues(statues);
    }
    public IssueInfoDTO(String id, String statues) {
        this.setId(id);
        this.setStatues(statues);
    }

    public IssueInfoDTO() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public String getDriverStatues() {
        return driverStatues;
    }

    public void setDriverStatues(String driverStatues) {
        this.driverStatues = driverStatues;
    }

    public String getStatues() {
        return statues;
    }

    public void setStatues(String statues) {
        this.statues = statues;
    }

    @Override
    public String toString() {
        return "IssueInfoDTO{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", vehicleId='" + vehicleId + '\'' +
                ", vehicleModel='" + vehicleModel + '\'' +
                ", driverStatues='" + driverStatues + '\'' +
                ", statues='" + statues + '\'' +
                '}';
    }
}
