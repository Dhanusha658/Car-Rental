package lk.ijse.dep.MostWantedCabs.DAO.custom;

import lk.ijse.dep.MostWantedCabs.DAO.CrudDAO;
import lk.ijse.dep.MostWantedCabs.Entity.Driver;

import java.util.List;

public interface DriverDAO extends CrudDAO<Driver,String> {
    String getLastDriverID()throws Exception;

    List<String > availableDrivers(String statues)throws Exception;

    boolean existVehicleId(String driverId)throws Exception;

    String getDriverId(String driverID)throws  Exception;

}
