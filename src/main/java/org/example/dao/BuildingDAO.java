package org.example.dao;

import org.example.configuration.SessionFactoryUtil;
import org.example.entity.Apartment;
import org.example.entity.Building;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Set;

/**
 * Data Access Object (DAO) for managing Building entities.
 */
public class BuildingDAO {

    /**
     * Creates a new Building in the database.
     *
     * @param building The Building object to be created.
     */
    public static void createBuilding(Building building) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(building);
            transaction.commit();
        }
    }

    /**
     * Retrieves a Building from the database by its ID.
     *
     * @param id The ID of the Building to retrieve.
     * @return The Building object retrieved from the database, or null if not found.
     */
    public static Building getBuildingById(long id) {
        Building building;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            building = session.get(Building.class, id);
            transaction.commit();
        }
        return building;
    }

    /**
     * Updates an existing Building in the database.
     *
     * @param building The Building object to update.
     */
    public static void updateBuilding(Building building) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(building);
            transaction.commit();
        }
    }

    /**
     * Deletes an existing Building from the database.
     *
     * @param building The Building object to delete.
     */
    public static void deleteBuilding(Building building) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(building);
            transaction.commit();
        }
    }

    /**
     * Prints the total number of apartments in the building with the specified ID.
     *
     * @param buildingId The ID of the building.
     */
    public static void printTotalNumberOfApartments(long buildingId) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Building building = session.get(Building.class, buildingId);
            if (building == null) {
                System.out.println("Building with ID " + buildingId + " not found.");
                return;
            }
            int totalApartments = building.getApartments().size();
            System.out.println("Total number of apartments in building with ID " + buildingId + ": " + totalApartments);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Prints the list of apartments in the building with the specified ID.
     *
     * @param buildingId The ID of the building.
     */
    public static void printListOfApartments(long buildingId) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Building building = session.get(Building.class, buildingId);
            if (building == null) {
                System.out.println("Building with ID " + buildingId + " not found.");
                return;
            }
            Set<Apartment> apartments = building.getApartments();
            System.out.println("List of apartments in building with ID " + buildingId + ":");
            for (Apartment apartment : apartments) {
                System.out.println("\tApartment ID: " + apartment.getId() + ", Floor: " + apartment.getFloor() + ", Area: " + apartment.getArea() + ", Has Pet: " + apartment.isHasPet());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

