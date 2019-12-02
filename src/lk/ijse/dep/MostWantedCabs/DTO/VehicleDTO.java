package lk.ijse.dep.MostWantedCabs.DTO;

import lk.ijse.dep.MostWantedCabs.Entity.Owner;
import lk.ijse.dep.MostWantedCabs.Entity.VehicleCategory;

public class VehicleDTO {
    private String id;
    private String registerNo;
    private VehicleCategory categoryId;
    private String modelName;
    private String statues;
    private Owner ownerId;

    public VehicleDTO(String id, String registerNo, VehicleCategory categoryId, String modelName, String statues, Owner ownerId) {
        this.setId(id);
        this.setRegisterNo(registerNo);
        this.setCategoryId(categoryId);
        this.setModelName(modelName);
        this.setStatues(statues);
        this.setOwnerId(ownerId);
    }

    public VehicleDTO(String registerNo, VehicleCategory categoryId, String modelName, String statues, Owner ownerId) {
        this.setRegisterNo(registerNo);
        this.setCategoryId(categoryId);
        this.setModelName(modelName);
        this.setStatues(statues);
        this.setOwnerId(ownerId);
    }

    public VehicleDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegisterNo() {
        return registerNo;
    }

    public void setRegisterNo(String registerNo) {
        this.registerNo = registerNo;
    }

    public VehicleCategory getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(VehicleCategory categoryId) {
        this.categoryId = categoryId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getStatues() {
        return statues;
    }

    public void setStatues(String statues) {
        this.statues = statues;
    }

    public Owner getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Owner ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public String toString() {
        return "VehicleDTO{" +
                "id='" + id + '\'' +
                ", registerNo='" + registerNo + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", modelName='" + modelName + '\'' +
                ", statues='" + statues + '\'' +
                ", ownerId='" + ownerId + '\'' +
                '}';
    }
}
