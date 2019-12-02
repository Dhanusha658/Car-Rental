package lk.ijse.dep.MostWantedCabs.DAO.custom;

import lk.ijse.dep.MostWantedCabs.DAO.CrudDAO;
import lk.ijse.dep.MostWantedCabs.Entity.Vehicle;

import java.util.List;

public interface VehicleDAO extends CrudDAO<Vehicle, String> {
    String getLastVehicleID()throws Exception;

    boolean existVehicleCategoryId(String vehicleCategoryId)throws Exception;

    boolean existOwnerId(String ownerId)throws Exception;

    List<String > availableVehicles(String statues)throws Exception;
}
