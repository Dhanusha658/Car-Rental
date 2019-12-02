package lk.ijse.dep.MostWantedCabs.DAO.custom.impl;

import lk.ijse.dep.MostWantedCabs.DAO.custom.QuaryDAO;
import lk.ijse.dep.MostWantedCabs.Entity.CustomEntity;
import lk.ijse.dep.MostWantedCabs.Entity.CustomReturnEntity;
import lk.ijse.dep.MostWantedCabs.Entity.Vehicle;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class QuaryDAOImpl implements QuaryDAO {

    private Session session;

    @Override
    public void setSession(Session session) {
        this.session=session;

    }

    @Override
    public List<CustomEntity> getIssueinfo() throws Exception {

        NativeQuery nativeQuery = session.createNativeQuery("SELECT issue.id AS id,issue.date AS date,issue.customerId AS customerId,customer.name AS customerName,issue.vehicleId AS vehicleId,vehicle.modelName AS vehicleModel" +
                ",issuedetail.driverId AS driverStatues,issue.statues AS Statues FROM issue LEFT JOIN issuedetail  on issue.id = issuedetail.issueId INNER JOIN " +
                "vehicle on issue.vehicleId = vehicle.id INNER JOIN customer on issue.customerId = customer.id GROUP BY issue.id");

        Query query = nativeQuery.setResultTransformer(Transformers.aliasToBean(CustomEntity.class));
        List list = query.list();
        return list;
    }

    @Override
    public boolean updateStatues(CustomEntity customEntity) throws Exception {
         return session.createNativeQuery("UPDATE issue SET statues=?1 WHERE id=?2").setParameter(1, customEntity.getStatues()).setParameter(2, customEntity.getId())!=null;

    }

    @Override
    public List<CustomReturnEntity> getReturnInfo() throws Exception {
        NativeQuery nativeQuery = session.createNativeQuery("SELECT issue.date AS issueDate,`return`.returnDate AS returnDate,`return`.issueId AS issueId,`return`.additionalDistance AS additionalKilometers,`return`.damageCost AS costOfDamage,vehicle.modelName AS vehicleModel,customer.name AS customerModel,`return`.total AS total " +
                "FROM `return` INNER JOIN issue on `return`.issueId = issue.id INNER JOIN vehicle on issue.vehicleId = vehicle.id INNER JOIN customer on issue.customerId =customer.id ORDER BY `return`.issueId");

        Query query = nativeQuery.setResultTransformer(Transformers.aliasToBean(CustomReturnEntity.class));
        return query.list();
    }

    @Override
    public List<Vehicle> SearchVehicle(String search) throws Exception {

        NativeQuery nativeQuery = session.createNativeQuery("SELECT * FROM vehicle WHERE id LIKE ?1 OR registerNo LIKE ?2 OR categoryId LIKE ?3 OR modelName LIKE ?4 OR statues LIKE ?5 OR ownerId LIKE ?6 GROUP by id");

        nativeQuery.setParameter(1,search);
        nativeQuery.setParameter(2,search);
        nativeQuery.setParameter(3,search);
        nativeQuery.setParameter(4,search);
        nativeQuery.setParameter(5,search);
        nativeQuery.setParameter(6,search);

        return nativeQuery.list();
    }
}
