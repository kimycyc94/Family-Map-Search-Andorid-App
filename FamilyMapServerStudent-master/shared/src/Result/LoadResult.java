package Result;

import java.util.Objects;

/**
 * LoadResult class in the Result package
 */
public class LoadResult {
    /**
     * unique authToken string
     */
    private String authToken;
    /**
     * user's name
     */
    private String associatedUsername;
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

    public LoadResult() {}

    public LoadResult(String message) { this.message = message; }

    public LoadResult(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    // For the junit test
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoadResult that = (LoadResult) o;
        return success == that.success && Objects.equals(authToken, that.authToken) && Objects.equals(associatedUsername, that.associatedUsername) && Objects.equals(personID, that.personID) && Objects.equals(message, that.message);
    }

    /**
     * LoadResult constructor
     */

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

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
}
