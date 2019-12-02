package lk.ijse.dep.MostWantedCabs.DAO.custom.impl;
import lk.ijse.dep.MostWantedCabs.DAO.CrudDAOImpl;
import lk.ijse.dep.MostWantedCabs.DAO.custom.IssueDAO;
import lk.ijse.dep.MostWantedCabs.Entity.Issue;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class IssueDAOImpl extends CrudDAOImpl<Issue,String> implements IssueDAO {


    @Override
    public String getLastIssueID() throws Exception {
        return (String) session.createNativeQuery("SELECT id FROM issue ORDER BY id DESC LIMIT 1").uniqueResult();
    }

    @Override
    public boolean existCustomerId(String customerId) throws Exception {
        return session.createNativeQuery("SELECT customerId FROM issue WHERE customerId=?1").setParameter(1, customerId)!=null;
    }

    @Override
    public boolean existVehicleId(String vehicleId) throws Exception {
        return session.createNativeQuery("SELECT vehicleId FROM issue WHERE vehicleId=?1").setParameter(1, vehicleId) !=null;
    }

    @Override
    public List<Issue> findallIssueIds(String statues) throws Exception {
        return session.createNativeQuery("SELECT * FROM Issue WHERE Issue.statues=?1").setParameter(1,statues).list();
    }
}
