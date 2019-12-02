package lk.ijse.dep.MostWantedCabs.Entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Issue implements SuperEntity{
    @Id
    private String id;
    private Date date;
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REMOVE,CascadeType.REFRESH})
    @JoinColumn(name = "vehicleId",referencedColumnName = "id",nullable = false)
    private Vehicle vehicle;
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REMOVE,CascadeType.REFRESH})
    @JoinColumn(name = "customerId",referencedColumnName = "id",nullable = false)
    private Customer customer;
    private String statues;
    @OneToOne(mappedBy = "issue",cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REMOVE,CascadeType.REFRESH})
    private
    Return return_;
    @OneToOne(mappedBy = "issue",cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REMOVE,CascadeType.REFRESH})
    private
    Driver driver;

    public Issue(String id, Date date, Vehicle vehicle, Customer customer, String statues) {
        this.setId(id);
        this.setDate(date);
        this.setVehicle(vehicle);
        this.setCustomer(customer);
        this.setStatues(statues);
    }

    public Issue() {
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

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getStatues() {
        return statues;
    }

    public void setStatues(String statues) {
        this.statues = statues;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", vehicle=" + vehicle +
                ", customer=" + customer +
                ", statues='" + statues + '\'' +
                '}';
    }

    public Return getReturn_() {
        return return_;
    }

    public void setReturn_(Return return_) {
        return_.setIssue(this);
        this.return_ = return_;
    }

    public void deleteReturn(Return return_){
        if(return_.getIssue()!=this){
            throw new RuntimeException("invalid return issue id");
        }
        return_.setIssue(null);
        this.setReturn_(null);
    }

    public Driver getDriver() {
        return driver;
    }

    public void addDriver(Driver driver) {
        driver.setIssue(this);
        this.driver = driver;
    }

    public void removeDriver(Driver driver){
        if(driver.getIssue()!=this){
            throw  new RuntimeException("Invalid driver Id");
        }
        driver.setIssue(null);
        this.addDriver(null);
    }
}
