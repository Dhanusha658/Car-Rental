package lk.ijse.dep.MostWantedCabs.DAO.custom.impl;
import lk.ijse.dep.MostWantedCabs.DAO.CrudDAOImpl;
import lk.ijse.dep.MostWantedCabs.DAO.custom.OwnerDAO;
import lk.ijse.dep.MostWantedCabs.Entity.Owner;

public class OwnerDAOImpl extends CrudDAOImpl<Owner,String> implements OwnerDAO {

    @Override
    public String getLastOwnerID() throws Exception {
       return (String) session.createNativeQuery("SELECT id FROM owner ORDER BY id DESC LIMIT 1").uniqueResult();
    }
}
