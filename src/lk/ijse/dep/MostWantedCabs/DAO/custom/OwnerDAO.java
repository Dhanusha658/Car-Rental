package lk.ijse.dep.MostWantedCabs.DAO.custom;

import lk.ijse.dep.MostWantedCabs.DAO.CrudDAO;
import lk.ijse.dep.MostWantedCabs.Entity.Owner;

public interface OwnerDAO extends CrudDAO<Owner,String> {
    String getLastOwnerID()throws Exception;
}
