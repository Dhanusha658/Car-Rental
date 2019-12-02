package lk.ijse.dep.MostWantedCabs.DAO.custom;

import lk.ijse.dep.MostWantedCabs.DAO.CrudDAO;
import lk.ijse.dep.MostWantedCabs.Entity.VehicleCategory;

public interface VehicleCategoryDAO extends CrudDAO<VehicleCategory, String> {
    String getLastCategoryID()throws Exception;
}
