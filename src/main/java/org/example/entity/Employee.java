package org.example.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing an Employee.
 */
@Entity
@Table(name = "employee")
@PrimaryKeyJoinColumn(name = "person_id")
public class Employee extends Person {

    /**
     * The buildings assigned to the Employee.
     */
    @OneToMany(mappedBy = "assignedEmployee", fetch = FetchType.LAZY)
    private Set<Building> buildings = new HashSet<>();

    /**
     * The company the Employee belongs to.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    /**
     * Default constructor.
     */
    public Employee() {
        // Default constructor
    }

    /**
     * Constructor to create an Employee with a specified name.
     *
     * @param name The name of the Employee.
     */
    public Employee(String name) {
        super(name);
    }

    /**
     * Gets the buildings assigned to the Employee.
     *
     * @return The buildings assigned to the Employee.
     */
    public Set<Building> getBuildings() {
        return buildings;
    }

    /**
     * Sets the buildings assigned to the Employee.
     *
     * @param buildings The buildings to set.
     */
    public void setBuildings(Set<Building> buildings) {
        this.buildings = buildings;
    }

    /**
     * Gets the company the Employee belongs to.
     *
     * @return The company the Employee belongs to.
     */
    public Company getCompany() {
        return company;
    }

    /**
     * Sets the company the Employee belongs to.
     *
     * @param company The company to set.
     */
    public void setCompany(Company company) {
        this.company = company;
    }

    /**
     * Provides a string representation of the Employee.
     *
     * @return A string representing the Employee.
     */
    @Override
    public String toString() {
        return "Employee{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                '}';
    }
}
