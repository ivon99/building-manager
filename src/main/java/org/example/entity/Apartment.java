package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing an Apartment.
 */
@Entity
@Table(name = "apartment")
public class Apartment {

        /**
         * Unique identifier for the Apartment.
         */
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        /**
         * The floor number of the Apartment. Must be between 1 and 200.
         */
        @Min(value = 1, message = "Floor must be at least 1")
        @Max(value = 200, message = "Floor must be at most 200")
        @NotNull(message = "Floor cannot be null")
        private Integer floor;

        /**
         * The area of the Apartment in square meters. Must be positive.
         */
        @Positive(message = "Area must be positive")
        @NotNull(message = "Area cannot be null")
        private Double area;

        /**
         * Indicates if the Apartment has a pet.
         */
        private boolean hasPet;

        /**
         * The owner of the Apartment.
         */
        @ManyToOne
        @JoinColumn(name = "owner_id", nullable = false)
        private Owner owner;

        /**
         * The building in which the Apartment is located.
         */
        @ManyToOne
        @JoinColumn(name = "building_id", nullable = false)
        private Building building;

        /**
         * The residents living in the Apartment.
         */
        @OneToMany(mappedBy = "apartment", cascade = CascadeType.ALL, orphanRemoval = true)
        private Set<Resident> residents;

        /**
         * The fees associated with the Apartment.
         */
        @OneToMany(mappedBy = "apartment", cascade = CascadeType.ALL, orphanRemoval = true)
        private Set<Fee> fees;

        /**
         * Default constructor initializing the sets of residents and fees.
         */
        public Apartment() {
                this.residents = new HashSet<>();
                this.fees = new HashSet<>();
        }

        /**
         * Constructor to create an Apartment with specified properties.
         *
         * @param floor The floor number of the Apartment.
         * @param area The area of the Apartment.
         * @param hasPet Indicates if the Apartment has a pet.
         * @param owner The owner of the Apartment.
         * @param building The building in which the Apartment is located.
         */
        public Apartment(Integer floor, Double area, boolean hasPet, Owner owner, Building building) {
                this.floor = floor;
                this.area = area;
                this.hasPet = hasPet;
                this.owner = owner;
                this.building = building;
                this.residents = new HashSet<>();
                this.fees = new HashSet<>();
        }

        /**
         * Gets the unique identifier of the Apartment.
         *
         * @return The id of the Apartment.
         */
        public Long getId() {
                return id;
        }

        /**
         * Sets the unique identifier of the Apartment.
         *
         * @param id The id to set.
         */
        public void setId(Long id) {
                this.id = id;
        }

        /**
         * Gets the floor number of the Apartment.
         *
         * @return The floor number.
         */
        public Integer getFloor() {
                return floor;
        }

        /**
         * Sets the floor number of the Apartment.
         *
         * @param floor The floor number to set.
         */
        public void setFloor(Integer floor) {
                this.floor = floor;
        }

        /**
         * Gets the area of the Apartment.
         *
         * @return The area.
         */
        public Double getArea() {
                return area;
        }

        /**
         * Sets the area of the Apartment.
         *
         * @param area The area to set.
         */
        public void setArea(Double area) {
                this.area = area;
        }

        /**
         * Checks if the Apartment has a pet.
         *
         * @return True if the Apartment has a pet, false otherwise.
         */
        public boolean isHasPet() {
                return hasPet;
        }

        /**
         * Sets the pet status of the Apartment.
         *
         * @param hasPet True if the Apartment has a pet, false otherwise.
         */
        public void setHasPet(boolean hasPet) {
                this.hasPet = hasPet;
        }

        /**
         * Gets the owner of the Apartment.
         *
         * @return The owner.
         */
        public Owner getOwner() {
                return owner;
        }

        /**
         * Sets the owner of the Apartment.
         *
         * @param owner The owner to set.
         */
        public void setOwner(Owner owner) {
                this.owner = owner;
        }

        /**
         * Gets the building in which the Apartment is located.
         *
         * @return The building.
         */
        public Building getBuilding() {
                return building;
        }

        /**
         * Sets the building in which the Apartment is located.
         *
         * @param building The building to set.
         */
        public void setBuilding(Building building) {
                this.building = building;
        }

        /**
         * Gets the residents living in the Apartment.
         *
         * @return The residents.
         */
        public Set<Resident> getResidents() {
                return residents;
        }

        /**
         * Sets the residents living in the Apartment.
         *
         * @param residents The residents to set.
         */
        public void setResidents(Set<Resident> residents) {
                this.residents = residents;
        }

        /**
         * Gets the fees associated with the Apartment.
         *
         * @return The fees.
         */
        public Set<Fee> getFees() {
                return fees;
        }

        /**
         * Sets the fees associated with the Apartment.
         *
         * @param fees The fees to set.
         */
        public void setFees(Set<Fee> fees) {
                this.fees = fees;
        }
}

