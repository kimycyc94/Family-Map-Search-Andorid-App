package Request;

/**
 * RegisterRequest class in the Request package
 * Request user's register
 */
public class RegisterRequest {
    /**
     * userName
     */
    private String username;
    /**
     * user's password
     */
    private String password;
    /**
     * user's email
     */
    private String email;
    /**
     * user's firstName
     */
    private String firstName;
    /**
     * user's LastName
     */
    private String lastName;
    /**
     * user;s gender
     */
    private String gender;
    /**
     * user's personID
     */
    private String personID;

    public RegisterRequest(String userName, String password, String email, String firstName, String lastName, String gender) {
        this.username = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    public RegisterRequest() { }

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
