package lk.ijse.dep.MostWantedCabs.DAO.custom;

import lk.ijse.dep.MostWantedCabs.DAO.CrudDAO;
import lk.ijse.dep.MostWantedCabs.Entity.Customer;

public interface CustomerDAO extends CrudDAO<Customer,String> {

    String getLasCustomerId()throws Exception;

}
