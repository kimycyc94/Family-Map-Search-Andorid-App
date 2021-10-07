package ServiceTest;

import Model.Events;
import Request.LoadRequest;
import Request.LoginRequest;
import Result.AllEventsResult;
import Result.LoginResult;
import Service.AllEventsService;
import Service.ClearService;
import Service.LoadService;
import Service.LoginService;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AllEventsServiceTest {
    LoginResult result;
    @BeforeEach
    public void setUp() throws Exception {
        ClearService clear = new ClearService();
        clear.clear();
        //Read and load file
        File file = new File("passoffFiles/LoadData.json");
        FileReader fileReader = new FileReader(file);
        JsonReader reader = new JsonReader(fileReader);
        Gson gson = new Gson();
        LoadRequest r = gson.fromJson(reader, LoadRequest.class);
        LoadService loadServe = new LoadService();
        loadServe.load(r);

        //Login
        LoginService loginServe = new LoginService();
        LoginRequest logr = new LoginRequest("sheila", "parker");
        result = loginServe.login(logr);
    }

    @AfterEach
    public void reSet() {
        ClearService clear = new ClearService();
        clear.clear();
    }

    @Test
    void GetAllEventsPass() {
        AllEventsService allEvents = new AllEventsService();
        Events[] events = new Events[2];
        Events event = new Events("Sheila_Birth", "sheila", "Sheila_Parker",
                -36.1833, 144.9667, "Australia", "Melbourne", "birth", 1970);
        Events event1 = new Events("Mrs_Jones_Surf", "sheila", "Mrs_Jones",
                -27.9833, 153.4, "Australia", "Gold Coast", "Learned to Surf", 2000);
        events[0] = event;
        events[1] = event1;

        AllEventsResult expectedResult = new AllEventsResult(events, true);
        AllEventsResult testedResult = allEvents.getAllEvents(result.getAuthtoken());
        assertNotNull(testedResult);
        assertEquals(16, testedResult.getData().length);
        assertEquals(expectedResult.getData()[0], testedResult.getData()[0]);
        assertEquals(expectedResult.getData()[1], testedResult.getData()[15]);
        assertTrue(testedResult.isSuccess());
    }

    @Test
    void GetAllEventsFail() {
        AllEventsService allEvents = new AllEventsService();
        AllEventsResult testedResult = allEvents.getAllEvents("Invalid authToken input");
        AllEventsResult expectedResult = new AllEventsResult("error - Invalid AuthToken");
        assertEquals(expectedResult.getMessage(), testedResult.getMessage());
        assertFalse(testedResult.isSuccess());
    }
}
