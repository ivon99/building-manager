package org.example.dao;

import org.example.configuration.SessionFactoryUtil;
import org.example.entity.*;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

/**
 * Data Access Object (DAO) class for performing CRUD operations and queries on the Fee entity.
 */
public class FeeDAO {

    /**
     * Adds a new fee to an apartment with an already specified amount.
     *
     * @param apartment The apartment to which the fee is to be added.
     * @param amount    The amount of the fee.
     */
    public static void addFee(Apartment apartment,Double amount){
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Fee fee = new Fee(amount, apartment);
            saveOrUpdateFee(fee);
            transaction.commit();
        }
    }

    /**
     * Adds a new fee to an apartment based on specific rates.
     *
     * @param apartment       The apartment to which the fee is to be added.
     * @param ratePerSqrMeter The rate per square meter.
     * @param ratePerResident The rate per resident.
     * @param ratePet         The rate for having a pet.
     */
    public static void addFee(Apartment apartment, BigDecimal ratePerSqrMeter, BigDecimal ratePerResident, BigDecimal ratePet){
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            BigDecimal areaFee = ratePerSqrMeter.multiply(BigDecimal.valueOf(apartment.getArea()));
            BigDecimal petFee = apartment.isHasPet() ? ratePet : BigDecimal.ZERO;
            BigDecimal residentFee = BigDecimal.ZERO;


            for (Resident resident : apartment.getResidents()) {
                if (resident.getAge() > 7 && resident.usesElevator()) {
                    residentFee = residentFee.add(ratePerResident);
                }
            }
            BigDecimal totalAmount = areaFee.add(petFee).add(residentFee);

            Fee fee = new Fee(totalAmount.doubleValue(), apartment);
            session.save(fee);
            transaction.commit();
        }
    }


    /**
     * Retrieves a list of paid fees associated with a given company.
     *
     * @param company The company for which to retrieve paid fees.
     * @return A list of paid fees associated with the given company.
     */
    public static List<Fee> getPaidFeesByCompany(Company company) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            // Create a CriteriaQuery object to define a query returning Fee entities
            CriteriaQuery<Fee> cq = cb.createQuery(Fee.class);
            Root<Fee> root = cq.from(Fee.class);

            // Join the Fee entity with the Apartment entity through the "apartment" association
            Join<Fee, Apartment> apartmentJoin = root.join("apartment");
            // Join the Apartment entity with the Building entity through the "building" association
            Join<Apartment, Building> buildingJoin = apartmentJoin.join("building");
            // Join the Building entity with the Employee entity through the "assignedEmployee" association
            Join<Building, Employee> employeeJoin = buildingJoin.join("assignedEmployee");

            // Create a Predicate to filter fees that are paid (paid attribute is true)
            Predicate paidPredicate = cb.isTrue(root.get("paid"));

            // Create a Predicate to filter fees associated with the specified company
            // This involves checking if the company of the assigned employee matches the given company
            Predicate companyPredicate = cb.equal(employeeJoin.get("company"), company);

            // Combine the predicates using an AND operation, so both conditions must be true
            cq.select(root).where(cb.and(paidPredicate, companyPredicate));

            Query<Fee> query = session.createQuery(cq);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    /**
     * Retrieves a list of paid fees associated with a specific building ID.
     *
     * @param buildingId The ID of the building for which to retrieve paid fees.
     * @return A list of paid fees associated with the given building ID.
     */
   public static List<Fee> getPaidFeesByBuilding(long buildingId) {
       try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
           CriteriaBuilder cb = session.getCriteriaBuilder();
           CriteriaQuery<Fee> cq = cb.createQuery(Fee.class);
           Root<Fee> root = cq.from(Fee.class);

           Join<Fee, Apartment> apartmentJoin = root.join("apartment");
           Join<Apartment, Building> buildingJoin = apartmentJoin.join("building");

           // Create a Predicate to filter fees that are paid (paid attribute is true)
           Predicate paidPredicate = cb.isTrue(root.get("paid"));
           // Create a Predicate to filter fees associated with the specified building
           // This involves checking if the building's ID matches the given buildingId
           Predicate buildingPredicate = cb.equal(buildingJoin.get("id"), buildingId);

           // Combine the predicates using an AND operation, so both conditions must be true
           cq.select(root).where(cb.and(paidPredicate, buildingPredicate));

           Query<Fee> query = session.createQuery(cq);
           return query.getResultList();
       } catch (Exception e) {
           e.printStackTrace();
           return Collections.emptyList();
       }
   }

    /**
     * Retrieves a list of paid fees associated with a specific employee ID.
     *
     * @param employeeId The ID of the employee for which to retrieve paid fees.
     * @return A list of paid fees associated with the given employee ID.
     */
    public static List<Fee> getPaidFeesByEmployee(long employeeId) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Fee> cq = cb.createQuery(Fee.class);
            Root<Fee> root = cq.from(Fee.class);

            Join<Fee, Apartment> apartmentJoin = root.join("apartment");
            Join<Apartment, Building> buildingJoin = apartmentJoin.join("building");
            Join<Building, Employee> employeeJoin = buildingJoin.join("assignedEmployee");

            // Create a Predicate to filter fees that are paid (paid attribute is true)
            Predicate paidPredicate = cb.isTrue(root.get("paid"));
            // Create a Predicate to filter fees associated with the specified employee
            // This involves checking if the employee's ID matches the given employeeId
            Predicate employeePredicate = cb.equal(employeeJoin.get("id"), employeeId);

            // Combine the predicates using an AND operation, so both conditions must be true
            cq.select(root).where(cb.and(paidPredicate, employeePredicate));

            Query<Fee> query = session.createQuery(cq);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    /**
     * Prints a list of paid fees associated with a given company to the console.
     *
     * @param company The company for which to print paid fees.
     */
    public static void printPaidFeesByCompany(Company company) {
        List<Fee> feesByCompany = getPaidFeesByCompany(company);
        System.out.println("Paid fees for company "+company.getName()+":");
        for (Fee fee : feesByCompany) {
            System.out.println("Fee ID: " + fee.getId() + ", Amount: " + fee.getAmount());
        }
    }

    /**
     * Prints a list of paid fees associated with a specific building ID to the console.
     *
     * @param buildingId The ID of the building for which to print paid fees.
     */
    public static void printPaidFeesByBuilding(long buildingId) {
        List<Fee> paidFeesByBuilding = getPaidFeesByBuilding(buildingId);
        System.out.println("Paid fees by Building "+buildingId+":");
        for (Fee fee : paidFeesByBuilding) {
            System.out.println("Fee ID: " + fee.getId() + ", Amount: " + fee.getAmount());
        }
    }

    /**
     * Prints a list of paid fees associated with a specific employee ID to the console.
     *
     * @param employeeId The ID of the employee for which to print paid fees.
     */
    public static void printPaidFeesByEmployee(long employeeId) {
        List<Fee> paidFeesByEmployee = getPaidFeesByEmployee(employeeId);
        System.out.println("Paid fees by Employee "+employeeId+" :");
        for (Fee fee : paidFeesByEmployee) {
            System.out.println("Fee ID: " + fee.getId() + ", Amount: " + fee.getAmount());
        }
    }

    /**
     * Prints a list of unpaid fees associated with a given company to the console.
     *
     * @param company The company for which to print unpaid fees.
     */
    public static void printUnpaidFeesByCompany(Company company) {
        List<Fee> unpaidFeesByCompany = getUnpaidFeesByCompany(company);
        System.out.println("Unpaid fees by Company " + company.getName() + ":");
        for (Fee fee : unpaidFeesByCompany) {
            System.out.println("Fee ID: " + fee.getId() + ", Amount: " + fee.getAmount());
        }
    }

    /**
     * Prints a list of unpaid fees associated with a specific employee ID to the console.
     *
     * @param employeeID The ID of the employee for which to print unpaid fees.
     */
    public static void printUnpaidFeesByEmployee(long employeeID) {
        List<Fee> unpaidFeesByEmployee = getUnpaidFeesByEmployee(employeeID);
        System.out.println("Unpaid fees by Employee "+employeeID+":");
        for (Fee fee : unpaidFeesByEmployee) {
            System.out.println("Fee ID: " + fee.getId() + ", Amount: " + fee.getAmount());
        }
    }

    /**
     * Prints a list of unpaid fees associated with a specific building to the console.
     *
     * @param building The building for which to print unpaid fees.
     */
    public static void printUnpaidFeesByBuilding(Building building) {
        List<Fee> unpaidFeesByBuilding = getUnpaidFeesByBuilding(building);
        System.out.println("Unpaid fees by Building " + building.getAddress() + ":");
        for (Fee fee : unpaidFeesByBuilding) {
            System.out.println("Fee ID: " + fee.getId() + ", Amount: " + fee.getAmount());
        }
    }

    /**
     * Marks a specific fee as paid.
     *
     * @param feeId The ID of the fee to mark as paid.
     */
    public static void payFee(Long feeId) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            Fee fee = session.get(Fee.class, feeId);
            if (fee != null && !fee.isPaid()) {
                fee.setPaid(true);
                session.update(fee);
            }

            transaction.commit();
        }
    }

    /**
     * Saves or updates a fee in the database.
     *
     * @param fee The fee to save or update.
     */
    private static void saveOrUpdateFee(Fee fee) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(fee);
            transaction.commit();
        }
    }

    /**
     * Retrieves a list of unpaid fees associated with a given company.
     *
     * @param company The company for which to retrieve unpaid fees.
     * @return A list of unpaid fees associated with the given company.
     */
    public static List<Fee> getUnpaidFeesByCompany(Company company) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Fee> cq = cb.createQuery(Fee.class);
            Root<Fee> root = cq.from(Fee.class);

            // Join tables to reach the company through the associations
            Join<Fee, Apartment> apartmentJoin = root.join("apartment");
            Join<Apartment, Building> buildingJoin = apartmentJoin.join("building");
            Join<Building, Employee> employeeJoin = buildingJoin.join("assignedEmployee");

            // Predicate to check if the fee is unpaid
            Predicate unpaidPredicate = cb.isFalse(root.get("paid"));

            // Create a Predicate to filter fees associated with the specified company
            // This involves checking if the company matches the company's ID
            Predicate companyPredicate = cb.equal(employeeJoin.get("company"), company);

            // Combine predicates
            cq.select(root).where(cb.and(unpaidPredicate, companyPredicate));

            Query<Fee> query = session.createQuery(cq);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    /**
     * Retrieves a list of unpaid fees associated with a specific building.
     *
     * @param building The building for which to retrieve unpaid fees.
     * @return A list of unpaid fees associated with the given building.
     */
    public static List<Fee> getUnpaidFeesByBuilding(Building building) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Fee> cq = cb.createQuery(Fee.class);
            Root<Fee> root = cq.from(Fee.class);

            // Join tables to reach the building
            Join<Fee, Apartment> apartmentJoin = root.join("apartment");
            Join<Apartment, Building> buildingJoin = apartmentJoin.join("building");

            // Predicate to check if the fee is unpaid
            Predicate unpaidPredicate = cb.isFalse(root.get("paid"));

            // Create a Predicate to filter fees associated with the specified building
            // This involves checking if the building matches the provided building's ID
            Predicate buildingPredicate = cb.equal(buildingJoin.get("id"), building.getId());

            // Combine predicates
            cq.select(root).where(cb.and(unpaidPredicate, buildingPredicate));

            Query<Fee> query = session.createQuery(cq);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    /**
     * Retrieves a list of unpaid fees associated with a specific employee ID.
     *
     * @param employeeID The ID of the employee for which to retrieve unpaid fees.
     * @return A list of unpaid fees associated with the given employee ID.
     */
    public static List<Fee> getUnpaidFeesByEmployee(long employeeID) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Fee> cq = cb.createQuery(Fee.class);
            Root<Fee> root = cq.from(Fee.class);

            // Join tables to reach the employee through the building
            Join<Fee, Apartment> apartmentJoin = root.join("apartment");
            Join<Apartment, Building> buildingJoin = apartmentJoin.join("building");
            Join<Building, Employee> employeeJoin = buildingJoin.join("assignedEmployee");

            // Predicate to check if the fee is unpaid
            Predicate unpaidPredicate = cb.isFalse(root.get("paid"));

            // Predicate to check if the employee matches the given employeeID
            Predicate employeePredicate = cb.equal(employeeJoin.get("id"), employeeID);

            // Combine predicates
            cq.select(root).where(cb.and(unpaidPredicate, employeePredicate));

            Query<Fee> query = session.createQuery(cq);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }


    /**
     * Writes all paid fees to a specified file.
     *
     * @param filename The name of the file to write the paid fees to.
     */
    public static void writePaidFeesToFile(String filename) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Fee> cq = cb.createQuery(Fee.class);
            Root<Fee> feeRoot = cq.from(Fee.class);
            Join<Fee, Apartment> apartmentJoin = feeRoot.join("apartment");
            Join<Apartment, Building> buildingJoin = apartmentJoin.join("building");
            Join<Building, Employee> employeeJoin = buildingJoin.join("assignedEmployee");
            Join<Employee, Company> companyJoin = employeeJoin.join("company");

            // Select the feeRoot (Fee entity) and apply a condition to filter only paid fees
            cq.select(feeRoot).where(cb.isTrue(feeRoot.get("paid")));

            Query<Fee> query = session.createQuery(cq);
            List<Fee> paidFees = query.getResultList();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            //Use BufferedWriter for better performance when writing data to file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
                for (Fee fee : paidFees) {
                    String company = fee.getApartment().getBuilding().getAssignedEmployee().getCompany().getName();
                    String employee = fee.getApartment().getBuilding().getAssignedEmployee().getName();
                    String building = fee.getApartment().getBuilding().getAddress();
                    String apartment = "Apartment ID: " + fee.getApartment().getId();
                    String amount = "$" + fee.getAmount();
                    String paidDate = dateFormat.format(fee.getDatePaid());

                    writer.write(String.format("%s - %s - %s - %s - %s - %s", company, employee, building, apartment, amount, paidDate));
                    writer.newLine();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
