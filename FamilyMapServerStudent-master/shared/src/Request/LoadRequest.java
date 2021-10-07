package Request;

import Model.*;

/**
 * LoadRequest class in the Request package
 * Request users, persons, and events data
 */
public class LoadRequest {
    private Users[] users = null;
    private Persons[] persons = null;
    private Events[] events = null;

    public LoadRequest() {}

    public LoadRequest(Users[] users, Persons[] persons, Events[] events) {
        this.users = users;
        this.persons = persons;
        this.events = events;

    }

    public Users[] getUsers() {
        return users;
    }

    public void setUsers(Users[] users) {
        this.users = users;
    }

    public Persons[] getPersons() {
        return persons;
    }

    public void setPersons(Persons[] persons) {
        this.persons = persons;
    }

    public Events[] getEvents() {
        return events;
    }

    public void setEvents(Events[] events) {
        this.events = events;
    }
}
