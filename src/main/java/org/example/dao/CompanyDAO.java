
package org.example.dao;

import jakarta.persistence.criteria.*;
import org.example.configuration.SessionFactoryUtil;
import org.example.entity.*;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Data Access Object (DAO) for managing Company entities.
 */
public class CompanyDAO {

    /**
     * Creates a new Company in the database.
     *
     * @param company The Company object to be created.
     */
    public static void createCompany(Company company) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(company);
            transaction.commit();
        }
    }


    /**
     * Retrieves a Company from the database by its ID.
     *
     * @param id The ID of the Company to retrieve.
     * @return The Company object retrieved from the database, or null if not found.
     */
    public static Company getCompanyById(long id) {
        Company company;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            company = session.get(Company.class, id);
            transaction.commit();
        }
        return company;
    }

    /**
     * Updates an existing Company in the database.
     *
     * @param company The Company object to update.
     */
    public static void updateCompany(Company company) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(company);
            transaction.commit();
        }
    }

    /**
     * Deletes an existing Company from the database.
     *
     * @param company The Company object to delete.
     */
    public static void deleteCompany(Company company) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(company);
            transaction.commit();
        }
    }

    /**
     * Hires a new employee with given name for the company specified by companyId.
     * The new employee is associated with the company and persisted to the database.
     *
     * @param companyId    The ID of the company hiring the employee.
     * @param employeeName The name of the new employee.
     * @throws IllegalArgumentException If the company with the specified ID is not found.
     */
    public static void hireNewEmployee(long companyId, String employeeName) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            // Retrieve the company entity using companyId
            Company company = session.get(Company.class, companyId);

            if (company == null) {
                throw new IllegalArgumentException("Company with ID " + companyId + " not found.");
            }

            // Create and save new employee
            Employee employee = new Employee(employeeName);
            employee.setCompany(company);
            session.persist(employee);

            // Update the company entity with the new employee
            company.getEmployees().add(employee);
            session.merge(company);

            transaction.commit();
        }
    }



    /**
     * Lays off an employee from the company, removes the employee's assignments,
     * and reassigns the employee's buildings to other employees in the company.
     *
     * @param companyId  The ID of the company laying off the employee.
     * @param employeeId The ID of the employee to be laid off.
     */
    public static void layOffEmployee(long companyId, long employeeId) {
        Transaction transaction = null;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            //Fetch the specified employee and company from the DB
            Company company = session.get(Company.class, companyId);
            Employee employee = session.get(Employee.class, employeeId);

            if (company != null && employee != null) {
                // Get the list of buildings assigned to the employee to be laid off
                Set<Building> buildingsToReassign = employee.getBuildings();

                // Remove employee from the company
                company.getEmployees().remove(employee);
                session.update(company);

                // Delete the employee
                session.delete(employee);

                // Retrieve the list of employees associated with the company
                // Sort the employees based on the number of buildings they are assigned to
                // Collect the sorted employees into a new List
                List<Employee> remainingEmployees = company.getEmployees().stream()
                        .sorted(Comparator.comparingInt(e -> e.getBuildings().size()))
                        .collect(Collectors.toList());

                // Reassign buildings to the remaining employees
                int employeeCount = remainingEmployees.size();
                int index = 0;

                // Iterate through each building that needs to be reassigned
                for (Building building : buildingsToReassign) {
                    // Retrieve the next employee from the sorted list using index modulo operation
                    Employee nextEmployee = remainingEmployees.get(index % employeeCount);
                    // Assign the building to the employee
                    nextEmployee.getBuildings().add(building);
                    building.setAssignedEmployee(nextEmployee);
                    session.update(building);
                    index++;
                }
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }



    /**
     * Adds a building to the company and assigns it to an employee with the least number of assigned buildings.
     *
     * @param companyId  The ID of the company.
     * @param buildingId The ID of the building to add to the company.
     */
    public static void addBuildingToCompany(long companyId, long buildingId) {
        Transaction transaction = null;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Retrieve the company by its ID
            Company company = session.get(Company.class, companyId);

            if (company != null) {
                // Retrieve the building by its ID
                Building newBuilding = session.get(Building.class, buildingId);

                if (newBuilding != null) {
                    // Find the employee with the least number of assigned buildings
                    // Using min() with a comparator to find the employee with the minimum number of buildings
                    // If no such employee is found (stream is empty), return null
                    Employee employeeWithLeastBuildings = company.getEmployees().stream()
                            .min(Comparator.comparingInt(e -> e.getBuildings().size()))
                            .orElse(null);

                    if (employeeWithLeastBuildings != null) {
                        // Assign the new building to the employee
                        newBuilding.setAssignedEmployee(employeeWithLeastBuildings);
                        employeeWithLeastBuildings.getBuildings().add(newBuilding);

                        // Update the employee
                        session.update(employeeWithLeastBuildings);
                    }
                }
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }


    /**
     * Reassigns a building from one employee to another within a session transaction.
     *
     * @param employeeIdAssigned ID of the employee currently assigned to the building
     * @param employeeIdToAssign ID of the employee to whom the building will be reassigned
     * @param buildingIDToReassign ID of the building to be reassigned
     * @throws IllegalArgumentException if any of the IDs provided do not correspond to existing entities
     */
    public static void reassignBuilding(long employeeIdAssigned, long employeeIdToAssign, long buildingIDToReassign) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            // Retrieve the employee who is currently assigned the building
            Employee currentEmployee = session.get(Employee.class, employeeIdAssigned);
            if (currentEmployee == null) {
                throw new IllegalArgumentException("Employee with ID " + employeeIdAssigned + " not found.");
            }

            // Retrieve the employee to whom the building will be reassigned
            Employee newEmployee = session.get(Employee.class, employeeIdToAssign);
            if (newEmployee == null) {
                throw new IllegalArgumentException("Employee with ID " + employeeIdToAssign + " not found.");
            }

            // Retrieve the building to be reassigned
            Building building = session.get(Building.class, buildingIDToReassign);
            if (building == null) {
                throw new IllegalArgumentException("Building with ID " + buildingIDToReassign + " not found.");
            }

            // Ensure the building is currently assigned to the correct employee
            if (!building.getAssignedEmployee().equals(currentEmployee)) {
                throw new IllegalArgumentException("Building is not assigned to the employee with ID " + employeeIdAssigned);
            }

            // Remove the building from the current employee
            currentEmployee.getBuildings().remove(building);

            // Assign the building to the new employee
            building.setAssignedEmployee(newEmployee);
            newEmployee.getBuildings().add(building);

            // Save or update the entities
            session.saveOrUpdate(currentEmployee);
            session.saveOrUpdate(newEmployee);
            session.saveOrUpdate(building);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Prints a list of all employees and their assigned buildings.
     */
    public static void printEmployeeAssignments() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Employee> query = builder.createQuery(Employee.class);
            Root<Employee> root = query.from(Employee.class);
            // Specify the selection criteria for the query, in this case, selecting all attributes of Employee
            query.select(root);

            // Execute the criteria query using session.createQuery(query) to retrieve a list of Employee objects
            List<Employee> employees = session.createQuery(query).getResultList();

            for (Employee employee : employees) {
                // Initialize the buildings collection to ensure it is fully loaded from the database
                Hibernate.initialize(employee.getBuildings());

                System.out.println("Employee: " + employee.getName());
                System.out.println("Buildings assigned:");
                for (Building building : employee.getBuildings()) {
                    System.out.println("- " + building.getAddress());
                }
                System.out.println();
            }

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Prints a list of companies sorted by the total amount of fees collected.
     */
    public static void printCompaniesByTotalCollectedFees() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            //CriteriaQuery returning an array of objects
            CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
            //The query will start from company
            Root<Company> companyRoot = cq.from(Company.class);

            // Perform joins to navigate through related entities: Company -> Employee -> Building -> Apartment -> Fee
            Join<Company, Employee> employeeJoin = companyRoot.join("employees");
            Join<Employee, Building> buildingJoin = employeeJoin.join("buildings");
            Join<Building, Apartment> apartmentJoin = buildingJoin.join("apartments");
            Join<Apartment, Fee> feeJoin = apartmentJoin.join("fees");

            // Only include paid fees
            Predicate paidPredicate = cb.isTrue(feeJoin.get("paid"));

            //select company and sum of fee amounts
            cq.multiselect(companyRoot, cb.sum(feeJoin.get("amount")));
            cq.where(paidPredicate);
            //group results by companyID
            cq.groupBy(companyRoot.get("id"));
            //Order results by ascending total sum of fee amounts
            cq.orderBy(cb.asc(cb.sum(feeJoin.get("amount"))));

            //Execute query to obtain results
            Query<Object[]> query = session.createQuery(cq);
            List<Object[]> results = query.getResultList();

            // Iterate through each result and process them
            for (Object[] result : results) {
                // Cast the first element of the result array to Company
                Company company = (Company) result[0];
                // Retrieve the total collected fees (cast the second element of the result array to Double)
                Double totalCollectedFees = (Double) result[1];
                System.out.println(company.getName() + " - Total Collected Fees: $" + totalCollectedFees);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Prints a list of employees in a specified company sorted alphabetically by their names.
     *
     * @param companyId ID of the company whose employees will be printed
     */
    public static void printEmployeesByName(Long companyId) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            // Create a CriteriaQuery object specifying that the query will return results of type Employee.
            CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
            // Define the root entity for the query, starting with the Company entity.
            Root<Company> companyRoot = cq.from(Company.class);
            // Perform a join operation to navigate from Company to Employee entities using the "employees" attribute of the Company entity.
            Join<Company, Employee> employeeJoin = companyRoot.join("employees");

            // To return the joined Employee entities.
            // Add a where clause to filter results where the Company's id matches the specified companyId.
            // Order the results by the Employee's name in ascending order.
            cq.select(employeeJoin)
                    .where(cb.equal(companyRoot.get("id"), companyId))
                    .orderBy(cb.asc(employeeJoin.get("name")));

            // Execute the query and get the list of Employee entities that match the criteria.
            Query<Employee> query = session.createQuery(cq);
            List<Employee> employees = query.getResultList();

            System.out.println("Employees in company ID " + companyId + " sorted by name:");
            for (Employee employee : employees) {
                System.out.println(employee.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Prints a list of employees in a specified company sorted by the number of buildings assigned to them.
     *
     * @param companyId ID of the company whose employees will be printed
     */
    public static void printEmployeesByNumberOfAssignedBuildings(Long companyId) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            // Create a CriteriaQuery object that will return an array of Objects.
            CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
            // Define the root entity of the query, which is the Company entity.
            Root<Company> companyRoot = cq.from(Company.class);
            // Join the employees collection from the Company entity.
            Join<Company, Employee> employeeJoin = companyRoot.join("employees");
            // Join the buildings collection from the Employee entity.
            Join<Employee, Building> buildingJoin = employeeJoin.join("buildings");

            // Define the select clause of the query to include the Employee entity and the count of associated Buildings.
            // Add a where clause to filter results where the Company's id matches the specified companyId.
            // Group the results by the Employee's id to aggregate the building counts for each employee.
            // Order the results by the count of buildings in ascending order.
            cq.multiselect(employeeJoin, cb.count(buildingJoin.get("id")))
                    .where(cb.equal(companyRoot.get("id"), companyId))
                    .groupBy(employeeJoin.get("id"))
                    .orderBy(cb.asc(cb.count(buildingJoin.get("id"))));

            Query<Object[]> query = session.createQuery(cq);
            List<Object[]> results = query.getResultList();

            System.out.println("Employees in company ID " + companyId + " sorted by number of assigned buildings:");
            for (Object[] result : results) {
                Employee employee = (Employee) result[0];
                Long buildingCount = (Long) result[1];
                System.out.println(employee.getName() + " - Number of Assigned Buildings: " + buildingCount);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
