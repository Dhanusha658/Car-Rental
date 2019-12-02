package lk.ijse.dep.MostWantedCabs.Entity;

public class IssueDetail implements SuperEntity{

   private IssueDetailPK issueDetailPK;

    public IssueDetail(IssueDetailPK issueDetailPK) {
        this.issueDetailPK = issueDetailPK;
    }
    public IssueDetail(String issueId,String driverId) {
        this.issueDetailPK = new IssueDetailPK(issueId,driverId);
    }

    public IssueDetail() {
    }

    public IssueDetailPK getIssueDetailPK() {
        return issueDetailPK;
    }

    public void setIssueDetailPK(IssueDetailPK issueDetailPK) {
        this.issueDetailPK = issueDetailPK;
    }

    @Override
    public String toString() {
        return "IssueDetail{" +
                "issueDetailPK=" + issueDetailPK +
                '}';
    }
}
