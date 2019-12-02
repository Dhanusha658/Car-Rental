package lk.ijse.dep.MostWantedCabs.Entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class VehicleCategory implements SuperEntity {
    @Id
    private String id;
    private String name;
    private double rentalForDay;
    private double renatlForKM;
    private int kilometerPerDay;
    @OneToMany(mappedBy = "vehicleCategory",cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REMOVE,CascadeType.REFRESH})
    private
    List<Vehicle>vehicle;

    public VehicleCategory(String id, String name, double rentalForDay, double renatlForKM, int kilometerPerDay) {
        this.setId(id);
        this.setName(name);
        this.setRentalForDay(rentalForDay);
        this.setRenatlForKM(renatlForKM);
        this.setKilometerPerDay(kilometerPerDay);
    }
    public VehicleCategory( String name, double rentalForDay, double renatlForKM, int kilometerPerDay) {
        this.setName(name);
        this.setRentalForDay(rentalForDay);
        this.setRenatlForKM(renatlForKM);
        this.setKilometerPerDay(kilometerPerDay);
    }


    public VehicleCategory() {
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

    public double getRentalForDay() {
        return rentalForDay;
    }

    public void setRentalForDay(double rentalForDay) {
        this.rentalForDay = rentalForDay;
    }

    public double getRenatlForKM() {
        return renatlForKM;
    }

    public void setRenatlForKM(double renatlForKM) {
        this.renatlForKM = renatlForKM;
    }

    public int getKilometerPerDay() {
        return kilometerPerDay;
    }

    public void setKilometerPerDay(int kilometerPerDay) {
        this.kilometerPerDay = kilometerPerDay;
    }

    public List<Vehicle> getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        vehicle.setVehicleCategory(this);
        this.vehicle.add(vehicle);
    }

    @Override
    public String toString() {
        return "VehicleCategory{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", rentalForDay=" + rentalForDay +
                ", renatlForKM=" + renatlForKM +
                ", kilometerPerDay=" + kilometerPerDay +
                '}';
    }
}
