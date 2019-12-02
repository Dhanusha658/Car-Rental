package lk.ijse.dep.MostWantedCabs.DTO;

import lk.ijse.dep.MostWantedCabs.Entity.Issue;

import java.sql.Date;

public class ReturnDTO {
    private Issue issueId;
    private Date returnDate;
    private int additionalDistance;
    private double damageCost;
    private double total;

    public ReturnDTO(Issue issueId, Date returnDate, int additionalDistance, double damageCost, double total) {
        this.setIssueId(issueId);
        this.setReturnDate(returnDate);
        this.setAdditionalDistance(additionalDistance);
        this.setDamageCost(damageCost);
        this.setTotal(total);
    }

    public ReturnDTO() {
    }

    public Issue getIssueId() {
        return issueId;
    }

    public void setIssueId(Issue issueId) {
        this.issueId = issueId;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public int getAdditionalDistance() {
        return additionalDistance;
    }

    public void setAdditionalDistance(int additionalDistance) {
        this.additionalDistance = additionalDistance;
    }

    public double getDamageCost() {
        return damageCost;
    }

    public void setDamageCost(double damageCost) {
        this.damageCost = damageCost;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "ReturnDTO{" +
                "issueId='" + issueId + '\'' +
                ", returnDate=" + returnDate +
                ", additionalDistance=" + additionalDistance +
                ", damageCost=" + damageCost +
                ", total=" + total +
                '}';
    }
}
