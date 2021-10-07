package ServiceTest;

import Request.RegisterRequest;
import Result.RegisterResult;
import Service.ClearService;
import Service.RegisterService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterServiceTest {
    @BeforeEach
    public void clearTable() {
        ClearService clear = new ClearService();
        clear.clear();
    }

    @AfterEach
    public void reSet() {
        ClearService clear = new ClearService();
        clear.clear();
    }

    @Test
    void RegisterPass() {  //
        RegisterService regiServe = new RegisterService();
        RegisterRequest r = new RegisterRequest("kimsyc94", "cookie825", "kimycyc94@gmail.com",
                "Youngchan", "Kim", "m");
        RegisterResult testedResult = regiServe.register(r);
        assertNotNull(testedResult.getAuthtoken());
        assertEquals(testedResult.getUsername(), "kimsyc94");
        assertTrue(testedResult.isSuccess());
    }

    @Test
    void RegisterFail() {
        RegisterService regiServe = new RegisterService();
        RegisterRequest r = new RegisterRequest(null, null, "kimycyc94@gmail.com",
                "Youngchan", "Kim", "m");
        RegisterResult testedResult = regiServe.register(r);
        RegisterResult expectedResult = new RegisterResult("error - Invalid or Missing error");
        assertEquals(expectedResult, testedResult);
        assertFalse(testedResult.isSuccess());
    }
}
