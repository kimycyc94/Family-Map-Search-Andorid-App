package Model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/**
 * Events model class in Model package
 */
public class Events {
    /**
     * event ID string
     */
    private String eventID;
    /**
     * user's name string
     */
    @SerializedName("associatedUsername")
    private String associatedUsername;
    /**
     * person ID string
     */
    private String personID;
    /**
     * where event happens: latitude double
     */
    private Double latitude;
    /**
     * where event happens: longitude double
     */
    private Double longitude;
    /**
     * where event happens: country name string
     */
    private String country;
    /**
     * where event happens: city name string
     */
    private String city;
    /**
     * what is the event type string
     */
    private String eventType;
    /**
     * when did the event happen integer
     */
    private int year;

    /**
     * Events constructor
     */
    public Events() {   // I may need more constructor
        eventID = "";
        associatedUsername = "";
        personID = "";
        latitude = 0.0;
        longitude = 0.0;
        country = "";
        city = "";
        eventType = "";
        year = 0;
    }

    /**
     * Events constructor with parameters
     */
    public Events(String eventID, String associatedUserName, String personID, Double latitude, Double longitude,
                  String country, String city, String eventType, int year) {
        this.eventID = eventID;
        this.associatedUsername = associatedUserName;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    /**
     *
     * @param o compare all the data members
     * @return if event's all the data members are the same, return true
     */
    @Override
    public boolean equals(Object o) {    // will need in other classes
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Events events = (Events) o;
        return year == events.year &&
                Objects.equals(eventID, events.eventID) &&
                Objects.equals(associatedUsername, events.associatedUsername) &&
                Objects.equals(personID, events.personID) &&
                Objects.equals(latitude, events.latitude) &&
                Objects.equals(longitude, events.longitude) &&
                Objects.equals(country, events.country) &&
                Objects.equals(city, events.city) &&
                Objects.equals(eventType, events.eventType);
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
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
}
