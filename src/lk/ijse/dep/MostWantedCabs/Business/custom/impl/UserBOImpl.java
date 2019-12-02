package lk.ijse.dep.MostWantedCabs.Business.custom.impl;

import lk.ijse.dep.MostWantedCabs.Business.custom.UserBO;
import lk.ijse.dep.MostWantedCabs.DAO.DAOFactory;
import lk.ijse.dep.MostWantedCabs.DAO.DAOType;
import lk.ijse.dep.MostWantedCabs.DAO.custom.UserDAO;
import lk.ijse.dep.MostWantedCabs.DB.HibernateUtil;
import lk.ijse.dep.MostWantedCabs.DTO.UserDTO;
import lk.ijse.dep.MostWantedCabs.Entity.User;
import org.hibernate.Session;


public class UserBOImpl implements UserBO {

    UserDAO userDAO = DAOFactory.getInstance().getDAO(DAOType.USER);

    @Override
    public UserDTO findUser(String password) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            userDAO.setSession(session);
            session.beginTransaction();
            User user = userDAO.find(password);
            session.getTransaction().commit();
            return new UserDTO(user.getUserName(), user.getPassword(), user.getAddress(), user.getContactNo());
        }
    }

    @Override
    public void save(UserDTO userDTO) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            userDAO.setSession(session);
            session.beginTransaction();
            userDAO.save(new User(userDTO.getUserName(), userDTO.getPassword(), userDTO.getAddress(), userDTO.getContactNo()));
            session.getTransaction().commit();
        }
    }

    @Override
    public boolean existUser(String password) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            userDAO.setSession(session);
            session.beginTransaction();
            boolean b = userDAO.existUser(password);
            session.getTransaction().commit();
            return b;
        }
    }

    @Override
    public boolean resetPassword(String newPassword, String oldpassword) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            userDAO.setSession(session);
            session.beginTransaction();
            boolean b = userDAO.updatePassword(newPassword, oldpassword);
            session.getTransaction().commit();
            return b;
        }
    }
}
