package org.example.dao;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.configuration.SessionFactoryUtil;
import org.example.entity.Apartment;
import org.example.entity.Building;
import org.example.entity.Resident;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Data Access Object (DAO) class for handling CRUD operations and queries related to Resident entities.
 */
public class ResidentDAO {

    /**
     * Creates a new Resident entity in the database and updates the associated Apartment entity with the new resident.
     *
     * @param resident The Resident object to be created.
     */
    public static void createResident(Resident resident) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            // Save the resident
            session.save(resident);

            // Update the apartment's list of residents
            Apartment apartment = resident.getApartment();
            apartment.getResidents().add(resident);
            session.update(apartment);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a Resident entity from the database based on its ID.
     *
     * @param id The ID of the Resident to retrieve.
     * @return The Resident object if found, otherwise null.
     */
    public static Resident getResidentById(long id) {
        Resident resident;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            resident = session.get(Resident.class, id);
            transaction.commit();
        }
        return resident;
    }

    /**
     * Updates an existing Resident entity in the database.
     *
     * @param resident The Resident object to be updated.
     */
    public static void updateResident(Resident resident) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(resident);
            transaction.commit();
        }
    }

    /**
     * Deletes a Resident entity from the database.
     *
     * @param resident The Resident object to be deleted.
     */
    public static void deleteResident(Resident resident) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(resident);
            transaction.commit();
        }
    }


    /**
     * Retrieves and prints all residents from the database sorted by name.
     */
    public static void printResidentsByName() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Resident> cq = cb.createQuery(Resident.class);
            Root<Resident> residentRoot = cq.from(Resident.class);

            //Order residents by name in ascending order
            cq.select(residentRoot).orderBy(cb.asc(residentRoot.get("name")));

            Query<Resident> query = session.createQuery(cq);
            List<Resident> residents = query.getResultList();

            System.out.println("Residents sorted by name:");
            for (Resident resident : residents) {
                System.out.println(resident.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves and prints all residents from the database sorted by age.
     */
    public static void printResidentsByAge() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Resident> cq = cb.createQuery(Resident.class);
            Root<Resident> residentRoot = cq.from(Resident.class);

            //Order residents by age in ascending order
            cq.select(residentRoot).orderBy(cb.asc(residentRoot.get("age")));

            Query<Resident> query = session.createQuery(cq);
            List<Resident> residents = query.getResultList();

            System.out.println("Residents sorted by age:");
            for (Resident resident : residents) {
                System.out.println(resident.getName() + " - Age: " + resident.getAge());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves and prints all residents residing in a specific building based on the building ID.
     *
     * @param buildingId The ID of the building to retrieve residents from.
     */
    public static void printResidentsInBuilding(long buildingId) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Building building = session.get(Building.class, buildingId);
            if (building == null) {
                System.out.println("Building with ID " + buildingId + " not found.");
                return;
            }
            Set<Resident> residents = new HashSet<>();
            //Iterate through each Apartment object retrieved from the building
            for (Apartment apartment : building.getApartments()) {
                //Adds all Resident objects from each Apartment to the residents set.
                // This set collects all unique Resident instances from all apartments in the building.
                residents.addAll(apartment.getResidents());
            }
            System.out.println("List of residents in building with ID " + buildingId + ":");
            for (Resident resident : residents) {
                System.out.println("\tResident ID: " + resident.getId() + ", Name: " + resident.getName() + ", Age: " + resident.getAge() + ", Uses Elevator: " + resident.usesElevator());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the number of residents residing in a specific building based on the building ID.
     *
     * @param buildingId The ID of the building to retrieve the number of residents from.
     * @return The number of residents in the building.
     */
    public static int getNumberOfResidentsInBuilding(long buildingId) {
        int numberOfResidents = 0;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Building building = session.get(Building.class, buildingId);
            if (building == null) {
                System.out.println("Building with ID " + buildingId + " not found.");
                return numberOfResidents;
            }
            Set<Resident> residents = new HashSet<>();
            //Iterate through each Apartment object retrieved from the building
            for (Apartment apartment : building.getApartments()) {
                //Adds all Resident objects from each Apartment to the residents set.
                // This set collects all unique Resident instances from all apartments in the building.
                residents.addAll(apartment.getResidents());
            }
            numberOfResidents = residents.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return numberOfResidents;
    }

}