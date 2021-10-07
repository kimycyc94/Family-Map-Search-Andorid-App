package ServiceTest;

import Dao.DataAccessException;
import Dao.Database;
import Result.ParentResult;
import Service.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ClearServiceTest {
    @Test
    void clearPass() {
        ClearService clearServe = new ClearService();
        ParentResult testedResult = clearServe.clear();
        ParentResult expectedResult = new ParentResult("Clear succeeded.", true);
        assertEquals(expectedResult, testedResult);
        assertTrue(testedResult.getSuccess());
    }

    @Test
    void clearNotNullPass() {
        ClearService clearServe = new ClearService();
        ParentResult testedResult = clearServe.clear();
        assertNotNull(testedResult);
        assertTrue(testedResult.getSuccess());
    }
}
