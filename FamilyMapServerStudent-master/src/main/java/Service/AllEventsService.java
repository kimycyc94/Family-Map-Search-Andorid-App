package Service;

import Dao.*;
import Model.*;
import Result.AllEventsResult;
import java.sql.Connection;

/**
 * AllEventsService class in service package
 */
public class AllEventsService {
    public AllEventsService() { }

    public AllEventsResult getAllEvents(String authToken) {
        Database db = new Database();
        try {
            Connection conn = db.openConnection();
            Events[] events;
            AuthTokenDao aDao = new AuthTokenDao(conn);
            AuthToken token = aDao.findAuthToken(authToken);
            boolean success;

            if(token == null) {
                db.closeConnection(false);
                return new AllEventsResult("error - Invalid AuthToken");
            }
            EventDao eDao = new EventDao(conn);
            if(eDao.findAll(token.getAssociatedUsername()) == null) {
                db.closeConnection(false);
                return new AllEventsResult("error - Unable to find all events");
            }
            events = eDao.findAll(token.getAssociatedUsername());
            db.closeConnection(true);
            success = true;
            return new AllEventsResult(events, success);
        } catch (DataAccessException e) {
            try {
                db.closeConnection(false);
            } catch (DataAccessException e2) {
                e2.printStackTrace();
            }
        }
        return new AllEventsResult("error - Internal error encountered while getting all events from the database");
    }
}
