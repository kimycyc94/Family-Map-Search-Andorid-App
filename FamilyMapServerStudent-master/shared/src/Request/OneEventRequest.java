package Request;

import Model.AuthToken;

/**
 * OneEventRequest class in the Request package
 * Request event data by auth token
 */
public class OneEventRequest {
    /**
     * AuthToken
     */
    private AuthToken token;

    public OneEventRequest() {}

    public AuthToken getToken() {
        return token;
    }

    public void setToken(AuthToken token) {
        this.token = token;
    }
}
