package lk.ijse.dep.MostWantedCabs.Business.custom.impl;

import lk.ijse.dep.MostWantedCabs.Business.custom.VehicleBO;
import lk.ijse.dep.MostWantedCabs.Business.exception.AlreadyExist;
import lk.ijse.dep.MostWantedCabs.DAO.DAOFactory;
import lk.ijse.dep.MostWantedCabs.DAO.DAOType;
import lk.ijse.dep.MostWantedCabs.DAO.custom.IssueDAO;
import lk.ijse.dep.MostWantedCabs.DAO.custom.QuaryDAO;
import lk.ijse.dep.MostWantedCabs.DAO.custom.VehicleDAO;
import lk.ijse.dep.MostWantedCabs.DB.HibernateUtil;
import lk.ijse.dep.MostWantedCabs.DTO.VehicleDTO;
import lk.ijse.dep.MostWantedCabs.Entity.Vehicle;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class VehicleBOImpl implements VehicleBO {

    private VehicleDAO vehicleDAO = DAOFactory.getInstance().getDAO(DAOType.VEHICLE);
    private IssueDAO issueDAO = DAOFactory.getInstance().getDAO(DAOType.ISSUE);
    private QuaryDAO quaryDAO = DAOFactory.getInstance().getDAO(DAOType.QUARY);

    @Override
    public void saveVehicle(VehicleDTO vehicle) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            vehicleDAO.setSession(session);
            session.beginTransaction();
            vehicleDAO.save(new Vehicle(vehicle.getId(), vehicle.getRegisterNo(), vehicle.getCategoryId(), vehicle.getModelName(), vehicle.getStatues(), vehicle.getOwnerId()));
            session.getTransaction().commit();
        }
    }

    @Override
    public void updateVehicle(VehicleDTO vehicle) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            vehicleDAO.setSession(session);
            session.beginTransaction();
            vehicleDAO.update(new Vehicle(vehicle.getId(), vehicle.getRegisterNo(), vehicle.getCategoryId(), vehicle.getModelName(), vehicle.getStatues(), vehicle.getOwnerId()));
            session.getTransaction().commit();
        }
    }

    @Override
    public void deleteVehicle(String vehicleId) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            vehicleDAO.setSession(session);
            session.beginTransaction();
            if (issueDAO.existVehicleId(vehicleId)) {
                throw new AlreadyExist("Vehicle already exists in an Issue, hence unable to delete !");
            }
            vehicleDAO.delete(vehicleId);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<VehicleDTO> findAllVehicles() throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            vehicleDAO.setSession(session);
            session.beginTransaction();
            List<Vehicle> vehicles = vehicleDAO.findAll();
            session.getTransaction().commit();
            List<VehicleDTO> vehicleDTOS = new ArrayList<>();
            for (Vehicle vehicle : vehicles) {
                vehicleDTOS.add(new VehicleDTO(vehicle.getId(), vehicle.getRegisterNo(), vehicle.getVehicleCategory(), vehicle.getModelName(), vehicle.getStatues(), vehicle.getOwner()));
            }
            return vehicleDTOS;
        }
    }

    @Override
    public String getLastVehicleId() throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            vehicleDAO.setSession(session);
            session.beginTransaction();
            String lastVehicleID = vehicleDAO.getLastVehicleID();
            session.getTransaction().commit();
            return lastVehicleID;
        }
    }

    @Override
    public VehicleDTO findVehicle(String vehicleId) throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().openSession();) {
            vehicleDAO.setSession(session);
            session.beginTransaction();
            Vehicle vehicle = vehicleDAO.find(vehicleId);
            session.getTransaction().commit();
            return new VehicleDTO(vehicle.getId(), vehicle.getRegisterNo(), vehicle.getVehicleCategory(), vehicle.getModelName(), vehicle.getStatues(), vehicle.getOwner());
        }
    }

    @Override
    public List<String> getAllVehicleIds() throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().openSession();) {
            vehicleDAO.setSession(session);
            session.beginTransaction();
            List<Vehicle> vehicles = vehicleDAO.findAll();
            session.getTransaction().commit();
            List<String> vehicleID = new ArrayList<>();
            for (Vehicle vehicle : vehicles) {
                vehicleID.add(vehicle.getId());
            }
            return vehicleID;
        }
    }

    @Override
    public List<String> getAllAvailableVehicleIds() throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().openSession();) {
            vehicleDAO.setSession(session);
            session.beginTransaction();
            String staues = "Available";
            List<String> vehicles = vehicleDAO.availableVehicles(staues);
            session.getTransaction().commit();
            return vehicles;
        }
    }

    @Override
    public List<VehicleDTO> searchVehicle(String search) throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().openSession();) {
            quaryDAO.setSession(session);
            session.beginTransaction();
            List<Vehicle> vehicles = quaryDAO.SearchVehicle(search);
            session.getTransaction().commit();
            List<VehicleDTO> vehicleDTOS = new ArrayList<>();
            for (Vehicle vehicle : vehicles) {
                vehicleDTOS.add(new VehicleDTO(vehicle.getId(), vehicle.getRegisterNo(), vehicle.getVehicleCategory(), vehicle.getModelName(), vehicle.getStatues(), vehicle.getOwner()));
            }
            return vehicleDTOS;
        }
    }
}
