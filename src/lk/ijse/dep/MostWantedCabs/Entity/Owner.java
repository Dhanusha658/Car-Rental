package lk.ijse.dep.MostWantedCabs.Entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Owner implements SuperEntity {
    @Id
    private String id;
    private String name;
    private String contactNo;
    private String address;
    @OneToMany(mappedBy = "owner",cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REMOVE,CascadeType.REFRESH})
    private
    List<Vehicle> vehicle;

    public Owner(String id, String name, String contactNo, String address) {
        this.setId(id);
        this.setName(name);
        this.setContactNo(contactNo);
        this.setAddress(address);
    }
    public Owner(String name, String contactNo, String address) {
        this.setName(name);
        this.setContactNo(contactNo);
        this.setAddress(address);
    }

    public Owner() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Vehicle> getVehicle() {
        return vehicle;
    }

    public void addVehicle(Vehicle vehicle) {
        vehicle.setOwner(this);
        this.vehicle.add(vehicle);
    }

    @Override
    public String toString() {
        return "Owner{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", contactNo='" + contactNo + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
