package lk.ijse.dep.MostWantedCabs.DAO.custom;

import lk.ijse.dep.MostWantedCabs.DAO.CrudDAO;
import lk.ijse.dep.MostWantedCabs.Entity.User;

public interface UserDAO extends CrudDAO<User,String> {
    boolean updatePassword(String oldpassowrd,String newpass)throws Exception;

    boolean existUser(String password)throws Exception;
}
