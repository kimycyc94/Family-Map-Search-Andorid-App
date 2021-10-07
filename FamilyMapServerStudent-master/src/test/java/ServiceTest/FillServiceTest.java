package ServiceTest;

import Request.LoadRequest;
import Request.LoginRequest;
import Result.FillResult;
import Result.LoginResult;
import Result.ParentResult;
import Service.ClearService;
import Service.FillService;
import Service.LoadService;
import Service.LoginService;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.junit.jupiter.api.Assertions.*;

public class FillServiceTest {
    @BeforeEach
    public void clearTable() throws FileNotFoundException {
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
        LoginResult result = loginServe.login(logr);
    }

    @AfterEach
    public void reSet() {
        ClearService clear = new ClearService();
        clear.clear();
    }

    @Test
    void FillPass() throws FileNotFoundException {

        FillService fillServe = new FillService();
        FillResult testedResult = fillServe.fill("sheila", 4);
        FillResult expectedResult = new FillResult("Successfully added 31 persons and 91 events to the database.", true);
        assertEquals(expectedResult, testedResult);
        assertTrue(testedResult.getSuccess());
    }

    @Test
    void FillFail() throws FileNotFoundException {
        FillService fillServe = new FillService();
        FillResult testedResult = fillServe.fill("shelia", -1);
        FillResult expectedResult = new FillResult("error - Invalid generation. Must be 0 or positive integer number");
        assertEquals(expectedResult, testedResult);
        assertFalse(testedResult.getSuccess());
    }
}
