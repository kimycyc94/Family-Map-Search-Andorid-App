package Model;

import java.util.Objects;
import java.util.UUID;

/**
 * Authentication Token model class in Model package
 */
public class AuthToken {
    /**
     * Unique authToken string
     */
    String authtoken;
    /**
     * user's name string
     */
    String associatedUsername;

    /**
     * AuthToken Constructor
     */
    public AuthToken() {}

    /**
     * AuthToken Constructor with AuthToken = UUID
     * @param associatedUserName
     */
    public AuthToken(String associatedUserName) {
        this.authtoken = UUID.randomUUID().toString();
        this.associatedUsername = associatedUserName;
    }

    /**
     * AuthToken Constructor with params
     * @param AuthToken
     * @param userName
     */
    public AuthToken(String AuthToken, String userName) {
        this.authtoken = AuthToken;
        this.associatedUsername = userName;
    }

    /**
     * @param o compare all the data members
     * @return if AuthToken's all the data members are the same, return true.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthToken authToken = (AuthToken) o;
        return Objects.equals(authtoken, authToken.authtoken) &&
                Objects.equals(associatedUsername, authToken.associatedUsername);
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }
}
