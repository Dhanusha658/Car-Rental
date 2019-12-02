package lk.ijse.dep.MostWantedCabs.DAO.custom;

import lk.ijse.dep.MostWantedCabs.DAO.CrudDAO;
import lk.ijse.dep.MostWantedCabs.Entity.Issue;

import java.util.List;

public interface IssueDAO extends CrudDAO<Issue,String> {

    String getLastIssueID()throws Exception;

    boolean existCustomerId(String customerId)throws Exception;

    boolean existVehicleId(String vehicleId)throws Exception;

    List<Issue> findallIssueIds(String statues)throws Exception;
}
