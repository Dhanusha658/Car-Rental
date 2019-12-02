package lk.ijse.dep.MostWantedCabs.Entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "`return`")
public class Return implements SuperEntity{
    @Id
    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REMOVE,CascadeType.REFRESH})
    @JoinColumn(name = "issueId",referencedColumnName = "id",nullable = false)
    private Issue issue;
    private Date returnDate;
    private int additionalDistance;
    private double damageCost;
    private double total;

    public Return(Issue issue, Date returnDate, int additionalDistance, double damageCost, double total) {
        this.setIssue(issue);
        this.setReturnDate(returnDate);
        this.setAdditionalDistance(additionalDistance);
        this.setDamageCost(damageCost);
        this.setTotal(total);
    }

    public Return() {
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
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
        return "Return{" +
                "issue=" + issue +
                ", returnDate=" + returnDate +
                ", additionalDistance=" + additionalDistance +
                ", damageCost=" + damageCost +
                ", total=" + total +
                '}';
    }
}
