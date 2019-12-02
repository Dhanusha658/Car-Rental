package lk.ijse.dep.MostWantedCabs.DAO.custom.impl;

import lk.ijse.dep.MostWantedCabs.DAO.CrudDAOImpl;
import lk.ijse.dep.MostWantedCabs.DAO.custom.UserDAO;
import lk.ijse.dep.MostWantedCabs.Entity.User;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl extends CrudDAOImpl<User,String> implements UserDAO {

    @Override
    public boolean updatePassword(String newpassword,String oldpassword) throws Exception {
        return session.createNativeQuery("UPDATE user SET password=?1 WHERE password=?2").setParameter(1,newpassword).setParameter(2,oldpassword)!=null;
    }

    @Override
    public boolean existUser(String password) throws Exception {
        return session.createNativeQuery("SELECT password FROM user WHERE password=?1").setParameter(1,password)!=null;
    }
}
