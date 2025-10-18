package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * Entity representing a Person.
 */
@Entity
@Table(name = "person")
@Inheritance(strategy = InheritanceType.JOINED)
public class Person {

    /**
     * The unique identifier for the Person.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    /**
     * The name of the Person.
     */
    @NotBlank(message = "Name cannot be blank")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "Name should contain only letters")
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Default constructor.
     */
    public Person() {
        // Default constructor
    }

    /**
     * Constructor to create a Person with a specified name.
     *
     * @param name The name of the Person.
     */
    public Person(String name) {
        this.name = name;
    }

    /**
     * Gets the unique identifier of the Person.
     *
     * @return The unique identifier of the Person.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the Person.
     *
     * @param id The unique identifier to set.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the name of the Person.
     *
     * @return The name of the Person.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the Person.
     *
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Provides a string representation of the Person.
     *
     * @return A string representation of the Person.
     */
    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}


