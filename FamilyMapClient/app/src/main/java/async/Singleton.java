package async;

import com.google.android.gms.maps.model.Marker;

import java.lang.reflect.Array;
import java.util.*;

import Result.*;
import Model.*;

public class Singleton {
    private static Singleton singleton;    // Only one. Belongs to class itself. No matter where you call it, instances are the same.
    private String authToken;
    private String personID;
    private Events eventID;
    private LoginResult loginResult;
    private Map<String, Persons> allPersons;
    private Map<String, Events> allEvents;

    private Set<Events> filteredEvents = new HashSet<>();
    private Map<Marker, Events> eventMarkers = new HashMap<>();
    private Map<String, String> typesNcolors = new HashMap<>();
    private ArrayList<Events> userEventList = new ArrayList<>();
    private ArrayList<Persons> userPersonList = new ArrayList<>();

    private boolean maleSwitch = true;
    private boolean femaleSwitch = true;
    private boolean spouseSwitch = true;
    private boolean fatherSwitch = true;
    private boolean motherSwitch = true;
    private boolean lifeStoryLineSwitch = true;
    private boolean familyTreeLineSwitch = true;
    private boolean settingsSwitch = false;

    public Singleton() { }

    public static Singleton getSingleton() {
        if (singleton == null) {
            singleton = new Singleton();
        }
        return singleton;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public Events getEventID() { return eventID; }

    public void setEventID(Events eventID) { this.eventID = eventID; }

    public LoginResult getLoginResult() { return loginResult; }

    public void setLoginResult(LoginResult loginResult) { this.loginResult = loginResult; }

    public Persons getPerson(String personID) {
        try {
            return allPersons.get(personID);
            } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Events getEvent(String eventID) {
        try {
            return allEvents.get(eventID);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map<String, Persons> getAllPersons() {
        return allPersons;
    }

    public void setAllPersons(Map<String, Persons> allPersons) {
        this.allPersons = allPersons;
    }

    public Map<String, Events> getAllEvents() {
        return allEvents;
    }

    public void setAllEvents(Map<String, Events> allEvents) {
        this.allEvents = allEvents;
        ArrayList<String> types = new ArrayList<>();
        ArrayList<String> eventColors = new ArrayList();

        eventColors.add("green"); eventColors.add("blue"); eventColors.add("orange"); eventColors.add("violet"); eventColors.add("magenta");
        eventColors.add("yellow"); eventColors.add("red"); eventColors.add("azure"); eventColors.add("Cyan"); eventColors.add("Burgundy");
        eventColors.add("Beige");

        for (Events e : singleton.getAllEvents().values()) {
            if (!types.contains(e.getEventType())) {
                types.add(e.getEventType());
            }
        }

        int i = 0;
        for (String t : types) {
            if (eventColors.size() < i) {
                i = 0;
            }
            typesNcolors.put(t, eventColors.get(i));
            i++;
        }
    }

    public ArrayList<Events> getUserEventList() {
        userEventList.addAll(singleton.getAllEvents().values());
        return userEventList;
    }

    public void setUserEventList(ArrayList<Events> userEventList) {
        this.userEventList = userEventList;
    }

    public ArrayList<Persons> getUserPersonList() {
        userPersonList.addAll(singleton.getAllPersons().values());
        return userPersonList;
    }

    public void setUserPersonList(ArrayList<Persons> userPersonList) {
        this.userPersonList = userPersonList;
    }

    public Set<Events> getFilteredEvents() {
        return filteredEvents;
    }

    public void setFilteredEvents(Set<Events> filteredEvents) {
        this.filteredEvents = filteredEvents;
    }

    public Map<Marker, Events> getEventMarkers() {
        return eventMarkers;
    }

    public void setEventMarkers(Map<Marker, Events> eventMarkers) {
        this.eventMarkers = eventMarkers;
    }

    public Map<String, String> getTypesNcolors() {
        return typesNcolors;
    }

    public void setTypesNcolors(Map<String, String> typesNcolors) {
        this.typesNcolors = typesNcolors;
    }

    //Switches//
    public boolean isMaleSwitch() {
        return maleSwitch;
    }

    public void setMaleSwitch(boolean maleSwitch) {
        this.maleSwitch = maleSwitch;
    }

    public boolean isFemaleSwitch() {
        return femaleSwitch;
    }

    public void setFemaleSwitch(boolean femaleSwitch) {
        this.femaleSwitch = femaleSwitch;
    }

    public boolean isSpouseSwitch() {
        return spouseSwitch;
    }

    public void setSpouseSwitch(boolean spouseSwitch) {
        this.spouseSwitch = spouseSwitch;
    }

    public boolean isFatherSwitch() {
        return fatherSwitch;
    }

    public void setFatherSwitch(boolean fatherSwitch) {
        this.fatherSwitch = fatherSwitch;
    }

    public boolean isMotherSwitch() {
        return motherSwitch;
    }

    public void setMotherSwitch(boolean motherSwitch) {
        this.motherSwitch = motherSwitch;
    }

    public boolean isLifeStoryLineSwitch() {
        return lifeStoryLineSwitch;
    }

    public void setLifeStoryLineSwitch(boolean lifeStoryLineSwitch) {
        this.lifeStoryLineSwitch = lifeStoryLineSwitch;
    }

    public boolean isFamilyTreeLineSwitch() {
        return familyTreeLineSwitch;
    }

    public void setFamilyTreeLineSwitch(boolean familyTreeLineSwitch) {
        this.familyTreeLineSwitch = familyTreeLineSwitch;
    }

    public boolean isSettingsSwitch() {
        return settingsSwitch;
    }

    public void setSettingsSwitch(boolean settingsSwitch) {
        this.settingsSwitch = settingsSwitch;
    }
}
