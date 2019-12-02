package lk.ijse.dep.MostWantedCabs.DAO.custom.impl;

import lk.ijse.dep.MostWantedCabs.DAO.CrudDAOImpl;
import lk.ijse.dep.MostWantedCabs.DAO.custom.VehicleCategoryDAO;
import lk.ijse.dep.MostWantedCabs.Entity.VehicleCategory;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class VehicleCategoryDAOImpl extends CrudDAOImpl<VehicleCategory,String> implements VehicleCategoryDAO {

    @Override
    public String getLastCategoryID() throws Exception {
        String i = (String) session.createNativeQuery("SELECT id FROM vehiclecategory ORDER BY id DESC LIMIT 1").uniqueResult();
        return (i == null) ? "0" : i;
    }
}
