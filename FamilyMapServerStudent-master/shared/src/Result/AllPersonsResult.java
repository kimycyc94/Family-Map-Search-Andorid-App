package Result;

import Model.Persons;

import java.util.Arrays;
import java.util.Objects;

/**
 * AllPersonsResult class in the Result package
 * It Contains All persons' information
 */
public class AllPersonsResult {
    /**
     * Array of event objects
     */
    private Persons[] data;

    private String message;

    private boolean success;

    public AllPersonsResult() {}
    /**
     * AllEventsResult constructor
     */
    public AllPersonsResult(Persons[] personsArray, boolean success) {
        this.data = personsArray;
        this.success = success;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AllPersonsResult that = (AllPersonsResult) o;
        return success == that.success && Arrays.equals(data, that.data) && Objects.equals(message, that.message);
    }

    public AllPersonsResult(String message) { this.message = message; }

    public Persons[] getData() {
        return data;
    }

    public void setData(Persons[] data) {
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
