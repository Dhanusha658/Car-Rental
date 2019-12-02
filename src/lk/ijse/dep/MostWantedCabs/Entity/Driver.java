package lk.ijse.dep.MostWantedCabs.Entity;

import javax.persistence.*;

@Entity
public class Driver implements SuperEntity{
    @Id
    private String id;
    private String name;
    private String  address;
    private String contactNo;
    private String licenseNo;
    private double salaryPerDay;
    private String statues;
    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REMOVE,CascadeType.REFRESH})
            @JoinTable(name = "issueDetail",joinColumns = @JoinColumn(name = "driverId",referencedColumnName = "id"),
                    inverseJoinColumns =@JoinColumn(name = "issueId",referencedColumnName = "id"))
    private
    Issue issue;


    public Driver(String id, String name, String address, String contactNo, String licenseNo, double salaryPerDay, String statues) {
        this.setId(id);
        this.setName(name);
        this.setAddress(address);
        this.setContactNo(contactNo);
        this.setLicenseNo(licenseNo);
        this.setSalaryPerDay(salaryPerDay);
        this.setStatues(statues);
    }

    public Driver( String name, String address, String contactNo, String licenseNo, double salaryPerDay, String statues) {
        this.setName(name);
        this.setAddress(address);
        this.setContactNo(contactNo);
        this.setLicenseNo(licenseNo);
        this.setSalaryPerDay(salaryPerDay);
        this.setStatues(statues);
    }

    public Driver() {
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

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public double getSalaryPerDay() {
        return salaryPerDay;
    }

    public void setSalaryPerDay(double salaryPerDay) {
        this.salaryPerDay = salaryPerDay;
    }

    public String getStatues() {
        return statues;
    }

    public void setStatues(String statues) {
        this.statues = statues;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }
}
