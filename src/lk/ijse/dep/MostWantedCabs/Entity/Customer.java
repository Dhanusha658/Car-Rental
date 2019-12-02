package lk.ijse.dep.MostWantedCabs.Entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Customer implements SuperEntity {
    @Id
    private String id;
    private String name;
    private String address;
    private String licenseNo;
    private String contactNo;
    @OneToMany(mappedBy = "customer",cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REMOVE,CascadeType.REFRESH})
    private
    List<Issue> issue;

    public Customer(String id, String name, String address, String licenseNo, String contactNo) {
        this.setId(id);
        this.setName(name);
        this.setAddress(address);
        this.setLicenseNo(licenseNo);
        this.setContactNo(contactNo);
    }

    public Customer( String name, String address, String licenseNo, String contactNo) {
        this.setName(name);
        this.setAddress(address);
        this.setLicenseNo(licenseNo);
        this.setContactNo(contactNo);
    }

    public Customer() {
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
    public List<Issue> getIssue() {
        return issue;
    }

    public void addIssue(Issue issue) {
        issue.setCustomer(this);
        this.issue .add(issue);
    }


    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", licenseNo='" + licenseNo + '\'' +
                ", contactNo='" + contactNo + '\'' +
                '}';
    }


}
