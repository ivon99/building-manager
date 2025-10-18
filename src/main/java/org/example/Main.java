package org.example;

import org.example.dao.*;
import org.example.dao.CompanyDAO;
import org.example.entity.*;

import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {
        //Creating companies to manage
        Company company1 = new Company("Hermes");
        CompanyDAO.createCompany(company1);
        Company company2 = new Company("Apollo");
        CompanyDAO.createCompany(company2);

        //Creating buildings
        BigDecimal buildingArea1 = new BigDecimal("4201.1");
        BigDecimal commonArea1 = new BigDecimal("510.3");
        Building building1 = new Building("ul. Petko Slaveikov 21", 21, buildingArea1, commonArea1);
        BuildingDAO.createBuilding(building1);
        BigDecimal buildingArea2 = new BigDecimal("310.1");
        BigDecimal commonArea2 = new BigDecimal("777.3");
        Building building2 = new Building("ul. Dondukov 56", 13, buildingArea2, commonArea2);
        BuildingDAO.createBuilding(building2);
        BigDecimal buildingArea3 = new BigDecimal("333.4");
        BigDecimal commonArea3 = new BigDecimal("119.2");
        Building building3 = new Building("ul. Madrid 14", 17, buildingArea3, commonArea3);
        BuildingDAO.createBuilding(building3);


        //Creating owners
        Owner owner1 = new Owner("Petar",27);
        OwnerDAO.createOwner(owner1);
        Owner owner2 = new Owner("Ivan",67);
        OwnerDAO.createOwner(owner2);
        Owner owner3 = new Owner("Lili",34);
        OwnerDAO.createOwner(owner3);
        Owner owner4 = new Owner("Amanda",22);
        OwnerDAO.createOwner(owner4);
        Owner owner5= new Owner("Mariq",67);
        OwnerDAO.createOwner(owner5);

        //Creating apartments
        Apartment apartment1 = new Apartment(1, 21.7, false, owner1, building1);
        ApartmentDAO.createApartment(apartment1);
        Apartment apartment2 = new Apartment(3, 56.8, true, owner2, building1);
        ApartmentDAO.createApartment(apartment2);
        Apartment apartment3 = new Apartment(2, 34.7, false, owner3, building1);
        ApartmentDAO.createApartment(apartment3);
        Apartment apartment4 = new Apartment(7, 156.8, true, owner4, building2);
        ApartmentDAO.createApartment(apartment4);
        Apartment apartment5 = new Apartment(8, 89.7, false, owner5, building2);
        ApartmentDAO.createApartment(apartment5);
        Apartment apartment6 = new Apartment(4, 99.3, true, owner1, building3);
        ApartmentDAO.createApartment(apartment6);

        //Creating residents
        Resident resident1 = new Resident("Billie",19,apartment1,true);
        ResidentDAO.createResident(resident1);
        Resident resident2 = new Resident("Taylor",4,apartment1,true);
        ResidentDAO.createResident(resident2);
        Resident resident3 = new Resident("Sheldon",26,apartment2,false);
        ResidentDAO.createResident(resident3);
        Resident resident4 = new Resident("Daryl",78,apartment3,false);
        ResidentDAO.createResident(resident4);
        Resident resident5 = new Resident("Leo",26,apartment4,true);
        ResidentDAO.createResident(resident5);
        Resident resident6 = new Resident("Missy",84,apartment5,false);
        ResidentDAO.createResident(resident6);
        Resident resident7 = new Resident("George",64,apartment6,true);
        ResidentDAO.createResident(resident7);

        //Hire employees
        CompanyDAO.hireNewEmployee(1,"Lana");
        CompanyDAO.hireNewEmployee(1,"Jim");
        CompanyDAO.hireNewEmployee(1,"Michael");
        CompanyDAO.hireNewEmployee(2,"Pam");
        CompanyDAO.hireNewEmployee(2,"Stanley");


        //Create fees
        BigDecimal ratePerSqrMeter = BigDecimal.valueOf(2.0);
        BigDecimal ratePerResident = BigDecimal.valueOf(50.0);
        BigDecimal ratePet = BigDecimal.valueOf(100.0);
        FeeDAO.addFee(apartment1, ratePerSqrMeter, ratePerResident, ratePet);
        FeeDAO.addFee(apartment2, ratePerSqrMeter, ratePerResident, ratePet);
        FeeDAO.addFee(apartment3, ratePerSqrMeter, ratePerResident, ratePet);

        BigDecimal ratePerSqrMeter2 = BigDecimal.valueOf(1.5);
        BigDecimal ratePerResident2 = BigDecimal.valueOf(30.0);
        BigDecimal ratePet2 = BigDecimal.valueOf(25.0);
        FeeDAO.addFee(apartment4, ratePerSqrMeter2, ratePerResident2, ratePet2);
        FeeDAO.addFee(apartment5, ratePerSqrMeter2, ratePerResident2, ratePet2);

        BigDecimal ratePerSqrMeter3 = BigDecimal.valueOf(3.0);
        BigDecimal ratePerResident3 = BigDecimal.valueOf(10.0);
        BigDecimal ratePet3 = BigDecimal.valueOf(13.0);
        FeeDAO.addFee(apartment6, ratePerSqrMeter3, ratePerResident3, ratePet3);

        //Companies making contracts with buildings
        CompanyDAO.addBuildingToCompany(1,1);
        CompanyDAO.addBuildingToCompany(1,2);
        CompanyDAO.addBuildingToCompany(2,3);

        //Showing current asignments of employee-to-buildings
        CompanyDAO.printEmployeeAssignments();

        //Laying off employees
        CompanyDAO.layOffEmployee(1,13);
        CompanyDAO.layOffEmployee(2,17);
        CompanyDAO.printEmployeeAssignments();

        //Paying off fees
        FeeDAO.payFee(1L);
        FeeDAO.payFee(3L);
        FeeDAO.payFee(4L);
        FeeDAO.payFee(6L);

        //SORTING METHODS
        //Sorting companies by revenue - ascending
        CompanyDAO.printCompaniesByTotalCollectedFees();

        //Sorting employee in a company
        //-by name
        CompanyDAO.printEmployeesByName(1L);
        CompanyDAO.printEmployeesByName(2L);
        //-by number of assigned buildings
        CompanyDAO.printEmployeesByNumberOfAssignedBuildings(1L);
        CompanyDAO.printEmployeesByNumberOfAssignedBuildings(2L);

        //Sorting resident
        //-by name
        ResidentDAO.printResidentsByName();
        //-by age
        ResidentDAO.printResidentsByAge();

        //QUERY METHODS
        //Assigned buildings by employee by company
        CompanyDAO.printEmployeesByNumberOfAssignedBuildings(1L);

        //Apartments in a building
        //-listing all
        BuildingDAO.printListOfApartments(1L);
        BuildingDAO.printListOfApartments(2L);
        BuildingDAO.printListOfApartments(3L);
        //-by number
        BuildingDAO.printTotalNumberOfApartments(1L);
        BuildingDAO.printTotalNumberOfApartments(2L);
        BuildingDAO.printTotalNumberOfApartments(3L);

        //Resident in a building
        //-listing all
        ResidentDAO.printResidentsInBuilding(1L);
        ResidentDAO.printResidentsInBuilding(2L);
        //-by number
        System.out.println(ResidentDAO.getNumberOfResidentsInBuilding(1L));
        System.out.println(ResidentDAO.getNumberOfResidentsInBuilding(2L));

        //Unpaid fees
        //-by companies
        FeeDAO.printUnpaidFeesByCompany(company1);
        FeeDAO.printUnpaidFeesByCompany(company2);
        //-by building
        FeeDAO.printUnpaidFeesByBuilding(building1);
        FeeDAO.printUnpaidFeesByBuilding(building2);
        FeeDAO.printUnpaidFeesByBuilding(building3);
        //-by employee
        FeeDAO.printUnpaidFeesByEmployee(14L);
        FeeDAO.printUnpaidFeesByEmployee(15L);
        FeeDAO.printUnpaidFeesByEmployee(16L);


        //Paid Fees
        //-by companies
        FeeDAO.printPaidFeesByCompany(company1);
        FeeDAO.printPaidFeesByCompany(company2);
        //-by building
        FeeDAO.printPaidFeesByBuilding(1L);
        FeeDAO.printPaidFeesByBuilding(2L);
        FeeDAO.printPaidFeesByBuilding(3L);
        //-by employee
        FeeDAO.printPaidFeesByEmployee(14L);
        FeeDAO.printPaidFeesByEmployee(15L);
        FeeDAO.printPaidFeesByEmployee(16L);

        //FILE WRITING
        FeeDAO.writePaidFeesToFile("paid_fees.txt");
    }
}

