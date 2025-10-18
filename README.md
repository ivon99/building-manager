# Building Manager

A Java application for managing multi-family buildings, residents, and fees for property maintenance.

## Project Overview

The **Building Manager** is a Java application designed for companies that manage shared properties in multi-family buildings. It helps track buildings, apartments, residents, employees, and monthly maintenance fees. The application ensures that fees are calculated based on apartment size, residents’ usage of elevators, and pets using common areas.

Buildings are assigned to employees for management, and the system automatically handles assignments when new contracts are created or employees leave.

The project uses a **MySQL server database** for persistent storage and leverages **Hibernate ORM** for database operations. Jakarta Persistence (JPA) is utilized for entity mapping and transaction management.

---

## Features

* Add, edit, and delete:

  * Companies
  * Buildings
  * Apartments, owners, and residents
  * Employees
* Assign buildings to employees based on workload
* Calculate and record maintenance fees per apartment
* Record payment of fees and maintain history
* Filter and sort companies, employees, and residents by multiple criteria
* Generate detailed and summarized reports for buildings, apartments, residents, fees, and payments
* Persistent data storage in MySQL database

---

## Technologies Used

* **Language:** Java
* **Database:** MySQL server with Hibernate ORM
* **Persistence API:** Jakarta Persistence (JPA)

---

## Functional Requirements

The application supports the following functionalities:

1. **Company Management**

   * Create, edit, delete company records
   * Track revenue and paid fees

2. **Building Management**

   * Manage buildings’ addresses, floors, apartments, common areas, and built-up area
   * Assign buildings to employees

3. **Resident & Apartment Management**

   * Track apartment owners and residents
   * Include data such as apartment number, area, and number of residents

4. **Employee Management**

   * Add, edit, delete employees
   * Track number of buildings assigned per employee
   * Auto-assign buildings to the employee with the least workload

5. **Fee Management**

   * Calculate monthly maintenance fees:

     * Based on apartment square footage
     * Extra fee for residents using elevators (>7 years old)
     * Extra fee for pets using common areas
   * Record payments with date, amount, and payer details

6. **Data Persistence**

   * All entities and payment records are stored in MySQL database via Hibernate ORM

---

## Reports & Filtering

The application supports:

**Filtering and sorting data for:**

* Companies: by revenue (collected fees)
* Company employees: by name and by the number of serviced buildings
* Building residents: by name and by age
    
**Summarized and detailed reports (total number and list) for:**

* Buildings serviced by each employee in a given company
* Apartments in a building
* Residents in a building
* Amounts due for payment (for each company, for each building, for each employee)
* Amounts paid (for each company, for each building, for each employee)
  
**The data for paid fees is recorded in a file.**

* The recorded information includes: company, employee, building, apartment, amount, payment date.
