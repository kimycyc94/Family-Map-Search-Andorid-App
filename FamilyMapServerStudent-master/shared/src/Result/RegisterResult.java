package Result;

import java.util.Objects;

/**
 * RegisterResult class in the Result package
 * It Contains user's information for register
 */
public class RegisterResult {
    /**
     * unique authToken string
     */
    private String authtoken;
    /**
     * user's name
     */
    private String username;
    /**
     * user's person ID
     */
    private String personID;
    /**
     * error message string
     */
    private String message;

    /**
     * successfully ran or not boolean
     */
    private boolean success;

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public boolean isSuccess() { return success; }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getString() { return message; }

    public void setString(String message) {this.message = message;}

    public RegisterResult() {}

    public RegisterResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    /**
     * RegisterResult constructor
     */
    public RegisterResult(String authToken, String userName, String personID, boolean success) {
        this.authtoken = authToken;
        this.username = userName;
        this.personID = personID;
        this.success = success;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisterResult that = (RegisterResult) o;
        return success == that.success && Objects.equals(authtoken, that.authtoken) && Objects.equals(username, that.username) && Objects.equals(personID, that.personID) && Objects.equals(message, that.message);
    }
}
