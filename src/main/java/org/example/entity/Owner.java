package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing an Owner.
 */
@Entity
@Table(name = "owner")
@PrimaryKeyJoinColumn(name = "person_id")
public class Owner extends Person {

    /**
     * The age of the Owner.
     */
    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 110, message = "Age cannot exceed 110")
    private int age;

    /**
     * The set of apartments owned by the Owner.
     */
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private Set<Apartment> apartments = new HashSet<>();

    /**
     * Default constructor.
     */
    public Owner() {
        // Default constructor
    }

    /**
     * Constructor to create an Owner with a specified name and age.
     *
     * @param name The name of the Owner.
     * @param age The age of the Owner.
     */
    public Owner(String name, int age) {
        super(name);
        this.age = age;
    }

    /**
     * Gets the age of the Owner.
     *
     * @return The age of the Owner.
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the age of the Owner.
     *
     * @param age The age to set.
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Gets the set of apartments owned by the Owner.
     *
     * @return The set of apartments owned by the Owner.
     */
    public Set<Apartment> getApartments() {
        return apartments;
    }

    /**
     * Sets the set of apartments owned by the Owner.
     *
     * @param apartments The set of apartments to set.
     */
    public void setApartments(Set<Apartment> apartments) {
        this.apartments = apartments;
    }
}

