package lk.ijse.dep.MostWantedCabs.Business.custom;

import lk.ijse.dep.MostWantedCabs.Business.SuperBO;
import lk.ijse.dep.MostWantedCabs.DTO.DriverDTO;

import java.util.List;

public interface DriverBO extends SuperBO {
    void saveDriver(DriverDTO driver)throws Exception;

    void updateDriver(DriverDTO driver)throws Exception;

    void deleteDriver(String driverId) throws Exception;

    List<DriverDTO> findAllDrivers() throws Exception;

    String getLastDriverId()throws Exception;

    DriverDTO findDriver(String driverId) throws Exception;

    List<String> getAllDriverIds()throws Exception;

    List<String> getAllAvailableDrivers()throws Exception;
}
