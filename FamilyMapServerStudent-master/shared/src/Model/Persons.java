package Model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;
import java.util.UUID;

/**
 * Persons model class in Model package
 */
public class Persons{
    /**
     * person ID string
     */
    private String personID;
    /**
     * user's name string
     */
    private String associatedUsername;
    /**
     * person's first name string
     */
    private String firstName;
    /**
     * person's last name string
     */
    private String lastName;
    /**
     * person's last name
     */
    private String gender;
    /**
     * person's father's ID string
     */
    private String fatherID;
    /**
     * person's mother's ID string
     */
    private String motherID;
    /**
     * person's spouse's ID string
     */
    private String spouseID;

    /**
     * Persons constructor
     */
    public Persons() { }

    /**
     * Persons constructor with parameters
     */
    public Persons(String personID, String associatedUserName, String firstName, String lastName,
                   String gender, String fatherID, String motherID, String spouseID) {
        this.personID = personID;
        this.associatedUsername = associatedUserName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }

    /**
     * Persons constructor with personID = UUID.randomUUID().toString()
     */
    public Persons(String associatedUserName, String firstName, String lastName,
                   String gender, String fatherID, String motherID, String spouseID) {
        this.personID = UUID.randomUUID().toString();
        this.associatedUsername = associatedUserName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }

    /**
     * Persons constructor with null fatherID, motherID, and spouseID
     */
    public Persons(String personID, String associatedUserName, String firstName, String lastName, String gender) {
        this.personID = personID;
        this.associatedUsername = associatedUserName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = null;
        this.motherID = null;
        this.spouseID = null;
    }

    /**
     * Persons constructor with associated user name, fatherName, lastName, and gender
     */
    public Persons(String associatedUserName, String fatherName, String lastName, String m) {
        this.associatedUsername = associatedUserName;
        this.firstName = fatherName;
        this.lastName = lastName;
        this.gender = m;

    }

    /**
     * @param o compare all the data members
     * @return if persons' all the data members are the same, return true
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Persons persons = (Persons) o;
        return Objects.equals(personID, persons.personID) &&
                Objects.equals(associatedUsername, persons.associatedUsername) &&
                Objects.equals(firstName, persons.firstName) &&
                Objects.equals(lastName, persons.lastName) &&
                Objects.equals(gender, persons.gender) &&
                Objects.equals(fatherID, persons.fatherID) &&
                Objects.equals(motherID, persons.motherID) &&
                Objects.equals(spouseID, persons.spouseID);
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }
}
