package ServiceTest;

import Request.LoadRequest;
import Request.LoginRequest;
import Result.LoginResult;
import Result.OneEventResult;
import Service.ClearService;
import Service.EventService;
import Service.LoadService;
import Service.LoginService;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileReader;

import static org.junit.jupiter.api.Assertions.*;

public class EventServiceTest {
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
    void GetEventPass() {
        EventService event = new EventService();
        OneEventResult testedResult = event.getEvent(result.getAuthtoken(), "Sheila_Marriage");
        OneEventResult expectedResult = new OneEventResult("sheila", "Sheila_Marriage",
                "Sheila_Parker", 34.05, -117.75, "United States", "Los Angeles",
                "marriage", 2012, true);
        assertEquals(expectedResult, testedResult);
        assertTrue(testedResult.isSuccess());
    }

    @Test
    void GetEventFail() {
        EventService event = new EventService();
        OneEventResult testedResult = event.getEvent(result.getAuthtoken(), "Invalid_Event_Input");
        OneEventResult expectedResult = new OneEventResult("error - Invalid EventID");
        assertEquals(expectedResult, testedResult);
        assertFalse(testedResult.isSuccess());
    }
}
