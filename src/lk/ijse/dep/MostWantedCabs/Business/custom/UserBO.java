package lk.ijse.dep.MostWantedCabs.Business.custom;

import lk.ijse.dep.MostWantedCabs.Business.SuperBO;
import lk.ijse.dep.MostWantedCabs.DTO.UserDTO;

import java.util.List;

public interface UserBO extends SuperBO {
    UserDTO findUser (String password)throws Exception;

    void save (UserDTO userDTO)throws Exception;

    boolean existUser(String password)throws Exception;

    boolean resetPassword(String oldpassword,String newPassword) throws Exception;
}
