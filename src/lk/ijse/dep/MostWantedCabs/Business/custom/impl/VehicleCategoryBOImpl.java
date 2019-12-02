package lk.ijse.dep.MostWantedCabs.Business.custom.impl;

import lk.ijse.dep.MostWantedCabs.Business.custom.VehicleCategoryBO;
import lk.ijse.dep.MostWantedCabs.Business.exception.AlreadyExist;
import lk.ijse.dep.MostWantedCabs.DAO.DAOFactory;
import lk.ijse.dep.MostWantedCabs.DAO.DAOType;
import lk.ijse.dep.MostWantedCabs.DAO.custom.VehicleCategoryDAO;
import lk.ijse.dep.MostWantedCabs.DAO.custom.VehicleDAO;
import lk.ijse.dep.MostWantedCabs.DB.HibernateUtil;
import lk.ijse.dep.MostWantedCabs.DTO.VehicleCategoryDTO;
import lk.ijse.dep.MostWantedCabs.Entity.VehicleCategory;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class VehicleCategoryBOImpl implements VehicleCategoryBO {
    private VehicleCategoryDAO vehicleCategoryDAO = DAOFactory.getInstance().getDAO(DAOType.VEHICLE_CATEGORY);
    private VehicleDAO vehicleDAO = DAOFactory.getInstance().getDAO(DAOType.VEHICLE);


    @Override
    public void saveVehicleCategory(VehicleCategoryDTO vehicleCategory) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            vehicleCategoryDAO.setSession(session);
            session.beginTransaction();
            vehicleCategoryDAO.save(new VehicleCategory(vehicleCategory.getId(), vehicleCategory.getName(), vehicleCategory.getRentalForDay(), vehicleCategory.getRenatlForKM(), vehicleCategory.getKilometerPerDay()));
            session.getTransaction().commit();
        }
    }

    @Override
    public void updateVehicleCategory(VehicleCategoryDTO vehicleCategory) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            vehicleCategoryDAO.setSession(session);
            session.beginTransaction();
            vehicleCategoryDAO.update(new VehicleCategory(vehicleCategory.getId(), vehicleCategory.getName(), vehicleCategory.getRentalForDay(), vehicleCategory.getRenatlForKM(), vehicleCategory.getKilometerPerDay()));
            session.getTransaction().commit();
        }
    }

    @Override
    public void deleteVehicleCategory(String vehicleCategoryId) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            vehicleCategoryDAO.setSession(session);
            vehicleDAO.setSession(session);
            session.beginTransaction();
            if (vehicleDAO.existVehicleCategoryId(vehicleCategoryId)) {
                throw new AlreadyExist("Vehicle Category already exists in vehicle, hence unable to delete !");
            }
            vehicleCategoryDAO.delete(vehicleCategoryId);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<VehicleCategoryDTO> findAllVehicleCategories() throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            vehicleCategoryDAO.setSession(session);
            session.beginTransaction();
            List<VehicleCategory> vehicleCategories = vehicleCategoryDAO.findAll();
            session.getTransaction().commit();
            List<VehicleCategoryDTO> vehicleCategoryDTOS = new ArrayList<>();
            for (VehicleCategory vehicleCategory : vehicleCategories) {
                vehicleCategoryDTOS.add(new VehicleCategoryDTO(vehicleCategory.getId(), vehicleCategory.getName(), vehicleCategory.getRentalForDay(), vehicleCategory.getRenatlForKM(), vehicleCategory.getKilometerPerDay()));
            }
            return vehicleCategoryDTOS;
        }
    }

    @Override
    public String getLastVehicleCategoryId() throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            vehicleCategoryDAO.setSession(session);
            session.beginTransaction();
            String lastCategoryID = vehicleCategoryDAO.getLastCategoryID();
            session.getTransaction().commit();
            return lastCategoryID;
        }
    }

    @Override
    public VehicleCategoryDTO findVehicleCategory(String vehicleCategoryId) throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().openSession();) {
            vehicleCategoryDAO.setSession(session);
            session.beginTransaction();
            VehicleCategory vehicleCategory = vehicleCategoryDAO.find(vehicleCategoryId);
            session.getTransaction().commit();
            return new VehicleCategoryDTO(vehicleCategory.getId(), vehicleCategory.getName(), vehicleCategory.getRentalForDay(), vehicleCategory.getRenatlForKM(), vehicleCategory.getKilometerPerDay());
        }
    }

    @Override
    public List<String> getAllVehicleCategoryIds() throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().openSession();) {
            vehicleCategoryDAO.setSession(session);
            session.beginTransaction();
            List<VehicleCategory> vehicleCategories = vehicleCategoryDAO.findAll();
            session.getTransaction().commit();
            List<String> vehicleCategoryId = new ArrayList<>();
            for (VehicleCategory vehicleCategory : vehicleCategories) {
                vehicleCategoryId.add(vehicleCategory.getId());
            }
            return vehicleCategoryId;
        }
    }
}
