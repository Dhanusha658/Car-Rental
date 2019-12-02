package lk.ijse.dep.MostWantedCabs.Business.custom;

import lk.ijse.dep.MostWantedCabs.Business.SuperBO;
import lk.ijse.dep.MostWantedCabs.DTO.VehicleCategoryDTO;
import lk.ijse.dep.MostWantedCabs.Entity.VehicleCategory;

import java.util.List;

public interface VehicleCategoryBO extends SuperBO {

    void saveVehicleCategory(VehicleCategoryDTO vehicleCategory)throws Exception;

    void updateVehicleCategory(VehicleCategoryDTO vehicleCategory)throws Exception;

    void deleteVehicleCategory(String vehicleCategoryId) throws Exception;

    List<VehicleCategoryDTO> findAllVehicleCategories() throws Exception;

    String getLastVehicleCategoryId()throws Exception;

    VehicleCategoryDTO findVehicleCategory(String vehicleCategoryId) throws Exception;

    List<String> getAllVehicleCategoryIds()throws Exception;
}
