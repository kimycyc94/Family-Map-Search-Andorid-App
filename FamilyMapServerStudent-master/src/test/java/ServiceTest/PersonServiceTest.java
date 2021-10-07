package ServiceTest;

import Request.LoadRequest;
import Request.LoginRequest;
import Result.LoginResult;
import Result.OnePersonResult;
import Service.ClearService;
import Service.LoadService;
import Service.LoginService;
import Service.PersonService;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;

import static org.junit.jupiter.api.Assertions.*;

public class PersonServiceTest {
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
    void GetPersonPass() {
        PersonService person = new PersonService();
        OnePersonResult testedResult = person.getPerson(result.getAuthtoken(), "Sheila_Parker");
        OnePersonResult expectedResult = new OnePersonResult("sheila", "Sheila_Parker",
                "Sheila", "Parker", "f", "Blaine_McGary", "Betty_White",
                "Davis_Hyer", true);
        assertEquals(expectedResult, testedResult);
        assertTrue(testedResult.isSuccess());
    }

    @Test
    void GetPersonFail() {
        PersonService person = new PersonService();
        OnePersonResult testedResult = person.getPerson(result.getAuthtoken(), "Invalid_PersonID_Input");
        OnePersonResult expectedResult = new OnePersonResult("error - Invalid PersonID");
        assertEquals(expectedResult, testedResult);
        assertFalse(testedResult.isSuccess());
    }
}
