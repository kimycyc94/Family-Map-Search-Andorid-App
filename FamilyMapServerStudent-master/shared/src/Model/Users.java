package Model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/**
 * Users model class in model package
 */
public class Users{
    /**
     * user's name string
     */
    @SerializedName("username")
    private String username;
    /**
     * user's password string
     */
    private String password;
    /**
     * user's email address string
     */
    private String email;
    /**
     * user's first name string
     */
    private String firstName;
    /**
     * user's last name string
     */
    private String lastName;
    /**
     * user's gender
     */
    private String gender;
    /**
     * user's person ID string
     */
    private String personID;

    /**
     * Users constructor
     */
    public Users() {
        username = "";
        password = "";
        email = "";
        firstName = "";
        lastName = "";
        gender = "";
        personID = "";
    }

    /**
     * Persons constructor with parameters
     */
    public Users(String userName, String password, String email, String firstName,
                 String lastName, String gender, String personID) {
        this.username = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personID = personID;
    }

    /**
     * @param o compare all the data members
     * @return if Users' all the data members are the same, return true
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return Objects.equals(username, users.username) &&
                Objects.equals(password, users.password) &&
                Objects.equals(email, users.email) &&
                Objects.equals(firstName, users.firstName) &&
                Objects.equals(lastName, users.lastName) &&
                Objects.equals(gender, users.gender) &&
                Objects.equals(personID, users.personID);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
}
