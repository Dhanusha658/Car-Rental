package lk.ijse.dep.MostWantedCabs.DTO;

public class CustomerDTO {
    private String id;
    private String name;
    private String address;
    private String licenseNo;
    private String contactNo;

    public CustomerDTO(String id, String name, String address, String licenseNo, String contactNo) {
        this.setId(id);
        this.setName(name);
        this.setAddress(address);
        this.setLicenseNo(licenseNo);
        this.setContactNo(contactNo);
    }
    public CustomerDTO( String name, String address, String licenseNo, String contactNo) {
        this.setName(name);
        this.setAddress(address);
        this.setLicenseNo(licenseNo);
        this.setContactNo(contactNo);
    }

    public CustomerDTO() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    @Override
    public String toString() {
        return "CustomerDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", licenseNo='" + licenseNo + '\'' +
                ", contactNo='" + contactNo + '\'' +
                '}';
    }
}
