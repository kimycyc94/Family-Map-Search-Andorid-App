package ServiceTest;

import Request.LoadRequest;
import Result.LoadResult;
import Service.ClearService;
import Service.LoadService;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;

import static org.junit.jupiter.api.Assertions.*;

public class LoadServiceTest {
    LoadResult result;
    LoadService loadServe;
    LoadRequest r;

    @BeforeEach
    public void setUp() throws Exception {
        ClearService clear = new ClearService();
        clear.clear();
        //Read and load file
        File file = new File("passoffFiles/LoadData.json");
        FileReader fileReader = new FileReader(file);
        JsonReader reader = new JsonReader(fileReader);
        Gson gson = new Gson();
        r = gson.fromJson(reader, LoadRequest.class);
        loadServe = new LoadService();
    }

    @AfterEach
    public void reSet() {
        ClearService clear = new ClearService();
        clear.clear();
    }

    @Test
    void LoadPass() {
        result = loadServe.load(r);
        LoadResult expectedResult = new LoadResult("Successfully added 2 users, 11 persons, and 19 events to the database.", true);
        assertEquals(expectedResult, result);
        assertTrue(result.getSuccess());
    }

    @Test
    void LoadFail() {
        r.getEvents()[1].setCountry(null);
        result = loadServe.load(r);
        LoadResult expectedResult = new LoadResult("error - Load has been failed due to invalid inputs.");
        assertEquals(expectedResult, result);
        assertFalse(result.getSuccess());
    }
}
