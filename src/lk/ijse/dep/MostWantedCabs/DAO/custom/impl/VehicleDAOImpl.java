package lk.ijse.dep.MostWantedCabs.DAO.custom.impl;

import lk.ijse.dep.MostWantedCabs.DAO.CrudDAOImpl;
import lk.ijse.dep.MostWantedCabs.DAO.custom.VehicleDAO;
import lk.ijse.dep.MostWantedCabs.Entity.Vehicle;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAOImpl extends CrudDAOImpl<Vehicle,String> implements VehicleDAO {

    @Override
    public String getLastVehicleID() throws Exception {
       return (String) session.createNativeQuery("SELECT id FROM vehicle ORDER BY id DESC LIMIT 1").uniqueResult();
    }

    @Override
    public boolean existVehicleCategoryId(String vehicleCategoryId) throws Exception {
        return session.createNativeQuery("SELECT categoryId FROM vehicle WHERE categoryId=?1").setParameter(1,vehicleCategoryId)!=null;
    }

    @Override
    public boolean existOwnerId(String ownerId) throws Exception {
       return session.createNativeQuery("SELECT ownerId FROM vehicle WHERE ownerId=?1").setParameter(1,ownerId)!=null;
    }

    @Override
    public List<String> availableVehicles(String statues) throws Exception {
        return session.createNativeQuery("SELECT id FROM vehicle WHERE statues=?1").setParameter(1,statues).list();
    }
}
