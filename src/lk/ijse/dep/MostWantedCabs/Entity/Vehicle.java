package lk.ijse.dep.MostWantedCabs.Entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Vehicle implements SuperEntity {
    @Id
    private String id;
    private String registerNo;
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REMOVE,CascadeType.REFRESH})
    @JoinColumn(name = "categoryId",referencedColumnName = "id",nullable = false)
    private
    VehicleCategory vehicleCategory;
    private String modelName;
    private String statues;
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REMOVE,CascadeType.REFRESH})
    @JoinColumn(name = "ownerId",referencedColumnName = "id",nullable = false)
    private
    Owner owner;
    @OneToMany(mappedBy = "vehicle",cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REMOVE,CascadeType.REFRESH})
    private
    List<Issue>issue;

    public Vehicle(String id, String registerNo, VehicleCategory vehicleCategory, String modelName, String statues, Owner owner) {
        this.setId(id);
        this.setRegisterNo(registerNo);
        this.setVehicleCategory(vehicleCategory);
        this.setModelName(modelName);
        this.setStatues(statues);
        this.setOwner(owner);
    }

    public Vehicle() {
    }


    public List<Issue> getIssue() {
        return issue;
    }

    public void addIssue(Issue issue) {
        issue.setVehicle(this);
        this.issue.add(issue);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegisterNo() {
        return registerNo;
    }

    public void setRegisterNo(String registerNo) {
        this.registerNo = registerNo;
    }

    public VehicleCategory getVehicleCategory() {
        return vehicleCategory;
    }

    public void setVehicleCategory(VehicleCategory vehicleCategory) {
        this.vehicleCategory = vehicleCategory;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getStatues() {
        return statues;
    }

    public void setStatues(String statues) {
        this.statues = statues;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id='" + id + '\'' +
                ", registerNo='" + registerNo + '\'' +
                ", vehicleCategory=" + vehicleCategory +
                ", modelName='" + modelName + '\'' +
                ", statues='" + statues + '\'' +
                ", owner=" + owner +
                '}';
    }
}
