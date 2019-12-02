package lk.ijse.dep.MostWantedCabs.Business.custom.impl;

import lk.ijse.dep.MostWantedCabs.Business.custom.CustomerBO;
import lk.ijse.dep.MostWantedCabs.Business.exception.AlreadyExist;
import lk.ijse.dep.MostWantedCabs.DAO.DAOFactory;
import lk.ijse.dep.MostWantedCabs.DAO.DAOType;
import lk.ijse.dep.MostWantedCabs.DAO.custom.CustomerDAO;
import lk.ijse.dep.MostWantedCabs.DAO.custom.IssueDAO;
import lk.ijse.dep.MostWantedCabs.DB.HibernateUtil;
import lk.ijse.dep.MostWantedCabs.DTO.CustomerDTO;
import lk.ijse.dep.MostWantedCabs.Entity.Customer;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {

    private CustomerDAO customerDAO= DAOFactory.getInstance().getDAO(DAOType.CUSTOMER);
    private IssueDAO issueDAO=DAOFactory.getInstance().getDAO(DAOType.ISSUE);

    @Override
    public void saveCustomer(CustomerDTO customer) throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().openSession();) {
            customerDAO.setSession(session);
            session.beginTransaction();
            customerDAO.save(new Customer(customer.getId(), customer.getName(), customer.getAddress(), customer.getLicenseNo(), customer.getContactNo()));
            session.getTransaction().commit();
        }
    }

    @Override
    public void updateCustomer(CustomerDTO customer) throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().openSession();) {
            customerDAO.setSession(session);
            session.beginTransaction();
            customerDAO.update(new Customer(customer.getId(), customer.getName(), customer.getAddress(), customer.getLicenseNo(), customer.getContactNo()));
            session.getTransaction().commit();
        }
    }

    @Override
    public void deleteCustomer(String customerId) throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().openSession();) {
            customerDAO.setSession(session);
            issueDAO.setSession(session);
            session.beginTransaction();
        if(issueDAO.existCustomerId(customerId)){
            throw new AlreadyExist("Customer already exists in an Issue, hence unable to delete !");
        }
         customerDAO.delete(customerId);
        session.getTransaction().commit();
        }
    }

    @Override
    public List<CustomerDTO> findAllCustomers() throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().openSession();) {
            customerDAO.setSession(session);
            session.beginTransaction();
            List<Customer> alcustomer = customerDAO.findAll();
            session.getTransaction().commit();
            List<CustomerDTO> customerDTOS = new ArrayList<>();
            for (Customer customer : alcustomer) {
                customerDTOS.add(new CustomerDTO(customer.getId(), customer.getName(), customer.getAddress(), customer.getLicenseNo(), customer.getContactNo()));
            }
            return customerDTOS;
        }
    }

    @Override
    public String getLastCustomerId() throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().openSession();) {
            customerDAO.setSession(session);
            session.beginTransaction();
            String lasCustomerId = customerDAO.getLasCustomerId();
            session.getTransaction().commit();
            return lasCustomerId;
        }
    }

    @Override
    public CustomerDTO findCustomer(String customerId) throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().openSession();) {
            customerDAO.setSession(session);
            session.beginTransaction();
            Customer customer = customerDAO.find(customerId);
            session.getTransaction().commit();
            return new CustomerDTO(customer.getId(), customer.getName(), customer.getAddress(), customer.getLicenseNo(), customer.getContactNo());
        }
    }

    @Override
    public List<String> getAllCustomerIds() throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().openSession();) {
            customerDAO.setSession(session);
            session.beginTransaction();
            List<Customer> customer = customerDAO.findAll();
            session.getTransaction().commit();
            List<String> customerid = new ArrayList<>();
            for (Customer customer1 : customer) {
                customerid.add(customer1.getId());
            }
            return customerid;
        }
    }
}
