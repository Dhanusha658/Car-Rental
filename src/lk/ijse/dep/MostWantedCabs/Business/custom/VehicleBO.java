package lk.ijse.dep.MostWantedCabs.Business.custom;

import lk.ijse.dep.MostWantedCabs.Business.SuperBO;
import lk.ijse.dep.MostWantedCabs.DTO.VehicleDTO;
import lk.ijse.dep.MostWantedCabs.Entity.Vehicle;

import java.util.List;

public interface VehicleBO extends SuperBO {
    void saveVehicle(VehicleDTO vehicle)throws Exception;

    void updateVehicle(VehicleDTO vehicle)throws Exception;

    void deleteVehicle(String vehicleId) throws Exception;

    List<VehicleDTO> findAllVehicles() throws Exception;

    String getLastVehicleId()throws Exception;

    VehicleDTO findVehicle(String vehicleId) throws Exception;

    List<String> getAllVehicleIds()throws Exception;

    List<String> getAllAvailableVehicleIds()throws Exception;

    List<VehicleDTO> searchVehicle(String search)throws Exception;

}
