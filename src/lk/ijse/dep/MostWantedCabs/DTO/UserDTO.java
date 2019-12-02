package lk.ijse.dep.MostWantedCabs.DTO;

public class UserDTO {
    private String userName;
    private String password;
    private String address;
    private String contactNo;

    public UserDTO(String userName, String password, String address, String contactNo) {
        this.setUserName(userName);
        this.setPassword(password);
        this.setAddress(address);
        this.setContactNo(contactNo);
    }

    public UserDTO() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", contactNo='" + contactNo + '\'' +
                '}';
    }
}
