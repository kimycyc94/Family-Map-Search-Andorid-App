package ServiceTest;

import Model.Events;
import Model.Persons;
import Request.LoadRequest;
import Request.LoginRequest;
import Result.AllEventsResult;
import Result.AllPersonsResult;
import Result.LoginResult;
import Service.AllPersonsService;
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

import static org.junit.jupiter.api.Assertions.*;

public class AllPersonsServiceTest {
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
    void GetAllPersonsPass() {
        AllPersonsService allPersons = new AllPersonsService();
        Persons[] persons = new Persons[2];
        Persons person = new Persons("Sheila_Parker", "sheila", "Sheila",
                "Parker", "f", "Blaine_McGary", "Betty_White", "Davis_Hyer");
        Persons person1 = new Persons("Mrs_Jones", "sheila", "Mrs",
                "Jones", "f", null, null, "Frank_Jones");
        persons[0] = person;
        persons[1] = person1;

        AllPersonsResult expectedResult = new AllPersonsResult(persons, true);
        AllPersonsResult testedResult = allPersons.getAllPersons(result.getAuthtoken());
        assertNotNull(testedResult);
        assertEquals(8, testedResult.getData().length);
        assertEquals(expectedResult.getData()[0], testedResult.getData()[0]);
        assertEquals(expectedResult.getData()[1], testedResult.getData()[7]);
        assertTrue(testedResult.isSuccess());
    }

    @Test
    void GetAllPersonsFail() {
        AllPersonsService allPersons = new AllPersonsService();
        AllPersonsResult testedResult = allPersons.getAllPersons("Invalid authToken input");
        AllPersonsResult expectedResult = new AllPersonsResult("error - Invalid AuthToken");
        assertEquals(expectedResult.getMessage(), testedResult.getMessage());
        assertFalse(testedResult.isSuccess());
    }
}
