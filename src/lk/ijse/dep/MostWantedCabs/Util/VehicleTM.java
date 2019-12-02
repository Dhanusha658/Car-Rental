package lk.ijse.dep.MostWantedCabs.Util;

public class VehicleTM {
    private String id;
    private String registerNo;
    private String categoryId;
    private String ownerId;
    private String modelName;
    private String statues;

    public VehicleTM(String id, String registerNo, String categoryId, String ownerId, String modelName, String statues) {
        this.setId(id);
        this.setRegisterNo(registerNo);
        this.setCategoryId(categoryId);
        this.setOwnerId(ownerId);
        this.setModelName(modelName);
        this.setStatues(statues);
    }

    public VehicleTM() {
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

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
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

    @Override
    public String toString() {
        return "VehicleTM{" +
                "id='" + id + '\'' +
                ", registerNo='" + registerNo + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", modelName='" + modelName + '\'' +
                ", statues='" + statues + '\'' +
                '}';
    }
}
