package Result;

import java.util.Objects;

/**
 * FillResult class in the Result package
*/
public class FillResult {
    /**
     * error message string
     */
    private String message;
    /**
     * successfully ran or not boolean
     */
    private boolean success;
    /**
     * user's name
     */
    private String associatedUsername;
    /**
     * how many generations do you want
     */
    private int generations;

    public FillResult(String message) {
        this.message = message;
    }

    public FillResult(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public FillResult() { }

    // For the Junit test
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FillResult that = (FillResult) o;
        return success == that.success && generations == that.generations && Objects.equals(message, that.message) && Objects.equals(associatedUsername, that.associatedUsername);
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

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public int getGenerations() {
        return generations;
    }

    public void setGenerations(int generations) {
        this.generations = generations;
    }
}
