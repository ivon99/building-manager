package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.Date;

/**
 * Entity representing a Fee.
 */
@Entity
@Table(name = "fee")
public class Fee {

    /**
     * The unique identifier for the Fee.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The amount of the Fee.
     */
    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be positive")
    private Double amount;

    /**
     * Indicates whether the Fee has been paid.
     */
    private boolean paid;

    /**
     * The date the Fee was paid.
     */
    @Temporal(TemporalType.DATE)
    private Date datePaid;

    /**
     * The apartment associated with the Fee.
     */
    @ManyToOne
    @JoinColumn(name = "apartment_id", nullable = false)
    private Apartment apartment;

    /**
     * Default constructor.
     */
    public Fee() {
        // Default constructor
    }

    /**
     * Constructor to create a Fee with a specified amount and apartment.
     *
     * @param amount The amount of the Fee.
     * @param apartment The apartment associated with the Fee.
     */
    public Fee(Double amount, Apartment apartment) {
        this.amount = amount;
        this.paid = false;
        this.apartment = apartment;
    }

    /**
     * Constructor to create a Fee with a specified amount, date paid, and apartment.
     *
     * @param amount The amount of the Fee.
     * @param datePaid The date the Fee was paid.
     * @param apartment The apartment associated with the Fee.
     */
    public Fee(Double amount, Date datePaid, Apartment apartment) {
        this.amount = amount;
        this.paid = true;
        this.datePaid = datePaid;
        this.apartment = apartment;
    }

    /**
     * Gets the unique identifier for the Fee.
     *
     * @return The unique identifier for the Fee.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the Fee.
     *
     * @param id The unique identifier for the Fee.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the amount of the Fee.
     *
     * @return The amount of the Fee.
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * Sets the amount of the Fee.
     *
     * @param amount The amount to set.
     * @throws IllegalArgumentException if the amount is negative.
     */
    public void setAmount(Double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        this.amount = amount;
    }

    /**
     * Checks if the Fee has been paid.
     *
     * @return true if the Fee has been paid, false otherwise.
     */
    public boolean isPaid() {
        return paid;
    }

    /**
     * Sets the paid status of the Fee.
     *
     * @param paid The paid status to set.
     */
    public void setPaid(boolean paid) {
        this.paid = paid;
        if (paid) {
            this.datePaid = new Date(); // Set the current date when paid is true
        } else {
            this.datePaid = null; // Reset the datePaid to null if not paid
        }
    }

    /**
     * Gets the date the Fee was paid.
     *
     * @return The date the Fee was paid.
     */
    public Date getDatePaid() {
        return datePaid;
    }

    /**
     * Sets the date the Fee was paid.
     *
     * @param datePaid The date to set.
     */
    public void setDatePaid(Date datePaid) {
        this.datePaid = datePaid;
    }

    /**
     * Gets the apartment associated with the Fee.
     *
     * @return The apartment associated with the Fee.
     */
    public Apartment getApartment() {
        return apartment;
    }

    /**
     * Sets the apartment associated with the Fee.
     *
     * @param apartment The apartment to set.
     */
    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }
}
