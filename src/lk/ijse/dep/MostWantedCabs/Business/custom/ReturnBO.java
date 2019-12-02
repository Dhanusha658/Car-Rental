package lk.ijse.dep.MostWantedCabs.Business.custom;

import lk.ijse.dep.MostWantedCabs.Business.SuperBO;
import lk.ijse.dep.MostWantedCabs.DTO.ReturnDTO;
import lk.ijse.dep.MostWantedCabs.DTO.ReturnInfoDTO;

import java.util.List;

public interface ReturnBO extends SuperBO {

    List<ReturnInfoDTO> getReturnInfo()throws Exception;

    void saveReturn(ReturnDTO returnDTO)throws Exception;
}
