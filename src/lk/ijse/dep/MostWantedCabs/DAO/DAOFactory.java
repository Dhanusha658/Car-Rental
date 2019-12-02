package lk.ijse.dep.MostWantedCabs.DAO;

import lk.ijse.dep.MostWantedCabs.DAO.custom.impl.*;

public class DAOFactory {

    private static  DAOFactory daoFactory;

    public DAOFactory() {
    }

    public static DAOFactory getInstance(){
        return (daoFactory==null)?(daoFactory=new DAOFactory()):daoFactory;
    }

    public  <T extends SuperDAO>T getDAO(DAOType daoType){
        switch (daoType){
            case CUSTOMER:
                return (T) new CustomerDAOImpl();
            case ISSUE:
                return (T) new IssueDAOImpl();
            case DRIVER:
                return (T) new DriverDAOImpl();
            case OWNER:
                return (T) new OwnerDAOImpl();
            case RETURN:
                return (T) new ReturnDAOImpl();
            case VEHICLE_CATEGORY:
                return  (T) new VehicleCategoryDAOImpl();
            case VEHICLE:
                return  (T) new VehicleDAOImpl();
            case USER:
                return (T) new UserDAOImpl();
            case QUARY:
                return (T) new QuaryDAOImpl();
            default:
                return null;
        }
    }
}
