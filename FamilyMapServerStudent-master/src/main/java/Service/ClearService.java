package Service;

import Dao.DataAccessException;
import Dao.Database;
import Result.ParentResult;

/**
 * ClearService class in service package
 */
public class ClearService {
    boolean success;
    public ClearService() {}

    public ParentResult clear() {
        Database db = new Database();
        try {
            db.openConnection();
            db.clearTables();
            db.closeConnection(true);
            success = true;
            return new ParentResult("Clear succeeded.", success);
        } catch (DataAccessException e) {
            try {
                db.closeConnection(false);
            } catch (DataAccessException e2) {
                e2.printStackTrace();
            }
            return new ParentResult("error - Internal error encountered while clearing from the database");
        }
    }
}
