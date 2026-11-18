package com.auca.abstracts;

import com.auca.exceptions.ValidationException;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;

import java.time.LocalDate;

public abstract class Person implements Identifiable, Registrable {

    private String personId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;

    public Person(String personId, String firstName, String lastName,
                  String email, String phoneNumber, LocalDate dateOfBirth) {
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
    }

    public abstract String getRoleDescription();

    @Override
    public String getId() {
        return personId;
    }

    @Override
    public String getDisplayName() {
        return firstName + " " + lastName;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public void validate() throws ValidationException {
        if (personId == null || personId.isBlank())
            throw new ValidationException("Person ID is required.");
        if (firstName == null || firstName.length() < 2 || firstName.length() > 60)
            throw new ValidationException("First name must contain 2-60 characters.");
        if (lastName == null || lastName.length() < 2 || lastName.length() > 60)
            throw new ValidationException("Last name must contain 2-60 characters.");
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))
            throw new ValidationException("Invalid email.");
        if (phoneNumber == null || !phoneNumber.matches("^\\+?\\d{10,15}$"))
            throw new ValidationException("Phone must contain 10-15 digits.");
        if (dateOfBirth == null || !dateOfBirth.isBefore(LocalDate.now()))
            throw new ValidationException("Date of birth must be in the past.");
    }

    @Override
    public void printDetails() {
        System.out.println(toString());
    }

    @Override
    public String getRegistrationSummary() {
        return getRoleDescription() + ": " + getDisplayName() + " [" + personId + "]";
    }

    public String getPersonId() { return personId; }
    public void setPersonId(String personId) { this.personId = personId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    @Override
    public String toString() {
        return "Person{" +
                "personId='" + personId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}