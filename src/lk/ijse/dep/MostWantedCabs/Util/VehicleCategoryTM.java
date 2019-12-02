package lk.ijse.dep.MostWantedCabs.Util;

public class VehicleCategoryTM {
    private String id;
    private String name;
    private double rentalForDay;
    private double renatlForKM;
    private int kilometerPerDay;

    public VehicleCategoryTM(String id, String name, double rentalForDay, double renatlForKM, int kilometerPerDay) {
        this.setId(id);
        this.setName(name);
        this.setRentalForDay(rentalForDay);
        this.setRenatlForKM(renatlForKM);
        this.setKilometerPerDay(kilometerPerDay);
    }

    public VehicleCategoryTM() {
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

    @Override
    public String toString() {
        return "VehicleCategoryTM{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", rentalForDay=" + rentalForDay +
                ", renatlForKM=" + renatlForKM +
                ", kilometerPerDay=" + kilometerPerDay +
                '}';
    }
}
