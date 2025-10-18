package org.example.dao;

import org.example.configuration.SessionFactoryUtil;
import org.example.entity.Employee;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Data Access Object (DAO) class for performing CRUD operations on the Employee entity.
 */
public class EmployeeDAO {
    /**
     * Creates a new Employee in the database.
     *
     * @param employee The Employee entity to be created.
     */
    public static void createEmployee(Employee employee) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(employee);
            transaction.commit();
        }
    }


    /**
     * Retrieves an Employee by its ID.
     *
     * @param id The ID of the Employee to retrieve.
     * @return The Employee entity with the specified ID, or null if not found.
     */
    public static Employee getEmployeeById(long id) {
        Employee employee;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            employee = session.get(Employee.class, id);
            transaction.commit();
        }
        return employee;
    }

    /**
     * Updates an existing Employee in the database.
     *
     * @param employee The Employee entity to be updated.
     */
    public static void updateEmployee(Employee employee) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(employee);
            transaction.commit();
        }
    }

    /**
     * Deletes an Employee from the database.
     *
     * @param employee The Employee entity to be deleted.
     */
    public static void deleteEmployee(Employee employee) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(employee);
            transaction.commit();
        }
    }

}
