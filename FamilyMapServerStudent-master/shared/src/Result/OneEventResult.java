package Result;

import java.util.Objects;

/**
 * OneEventResult class in the Result package
 * It Contains person's Event information
 */
public class OneEventResult {
    /**
     * user's name
     */
    private String associatedUsername;

    // for the Junit test
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OneEventResult that = (OneEventResult) o;
        return year == that.year && success == that.success && Objects.equals(associatedUsername, that.associatedUsername) && Objects.equals(eventID, that.eventID) && Objects.equals(personID, that.personID) && Objects.equals(latitude, that.latitude) && Objects.equals(longitude, that.longitude) && Objects.equals(country, that.country) && Objects.equals(city, that.city) && Objects.equals(eventType, that.eventType) && Objects.equals(message, that.message);
    }

    /**
     * event's unique ID
     */
    private String eventID;
    /**
     * person's unique ID
     */
    private String personID;
    /**
     * latitude where event happens
     */
    private Double latitude;
    /**
     * longitude where event happens
     */
    private Double longitude;
    /**
     * country where event happens
     */
    private String country;
    /**
     * city where event happens
     */
    private String city;
    /**
     * event type
     */
    private String eventType;
    /**
     * when the event happens
     */
    private int year;
    /**
     * error message string
     */
    private String message;

    /**
     * successfully ran or not boolean
     */
    private boolean success;



    public OneEventResult(String message) { this.message = message; }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

    public OneEventResult(String message, int year) {
        this.message = message;

    }
    /**
     * OneEventResult constructor
     */
    public OneEventResult(String associatedUsername, String eventID, String personID, Double latitude, Double longitude, String country, String city, String eventType, int year, boolean success) {
        this.associatedUsername = associatedUsername;
        this.eventID = eventID;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
        this.success = success;
    }
}
