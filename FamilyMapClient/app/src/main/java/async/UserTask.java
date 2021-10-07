package async;

import android.os.AsyncTask;

import com.example.familymapclient.ServerProxy;

import java.util.*;

import Model.*;
import Result.*;

public class UserTask extends AsyncTask<String, Void, Void> {
    private String serverHost;
    private int serverPort;
    private Context context;
    private Singleton user = Singleton.getSingleton();

    @Override
    protected Void doInBackground(String... strings) {
        ServerProxy serverProxy;
        AllPersonsResult personsResult;
        Map<String, Persons> allPersons = new HashMap<String, Persons>();
        AllEventsResult eventsResult;
        Map<String, Events> allEvents = new HashMap<String, Events>();

        if (strings.length == 2) {
            user.setPersonID(strings[0]);   //personID
            user.setAuthToken(strings[1]);  //authToken
            serverProxy = new ServerProxy(serverHost, serverPort);

            personsResult = serverProxy.getAllPersons(strings[1]);
            if (personsResult == null) {
                context.userTaskFailed();
                return null;
            }
            for (Persons person : personsResult.getData()) {
                allPersons.put(person.getPersonID(), person);
            }
            user.setAllPersons(allPersons);

            eventsResult = serverProxy.getAllEvents(strings[1]);
            for (Events event : eventsResult.getData()) {
                allEvents.put(event.getEventID(), event);
            }
            user.setAllEvents(allEvents);

            context.userTaskProceed();
        }
        return null;
    }

    public UserTask(String serverHost, int serverPort, Context context) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        this.context = context;
    }

    public interface Context {
        void userTaskProceed();
        void userTaskFailed();
    }
}
