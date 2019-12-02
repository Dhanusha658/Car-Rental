package lk.ijse.dep.MostWantedCabs.Entity;

import java.sql.Date;

public class CustomEntity implements SuperEntity {
    private String id;
    private Date date;
    private String customerId;
    private String customerName;
    private String vehicleId;
    private String vehicleModel;
    private String driverStatues;
    private String Statues;

    public CustomEntity(String id, Date date, String customerId, String customerName, String vehicleId, String vehicleModel, String driverStatues, String statues) {
        this.setId(id);
        this.setDate(date);
        this.setCustomerId(customerId);
        this.setCustomerName(customerName);
        this.setVehicleId(vehicleId);
        this.setVehicleModel(vehicleModel);
        this.setDriverStatues(driverStatues);
        setStatues(statues);
    }
    public CustomEntity(String id,String statues) {
        this.setId(id);
        setStatues(statues);
    }

    public CustomEntity() {
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
        return Statues;
    }

    public void setStatues(String statues) {
        Statues = statues;
    }

    @Override
    public String toString() {
        return "CustomEntity{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", vehicleId='" + vehicleId + '\'' +
                ", vehicleModel='" + vehicleModel + '\'' +
                ", driverStatues='" + driverStatues + '\'' +
                ", Statues='" + Statues + '\'' +
                '}';
    }
}
