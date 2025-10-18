package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

/**
 * Entity representing a Resident.
 */
@Entity
@Table(name = "resident")
@PrimaryKeyJoinColumn(name = "person_id")
public class Resident extends Person {

    /**
     * The age of the Resident.
     */
    @Min(value = 0, message = "Age must be non-negative")
    @Max(value = 150, message = "Age cannot exceed 150")
    private int age;

    /**
     * Indicates whether the Resident uses an elevator.
     */
    private boolean usesElevator;

    /**
     * The apartment the Resident lives in.
     */
    @ManyToOne
    @JoinColumn(name = "apartment_id", nullable = false)
    private Apartment apartment;

    /**
     * Default constructor.
     */
    public Resident() {
        // Default constructor
    }

    /**
     * Constructor to create a Resident with specified name, age, apartment, and elevator usage.
     *
     * @param name The name of the Resident.
     * @param age The age of the Resident.
     * @param apartment The apartment associated with the Resident.
     * @param usesElevator Whether the Resident uses an elevator.
     */
    public Resident(String name, int age, Apartment apartment, boolean usesElevator) {
        super(name);
        this.age = age;
        this.usesElevator = usesElevator;
        this.apartment = apartment;
    }

    /**
     * Gets the age of the Resident.
     *
     * @return The age of the Resident.
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the age of the Resident.
     *
     * @param age The age to set.
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Gets the apartment the Resident inhabits.
     *
     * @return The apartment the Resident inhabits.
     */
    public Apartment getApartment() {
        return apartment;
    }

    /**
     * Sets the apartment the Resident inhabits.
     *
     * @param apartment The apartment to set.
     */
    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    /**
     * Checks if the Resident uses an elevator.
     *
     * @return True if the Resident uses an elevator, false otherwise.
     */
    public boolean usesElevator() {
        return usesElevator;
    }

    /**
     * Sets whether the Resident uses an elevator.
     *
     * @param usesElevator Whether the Resident uses an elevator. True if they use it. False if not.
     */
    public void setUsesElevator(boolean usesElevator) {
        this.usesElevator = usesElevator;
    }
}

