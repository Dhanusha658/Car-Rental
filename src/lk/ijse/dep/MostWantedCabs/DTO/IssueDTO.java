package lk.ijse.dep.MostWantedCabs.DTO;

import lk.ijse.dep.MostWantedCabs.Entity.Customer;
import lk.ijse.dep.MostWantedCabs.Entity.Driver;
import lk.ijse.dep.MostWantedCabs.Entity.Vehicle;

import java.sql.Date;

public class IssueDTO {
    private String id;
    private Date date;
    private Vehicle vehicleId;
    private Customer customerId;
    private String statues;
    private Driver driverID;

    public IssueDTO(String id, Date date, Vehicle vehicleId, Customer customerId, String statues, Driver driverID) {
        this.id = id;
        this.date = date;
        this.vehicleId = vehicleId;
        this.customerId = customerId;
        this.statues = statues;
        this.setDriverID(driverID);
    }

    public IssueDTO(String id, Date date, Vehicle vehicleId, Customer customerId, String statues) {
        this.setId(id);
        this.setDate(date);
        this.setVehicleId(vehicleId);
        this.setCustomerId(customerId);
        this.setStatues(statues);
    }
    public IssueDTO(Date date, Vehicle vehicleId, Customer customerId, String statues) {
        this.setDate(date);
        this.setVehicleId(vehicleId);
        this.setCustomerId(customerId);
        this.setStatues(statues);
    }

    public IssueDTO() {
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

    public Vehicle getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Vehicle vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Customer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Customer customerId) {
        this.customerId = customerId;
    }

    public String getStatues() {
        return statues;
    }

    public void setStatues(String statues) {
        this.statues = statues;
    }


    @Override
    public String toString() {
        return "IssueDTO{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", vehicleId='" + vehicleId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", statues='" + statues + '\'' +
                '}';
    }

    public Driver getDriverID() {
        return driverID;
    }

    public void setDriverID(Driver driver) {
        this.driverID = driver;
    }
}
