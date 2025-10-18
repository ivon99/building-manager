package org.example.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a Company.
 */
@Entity
@Table(name = "company")
public class Company {

    /**
     * Unique identifier for the Company.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * The name of the Company. Cannot be null and has a maximum length of 20 characters.
     */
    @Column(name = "name", nullable = false, length = 20)
    private String name;

    /**
     * The employees working for the Company.
     */
    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Employee> employees = new HashSet<>();

    /**
     * Default constructor.
     */
    public Company() {
        // Default constructor
    }

    /**
     * Constructor to create a Company with a specified name.
     *
     * @param name The name of the Company.
     */
    public Company(String name) {
        this.name = name;
    }

    /**
     * Gets the unique identifier of the Company.
     *
     * @return The id of the Company.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the Company.
     *
     * @param id The id to set.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the name of the Company.
     *
     * @return The name of the Company.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the Company.
     *
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the employees working for the Company.
     *
     * @return The employees.
     */
    public Set<Employee> getEmployees() {
        return employees;
    }

    /**
     * Sets the employees working for the Company.
     *
     * @param employees The employees to set.
     */
    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    /**
     * Provides a string representation of the Company.
     *
     * @return A string representing the Company.
     */
    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
