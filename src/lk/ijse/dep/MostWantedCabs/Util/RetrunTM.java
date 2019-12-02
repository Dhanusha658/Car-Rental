package lk.ijse.dep.MostWantedCabs.Util;

import java.sql.Date;

public class RetrunTM {
    private Date issueDate;
    private Date returnDate;
    private String issueId;
    private String additionalKilometers;
    private double costOfDamage;
    private String vehicleModel;
    private String customerName;
    private double total;

    public RetrunTM(Date issueDate, Date returnDate, String issueId, String additionalKilometers, double costOfDamage, String vehicleModel, String customerName, double total) {
        this.setIssueDate(issueDate);
        this.setReturnDate(returnDate);
        this.setIssueId(issueId);
        this.setAdditionalKilometers(additionalKilometers);
        this.setCostOfDamage(costOfDamage);
        this.setVehicleModel(vehicleModel);
        this.setCustomerName(customerName);
        this.setTotal(total);
    }

    public RetrunTM() {
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public String getAdditionalKilometers() {
        return additionalKilometers;
    }

    public void setAdditionalKilometers(String additionalKilometers) {
        this.additionalKilometers = additionalKilometers;
    }

    public double getCostOfDamage() {
        return costOfDamage;
    }

    public void setCostOfDamage(double costOfDamage) {
        this.costOfDamage = costOfDamage;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "RetrunTM{" +
                "issueDate=" + issueDate +
                ", returnDate=" + returnDate +
                ", issueId='" + issueId + '\'' +
                ", additionalKilometers='" + additionalKilometers + '\'' +
                ", costOfDamage=" + costOfDamage +
                ", vehicleModel='" + vehicleModel + '\'' +
                ", customerName='" + customerName + '\'' +
                ", total=" + total +
                '}';
    }
}
