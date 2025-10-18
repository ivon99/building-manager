package org.example.dao;

import org.example.configuration.SessionFactoryUtil;
import org.example.entity.Apartment;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Data Access Object (DAO) for managing Apartment entities.
 */
public class ApartmentDAO {

    /**
     * Creates a new Apartment in the database.
     *
     * @param apartment The Apartment object to be created.
     */
    public static void createApartment(Apartment apartment) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(apartment);
            transaction.commit();
        }
    }

    /**
     * Retrieves an Apartment from the database by its ID.
     *
     * @param id The ID of the Apartment to retrieve.
     * @return The Apartment object retrieved from the database, or null if not found.
     */
    public static Apartment getApartmentById(long id) {
        Apartment apartment;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            apartment = session.get(Apartment.class, id);
            transaction.commit();
        }
        return apartment;
    }

    /**
     * Updates an existing Apartment in the database.
     *
     * @param apartment The Apartment object to update.
     */
    public static void updateApartment(Apartment apartment) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(apartment);
            transaction.commit();
        }
    }

    /**
     * Deletes an existing Apartment from the database.
     *
     * @param apartment The Apartment object to delete.
     */
    public static void deleteApartment(Apartment apartment) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(apartment);
            transaction.commit();
        }
    }
}


