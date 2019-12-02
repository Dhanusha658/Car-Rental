package lk.ijse.dep.MostWantedCabs.Business.custom.impl;

import lk.ijse.dep.MostWantedCabs.Business.custom.DriverBO;
import lk.ijse.dep.MostWantedCabs.Business.exception.AlreadyExist;
import lk.ijse.dep.MostWantedCabs.DAO.DAOFactory;
import lk.ijse.dep.MostWantedCabs.DAO.DAOType;
import lk.ijse.dep.MostWantedCabs.DAO.custom.DriverDAO;
import lk.ijse.dep.MostWantedCabs.DB.HibernateUtil;
import lk.ijse.dep.MostWantedCabs.DTO.DriverDTO;
import lk.ijse.dep.MostWantedCabs.Entity.Driver;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class DriverBOImpl implements DriverBO {
    private DriverDAO driverDAO= DAOFactory.getInstance().getDAO(DAOType.DRIVER);


    @Override
    public void saveDriver(DriverDTO driver) throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().openSession();) {
            driverDAO.setSession(session);
            session.beginTransaction();
            driverDAO.save(new Driver(driver.getId(), driver.getName(), driver.getAddress(), driver.getContactNo(), driver.getLicenseNo(), driver.getSalaryPerDay(), driver.getStatues()));
            session.getTransaction().commit();
        }
    }

    @Override
    public void updateDriver(DriverDTO driver) throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().openSession();) {
            driverDAO.setSession(session);
            session.beginTransaction();
            driverDAO.update(new Driver(driver.getId(), driver.getName(), driver.getAddress(), driver.getContactNo(), driver.getLicenseNo(), driver.getSalaryPerDay(), driver.getStatues()));
            session.getTransaction().commit();
        }
    }

    @Override
    public void deleteDriver(String driverId) throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().openSession();) {
            driverDAO.setSession(session);
            session.beginTransaction();
            if (driverDAO.existVehicleId(driverId)) {
                throw new AlreadyExist("Deiver already exists in an Issue detail, hence unable to delete !");
            }
            driverDAO.delete(driverId);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<DriverDTO> findAllDrivers() throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().openSession();) {
            driverDAO.setSession(session);
            session.beginTransaction();
            List<Driver> drivers = driverDAO.findAll();
            session.getTransaction().commit();
            List<DriverDTO> driverDTOS = new ArrayList<>();
            for (Driver driver : drivers) {
                driverDTOS.add(new DriverDTO(driver.getId(), driver.getName(), driver.getAddress(), driver.getContactNo(), driver.getLicenseNo(), driver.getSalaryPerDay(), driver.getStatues()));
            }
            return driverDTOS;
        }
    }

    @Override
    public String getLastDriverId() throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().openSession();) {
            driverDAO.setSession(session);
            session.beginTransaction();
            String lastDriverID = driverDAO.getLastDriverID();
            session.getTransaction().commit();
            return lastDriverID;
        }
    }

    @Override
    public DriverDTO findDriver(String driverId) throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().openSession();) {
            driverDAO.setSession(session);
            session.beginTransaction();
            Driver driver = driverDAO.find(driverId);
            session.getTransaction().commit();
            return new DriverDTO(driver.getId(), driver.getName(), driver.getAddress(), driver.getContactNo(), driver.getLicenseNo(), driver.getSalaryPerDay(), driver.getStatues());
        }
    }

    @Override
    public List<String> getAllDriverIds() throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().openSession();) {
            driverDAO.setSession(session);
            session.beginTransaction();
            List<Driver> drivers = driverDAO.findAll();
            session.getTransaction().commit();
            List<String> driverIds = new ArrayList<>();
            for (Driver driver : drivers) {
                driverIds.add(driver.getId());
            }
            return driverIds;
        }
    }

    @Override
    public List<String> getAllAvailableDrivers() throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().openSession();) {
            driverDAO.setSession(session);
            session.beginTransaction();
            String staues = "Available";
            List<String> drivers = driverDAO.availableDrivers(staues);
            session.getTransaction().commit();
            return drivers;
        }
    }
}
