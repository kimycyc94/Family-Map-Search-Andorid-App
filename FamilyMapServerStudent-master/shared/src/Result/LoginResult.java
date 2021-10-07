package Result;

import java.util.Objects;

/**
 * OneEventResult class in the Result package
 * It Contains registered user's information for login
 */
public class LoginResult {
    /**
     * unique authToken string
     */
    private String authtoken;
    /**
     * user's name
     */
    private String username;
    /**
     * person's unique ID
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

    /**
     * LonginResult constructor
     */
    public LoginResult(String authToken, String userName, String personID, boolean success) {
        this.authtoken = authToken;
        this.username = userName;
        this.personID = personID;
        this.success = success;
    }

    public LoginResult(String authToken, String userName, String personID, String message, boolean success) {
        this.authtoken = authToken;
        this.username = userName;
        this.personID = personID;
        this.message = message;
        this.success = success;
    }

    public LoginResult(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginResult that = (LoginResult) o;
        return success == that.success && Objects.equals(authtoken, that.authtoken) && Objects.equals(username, that.username) && Objects.equals(personID, that.personID) && Objects.equals(message, that.message);
    }

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * LoginResult failed
     * @param message
     */
    public LoginResult(String message) {
        this.message = message;
        success = false;
    }
}
