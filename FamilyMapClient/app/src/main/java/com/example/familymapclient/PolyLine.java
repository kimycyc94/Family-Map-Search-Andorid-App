package com.example.familymapclient;

import java.util.*;
import Model.*;
import async.Singleton;

public class PolyLine {
    private Set<Events> lifeStoryEvents;
    private Set<Events> spouseEvents;
    private Persons person;

    public PolyLine(Events event) {
        lifeStoryEvents = new HashSet<>();
        spouseEvents = new HashSet<>();
        person = null;
        createLines(event);
    }

    private void createLines(Events event) {
        person = Singleton.getSingleton().getAllPersons().get(event.getPersonID());
        for (Events e : Singleton.getSingleton().getFilteredEvents()) {
            if (e.getPersonID().equals(event.getPersonID())) {
                lifeStoryEvents.add(e);
            }
        }
    }
}
