package lk.ijse.dep.MostWantedCabs.DAO.custom;

import lk.ijse.dep.MostWantedCabs.DAO.SuperDAO;
import lk.ijse.dep.MostWantedCabs.Entity.CustomEntity;
import lk.ijse.dep.MostWantedCabs.Entity.CustomReturnEntity;
import lk.ijse.dep.MostWantedCabs.Entity.Vehicle;

import java.util.List;

public interface QuaryDAO extends SuperDAO {

    List<CustomEntity> getIssueinfo()throws Exception;


    boolean updateStatues(CustomEntity customEntity)throws Exception;

    List<CustomReturnEntity> getReturnInfo()throws Exception;

    List<Vehicle> SearchVehicle(String query) throws Exception;

}
