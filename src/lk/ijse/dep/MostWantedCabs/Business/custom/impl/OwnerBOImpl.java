package lk.ijse.dep.MostWantedCabs.Business.custom.impl;

import lk.ijse.dep.MostWantedCabs.Business.custom.OwnerBO;
import lk.ijse.dep.MostWantedCabs.Business.exception.AlreadyExist;
import lk.ijse.dep.MostWantedCabs.DAO.DAOFactory;
import lk.ijse.dep.MostWantedCabs.DAO.DAOType;
import lk.ijse.dep.MostWantedCabs.DAO.custom.OwnerDAO;
import lk.ijse.dep.MostWantedCabs.DAO.custom.VehicleDAO;
import lk.ijse.dep.MostWantedCabs.DB.HibernateUtil;
import lk.ijse.dep.MostWantedCabs.DTO.OwnerDTO;
import lk.ijse.dep.MostWantedCabs.Entity.Owner;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class OwnerBOImpl implements OwnerBO {
    private OwnerDAO ownerDAO = DAOFactory.getInstance().getDAO(DAOType.OWNER);
    private VehicleDAO vehicleDAO = DAOFactory.getInstance().getDAO(DAOType.VEHICLE);

    @Override
    public void saveOwner(OwnerDTO owner) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            ownerDAO.setSession(session);
            session.beginTransaction();
            ownerDAO.save(new Owner(owner.getId(), owner.getName(), owner.getContactNo(), owner.getAddress()));
            session.getTransaction().commit();
        }
    }

    @Override
    public void updateOwner(OwnerDTO owner) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            ownerDAO.setSession(session);
            session.beginTransaction();
            ownerDAO.update(new Owner(owner.getId(), owner.getName(), owner.getContactNo(), owner.getAddress()));
            session.getTransaction().commit();
        }
    }

    @Override
    public void deleteOwner(String ownerId) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            ownerDAO.setSession(session);
            vehicleDAO.setSession(session);
            session.beginTransaction();
//            if (vehicleDAO.existOwnerId(ownerId)) {
//                throw new AlreadyExist("Owner already exists in vehicle, hence unable to delete !");
//            }
            ownerDAO.delete(ownerId);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<OwnerDTO> findAllOwners() throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            ownerDAO.setSession(session);
            session.beginTransaction();
            List<Owner> owners = ownerDAO.findAll();
            session.getTransaction().commit();
            List<OwnerDTO> ownerDTOS = new ArrayList<>();
            for (Owner owner : owners) {
                ownerDTOS.add(new OwnerDTO(owner.getId(), owner.getName(), owner.getContactNo(), owner.getAddress()));
            }
            return ownerDTOS;
        }
    }

    @Override
    public String getLastOwnerId() throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            ownerDAO.setSession(session);
            session.beginTransaction();
            String lastOwnerID = ownerDAO.getLastOwnerID();
            session.getTransaction().commit();
            return lastOwnerID;
        }
    }

    @Override
    public OwnerDTO findOwner(String ownerId) throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().openSession();) {
            ownerDAO.setSession(session);
            session.beginTransaction();
            Owner owner = ownerDAO.find(ownerId);
            session.getTransaction().commit();
            return new OwnerDTO(owner.getId(), owner.getName(), owner.getContactNo(), owner.getAddress());
        }
    }

    @Override
    public List<String> getAllOwnerIds() throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().openSession();) {
            ownerDAO.setSession(session);
            session.beginTransaction();
            List<Owner> owners = ownerDAO.findAll();
            session.getTransaction().commit();
            List<String> owenerId = new ArrayList<>();
            for (Owner owner : owners) {
                owenerId.add(owner.getId());
            }
            return owenerId;
        }
    }
}
