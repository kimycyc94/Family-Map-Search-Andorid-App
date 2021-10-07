package Request;

/**
 * LoginRequest Class in the Request package
 * Request login by username and password
 */
public class LoginRequest {
    /**
     * user name
     */
    private String username = null;
    /**
     * user's password
     */
    private String password = null;

    public LoginRequest() {}

    public LoginRequest(String userName, String password) {
        this.username = userName;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword (String password) {
        this.password = password;
    }
}
