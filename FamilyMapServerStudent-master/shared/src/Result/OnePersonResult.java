package Result;

import java.util.Objects;

/**
 * OnePersonResult class in the Result package
 * It Contains one person's information
 */
public class OnePersonResult {
    /**
     * user's name
     */
    private String associatedUsername;
    /**
     * person's unique ID
     */
    private String personID;
    /**
     * person's firstName
     */
    private String firstName;
    /**
     * person's lastName
     */
    private String lastName;
    /**
     * person's gender
     */
    private String gender;
    /**
     * fatherID (can be null)
     */
    private String fatherID;
    /**
     * motherID (can be null)
     */
    private String motherID;
    /**
     * spouseID (can be null)
     */
    private String spouseID;
    /**
     * error message string
     */
    private String message;

    /**
     * successfully ran or not boolean
     */
    private boolean success;

    // For the Junit test
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OnePersonResult that = (OnePersonResult) o;
        return success == that.success && Objects.equals(associatedUsername, that.associatedUsername) && Objects.equals(personID, that.personID) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(gender, that.gender) && Objects.equals(fatherID, that.fatherID) && Objects.equals(motherID, that.motherID) && Objects.equals(spouseID, that.spouseID) && Objects.equals(message, that.message);
    }

    public OnePersonResult(String message) { this.message = message; }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
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

    public String getGender() { return gender; }

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

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) { }

    public OnePersonResult() {}
    /**
     * OnePersonResult constructor
     */
    public OnePersonResult(String associatedUsername, String personID, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID, boolean success) {
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
        this.success = success;
    }
}
