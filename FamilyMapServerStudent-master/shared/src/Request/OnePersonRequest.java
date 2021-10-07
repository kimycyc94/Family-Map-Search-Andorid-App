package Request;

import Model.AuthToken;

/**
 * OnePersonRequest Class in the Request package
 * Request one person's data by auth token
 */
public class OnePersonRequest {
    /**
     * AuthToken
     */
    private AuthToken token;

    public OnePersonRequest() { }

    public AuthToken getToken() {
        return token;
    }

    public void setToken(AuthToken token) {
        this.token = token;
    }
}
