package org.example.dao;

import org.example.configuration.SessionFactoryUtil;
import org.example.entity.Owner;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Data Access Object (DAO) for managing Owner entities.
 */
public class OwnerDAO {

    /**
     * Creates a new Owner in the database.
     *
     * @param owner The Owner object to be created.
     */
    public static void createOwner(Owner owner) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(owner);
            transaction.commit();
        }
    }

    /**
     * Retrieves an Owner from the database by its ID.
     *
     * @param id The ID of the Owner to retrieve.
     * @return The Owner object retrieved from the database, or null if not found.
     */
    public static Owner getOwnerById(long id) {
        Owner owner;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            owner = session.get(Owner.class, id);
            transaction.commit();
        }
        return owner;
    }

    /**
     * Updates an existing Owner in the database.
     *
     * @param owner The Owner object to update.
     */
    public static void updateOwner(Owner owner) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(owner);
            transaction.commit();
        }
    }

    /**
     * Deletes an existing Owner from the database.
     *
     * @param owner The Owner object to delete.
     */
    public static void deleteOwner(Owner owner) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(owner);
            transaction.commit();
        }
    }
}


