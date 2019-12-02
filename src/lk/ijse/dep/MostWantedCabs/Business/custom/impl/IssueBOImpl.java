package lk.ijse.dep.MostWantedCabs.Business.custom.impl;

import lk.ijse.dep.MostWantedCabs.Business.custom.IssueBO;
import lk.ijse.dep.MostWantedCabs.DAO.DAOFactory;
import lk.ijse.dep.MostWantedCabs.DAO.DAOType;
import lk.ijse.dep.MostWantedCabs.DAO.custom.*;
import lk.ijse.dep.MostWantedCabs.DB.HibernateUtil;
import lk.ijse.dep.MostWantedCabs.DTO.IssueDTO;
import lk.ijse.dep.MostWantedCabs.DTO.IssueInfoDTO;
import lk.ijse.dep.MostWantedCabs.Entity.*;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class IssueBOImpl implements IssueBO {

    private IssueDAO issueDAO = DAOFactory.getInstance().getDAO(DAOType.ISSUE);
    private QuaryDAO quaryDAO=DAOFactory.getInstance().getDAO(DAOType.QUARY);
    private DriverDAO driverDAO=DAOFactory.getInstance().getDAO(DAOType.DRIVER);
    private VehicleDAO vehicleDAO=DAOFactory.getInstance().getDAO(DAOType.VEHICLE);

    @Override
    public String getLastIssueId() throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().openSession();) {
            issueDAO.setSession(session);
            session.beginTransaction();
            String lastIssueID = issueDAO.getLastIssueID();
            session.getTransaction().commit();
            return lastIssueID;
            }
        }

    @Override
    public List<String> getAllIssueIds() throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().openSession();) {
            issueDAO.setSession(session);
            session.beginTransaction();
            List<Issue> issues = issueDAO.findAll();
            session.getTransaction().commit();
            List<String> issueIds = new ArrayList<>();
            for (Issue issue : issues) {
                issueIds.add(issue.getId());
            }
            return issueIds;
        }
    }

    @Override
    public void issueVehicle(IssueDTO issue) throws Exception{
        String driverId="";
        String staues="Not Available";

        try(Session session = HibernateUtil.getSessionFactory().openSession();) {
            issueDAO.setSession(session);
            driverDAO.setSession(session);
            vehicleDAO.setSession(session);
            session.beginTransaction();

            issueDAO.save(new Issue(issue.getId(), issue.getDate(), issue.getVehicleId(), issue.getCustomerId(), issue.getStatues()));

            driverId = issue.getDriverID().getId();


            if (!driverId.equals("Without Driver")) {

                Driver driver = driverDAO.find(issue.getDriverID().getId());
                issue.setDriverID(driver);

                driverDAO.update(new Driver(driver.getId(), driver.getName(), driver.getAddress(), driver.getContactNo(),
                        driver.getLicenseNo(), driver.getSalaryPerDay(), staues));
            }

            Vehicle vehicle = vehicleDAO.find(issue.getVehicleId().getId());
            vehicleDAO.update(new Vehicle(vehicle.getId(), vehicle.getRegisterNo(), vehicle.getVehicleCategory(), vehicle.getModelName(),
                    staues, vehicle.getOwner()));

            session.getTransaction().commit();
        }
    }

    @Override
    public List<String> findAllNotReturnId(String status) throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().openSession();) {
            issueDAO.setSession(session);
            session.beginTransaction();
            List<Issue> issuId = issueDAO.findallIssueIds(status);
            session.getTransaction().commit();
            List<String> id = new ArrayList<>();
            for (Issue issue : issuId) {
                id.add(issue.getId());
            }
            return id;
        }
    }

    @Override
    public boolean updateIssue(IssueInfoDTO infoDTOS) throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().openSession();) {
            quaryDAO.setSession(session);
            session.beginTransaction();
            boolean b = quaryDAO.updateStatues(new CustomEntity(infoDTOS.getId(), infoDTOS.getStatues()));
            session.getTransaction().commit();
            return b;
        }
    }

    @Override
    public IssueDTO findIssue(String issueId) throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().openSession();) {
            issueDAO.setSession(session);
            session.beginTransaction();
            Issue issue = issueDAO.find(issueId);
            session.getTransaction().commit();
            return new IssueDTO(issue.getId(), issue.getDate(), issue.getVehicle(), issue.getCustomer(), issue.getStatues());
        }
    }

    @Override
    public List<IssueInfoDTO> getIssueInfo() throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().openSession();) {
            quaryDAO.setSession(session);
            session.beginTransaction();
            List<CustomEntity> issueifo = quaryDAO.getIssueinfo();
            session.getTransaction().commit();
            List<IssueInfoDTO> issueInfoDTOS = new ArrayList<>();
            for (CustomEntity customEntity : issueifo) {
                issueInfoDTOS.add(new IssueInfoDTO(customEntity.getId(), customEntity.getDate(), customEntity.getCustomerId(), customEntity.getCustomerName(), customEntity.getVehicleId(), customEntity.getVehicleModel(), customEntity.getDriverStatues(), customEntity.getStatues()));
            }
            return issueInfoDTOS;
        }
    }
}
