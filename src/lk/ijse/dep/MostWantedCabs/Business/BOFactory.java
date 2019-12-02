package lk.ijse.dep.MostWantedCabs.Business;

import lk.ijse.dep.MostWantedCabs.Business.custom.ReturnBO;
import lk.ijse.dep.MostWantedCabs.Business.custom.impl.*;
import lk.ijse.dep.MostWantedCabs.DAO.custom.impl.UserDAOImpl;

public class BOFactory {

    public static BOFactory boFactory;

    public BOFactory() {
    }

    public static  BOFactory getInstance(){
        return(boFactory==null)?(boFactory=new BOFactory()):boFactory;
    }

    public<T extends SuperBO> T getBO(BOType boType){
        switch (boType){
            case CUSTOMER:
                return (T) new CustomerBOImpl();
            case USER:
                return (T) new UserBOImpl();
            case VEHICLE:
                return  (T) new VehicleBOImpl();
            case VEHICLE_CATEGORY:
                return (T) new VehicleCategoryBOImpl();
            case OWNER:
                return (T) new OwnerBOImpl();
            case DRIVER:
                return (T) new DriverBOImpl();
            case ISSUE:
                return (T) new IssueBOImpl();
            case RETURN:
                return (T) new RetrunBOImpl();
            default:
                return null;

        }
    }
}
