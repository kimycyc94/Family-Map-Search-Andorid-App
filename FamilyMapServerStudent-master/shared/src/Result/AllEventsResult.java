package Result;

import Model.Events;

import java.util.Arrays;
import java.util.Objects;

/**
 * AllEventsResult class in the Result package
 * It Contains person's all Events information
 */
public class AllEventsResult {
    /**
     * Array of event objects
     */
    private Events[] data;
    /**
     * error message string
     */
    private String message;
    /**
     * Success boolean
     */
    private boolean success;

    public AllEventsResult() { }
    /**
     * AllEventsResult constructor
     */
    public AllEventsResult(Events[] eventsArray, boolean success) {
        this.data = eventsArray;
        this.success = success;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AllEventsResult that = (AllEventsResult) o;
        return success == that.success && Arrays.equals(data, that.data) && Objects.equals(message, that.message);
    }

    public AllEventsResult(String message) { this.message = message; }

    public Events[] getData() {
        return data;
    }

    public void setData(Events[] data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }
}
