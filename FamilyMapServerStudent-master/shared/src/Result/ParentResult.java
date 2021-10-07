package Result;

import java.util.Objects;

/**
 * ParentResult class in the Result package
 * All result classes were built based on this structure
 */
public class ParentResult {
    /**
     * error message string
     */
    private String message;
    /**
     * success boolean
     */
    private boolean success;

    public ParentResult() { }

    public ParentResult(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    // for the Junit test
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParentResult that = (ParentResult) o;
        return success == that.success && Objects.equals(message, that.message);
    }

    public ParentResult(String message) { this.message = message; }

    public Boolean getSuccess() { return success; }

    public String getMessage() { return message; }

    public void setSuccess(Boolean success) { this.success = success; }

    public void setMessage(String message) { this.message = message; }
}
