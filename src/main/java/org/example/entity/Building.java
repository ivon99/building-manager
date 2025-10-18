package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a Building.
 */
@Entity
@Table(name = "building")
public class Building {

    /**
     * Unique identifier for the Building.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The address of the Building. Cannot be blank.
     */
    @NotBlank(message = "Address cannot be blank")
    private String address;

    /**
     * The number of floors in the Building. Must be between 1 and 200.
     */
    @Min(value = 1, message = "Number of floors must be at least 1")
    @Max(value = 200, message = "Number of floors cannot exceed 200")
    @NotNull(message = "Number of floors cannot be null")
    private Integer numberOfFloors;

    /**
     * The total area of the Building in square meters. Must be a positive number.
     */
    @Positive(message = "Building area must be a positive number")
    private BigDecimal buildingArea;

    /**
     * The common area of the Building in square meters. Must be a positive number.
     */
    @Positive(message = "Common area must be a positive number")
    private BigDecimal commonArea;

    /**
     * The employee assigned to manage the Building.
     */
    @ManyToOne
    @JoinColumn(name = "assigned_employee_id")
    private Employee assignedEmployee;

    /**
     * The apartments in the Building.
     */
    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Apartment> apartments = new HashSet<>();

    /**
     * Default constructor initializing the set of apartments.
     */
    public Building() {
        // Default constructor
    }

    /**
     * Constructor to create a Building with specified properties.
     *
     * @param address The address of the Building.
     * @param numberOfFloors The number of floors in the Building.
     * @param buildingArea The total area of the Building.
     * @param commonArea The common area of the Building.
     */
    public Building(String address, Integer numberOfFloors, BigDecimal buildingArea, BigDecimal commonArea) {
        this.address = address;
        this.numberOfFloors = numberOfFloors;
        this.buildingArea = buildingArea;
        this.commonArea = commonArea;
        this.apartments = new HashSet<>();
    }

    /**
     * Gets the unique identifier of the Building.
     *
     * @return The id of the Building.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the Building.
     *
     * @param id The id to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the address of the Building.
     *
     * @return The address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the Building.
     *
     * @param address The address to set.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the number of floors in the Building.
     *
     * @return The number of floors.
     */
    public Integer getNumberOfFloors() {
        return numberOfFloors;
    }

    /**
     * Sets the number of floors in the Building.
     *
     * @param numberOfFloors The number of floors to set.
     */
    public void setNumberOfFloors(Integer numberOfFloors) {
        this.numberOfFloors = numberOfFloors;
    }

    /**
     * Gets the total area of the Building.
     *
     * @return The building area.
     */
    public BigDecimal getBuildingArea() {
        return buildingArea;
    }

    /**
     * Sets the total area of the Building.
     *
     * @param buildingArea The building area to set.
     */
    public void setBuildingArea(BigDecimal buildingArea) {
        this.buildingArea = buildingArea;
    }

    /**
     * Gets the common area of the Building.
     *
     * @return The common area.
     */
    public BigDecimal getCommonArea() {
        return commonArea;
    }

    /**
     * Sets the common area of the Building.
     *
     * @param commonArea The common area to set.
     */
    public void setCommonArea(BigDecimal commonArea) {
        this.commonArea = commonArea;
    }

    /**
     * Gets the employee assigned to manage the Building.
     *
     * @return The assigned employee.
     */
    public Employee getAssignedEmployee() {
        return assignedEmployee;
    }

    /**
     * Sets the employee assigned to manage the Building.
     *
     * @param assignedEmployee The employee to set.
     */
    public void setAssignedEmployee(Employee assignedEmployee) {
        this.assignedEmployee = assignedEmployee;
    }

    /**
     * Gets the apartments in the Building.
     *
     * @return The apartments.
     */
    public Set<Apartment> getApartments() {
        return apartments;
    }

    /**
     * Sets the apartments in the Building.
     *
     * @param apartments The apartments to set.
     */
    public void setApartments(Set<Apartment> apartments) {
        this.apartments = apartments;
    }
}