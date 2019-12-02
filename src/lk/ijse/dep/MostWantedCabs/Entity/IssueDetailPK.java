package lk.ijse.dep.MostWantedCabs.Entity;

public class IssueDetailPK {
    private String issueId;
    private String driverId;

    public IssueDetailPK(String issueId, String driverId) {
        this.issueId = issueId;
        this.driverId = driverId;
    }

    public IssueDetailPK() {
    }

    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    @Override
    public String toString() {
        return "IssueDetailPK{" +
                "issueId='" + issueId + '\'' +
                ", driverId='" + driverId + '\'' +
                '}';
    }
}
