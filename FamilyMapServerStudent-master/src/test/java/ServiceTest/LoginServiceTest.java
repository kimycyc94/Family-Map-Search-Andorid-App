package ServiceTest;

import Request.LoadRequest;
import Request.LoginRequest;
import Result.LoginResult;
import Service.*;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.junit.jupiter.api.*;
import java.io.File;
import java.io.FileReader;

import static org.junit.jupiter.api.Assertions.*;

public class LoginServiceTest {
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
    }

    @AfterEach
    public void reSet() {
        ClearService clear = new ClearService();
        clear.clear();
    }

    @Test
    void LoginPass() {
        LoginService loginServe = new LoginService();
        LoginRequest r = new LoginRequest("sheila","parker");
        LoginResult testedResult = loginServe.login(r);
        LoginResult expectedResult = new LoginResult(null, "sheila", "Sheila_Parker", null, true);
        assertNotNull(testedResult);  // if success is true, right data, etc
        assertEquals(expectedResult.getUsername(), testedResult.getUsername());
        assertEquals(expectedResult.getPersonID(), testedResult.getPersonID());
        assertEquals(expectedResult.getMessage(), testedResult.getMessage());
        assertEquals(expectedResult.getSuccess(), testedResult.getSuccess());
        assertTrue(testedResult.getSuccess());
    }

    @Test
    void LoginFail() {
        LoginService loginServe = new LoginService();
        LoginRequest r = new LoginRequest("kimsyc94","5882");
        LoginResult testedResult = loginServe.login(r);
        LoginResult expectedResult = new LoginResult("error - Login has been failed due to incorrect information.");
        assertEquals(expectedResult.getUsername(), testedResult.getUsername());
        assertEquals(expectedResult.getPersonID(), testedResult.getPersonID());
        assertEquals(expectedResult.getMessage(), testedResult.getMessage());
        assertEquals(expectedResult.getSuccess(), testedResult.getSuccess());
        assertFalse(testedResult.getSuccess());
    }
}


