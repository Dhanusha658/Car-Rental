package lk.ijse.dep.MostWantedCabs.DTO;

public class OwnerDTO {
    private String id;
    private String name;
    private String contactNo;
    private String address;

    public OwnerDTO(String id, String name, String contactNo, String address) {
        this.setId(id);
        this.setName(name);
        this.setContactNo(contactNo);
        this.setAddress(address);
    }
    public OwnerDTO( String name, String contactNo, String address) {
        this.setName(name);
        this.setContactNo(contactNo);
        this.setAddress(address);
    }

    public OwnerDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "OwnerDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", contactNo='" + contactNo + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
