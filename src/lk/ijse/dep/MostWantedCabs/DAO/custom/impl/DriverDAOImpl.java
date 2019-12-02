package lk.ijse.dep.MostWantedCabs.DAO.custom.impl;

import lk.ijse.dep.MostWantedCabs.DAO.CrudDAOImpl;
import lk.ijse.dep.MostWantedCabs.DAO.custom.DriverDAO;
import lk.ijse.dep.MostWantedCabs.Entity.Driver;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DriverDAOImpl extends CrudDAOImpl<Driver,String> implements DriverDAO {

    @Override
    public String getLastDriverID() throws Exception {
        return (String) session.createNativeQuery("SELECT id FROM driver ORDER BY id DESC LIMIT 1").uniqueResult();
    }

    @Override
    public List<String> availableDrivers(String statues) throws Exception {
       return session.createNativeQuery("SELECT id FROM driver WHERE statues=?1").setParameter(1,statues).list();
    }

    @Override
    public boolean existVehicleId(String driverId) throws Exception {
         return session.createQuery("SELECT issue FROM Driver WHERE issue.driver.id=?1").setParameter(1,driverId) !=null;
    }

    @Override
    public String getDriverId(String issueId) throws Exception {
       return String.valueOf(session.createQuery("SELECT issue.driver.id FROM Driver WHERE issue.id=?1").setParameter(1,issueId));
    }
}
