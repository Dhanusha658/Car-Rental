package lk.ijse.dep.MostWantedCabs.DAO;

import lk.ijse.dep.MostWantedCabs.Entity.SuperEntity;

import java.util.List;

public interface CrudDAO <T extends SuperEntity,ID>extends SuperDAO {

     List<T> findAll()throws Exception;

     T find(ID id)throws Exception;

     void save(T entity)throws Exception;

     void update(T entity)throws Exception;

     void delete(ID id)throws Exception;

}
