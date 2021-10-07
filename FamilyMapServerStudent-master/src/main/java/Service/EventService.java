package Service;

import Dao.*;
import Model.AuthToken;
import Model.Events;
import Result.OneEventResult;
import java.sql.Connection;

/**
 * Event class in service package
 */
public class EventService {
    private Database db = new Database();
    private Connection conn;
    boolean success;

    public EventService() {}
    /**
     * @param authToken
     * @param eventID
     * @return
     */
    public OneEventResult getEvent(String authToken, String eventID) {
        try {
            conn = db.openConnection();
            AuthTokenDao aDao = new AuthTokenDao(conn);
            AuthToken token = aDao.findAuthToken(authToken);
            if (token == null) {
                db.closeConnection(false);
                return new OneEventResult("error - Invalid AuthToken");
            }

            EventDao eDao = new EventDao(conn);
            Events event = eDao.findEvent(eventID);
            if (event == null) {
                db.closeConnection(false);
                return new OneEventResult("error - Invalid EventID");
            }

            if(!event.getAssociatedUsername().equals(token.getAssociatedUsername())) {
                db.closeConnection(false);
                return new OneEventResult("error - unmatched userName");
            }
            db.closeConnection(true);
            success = true;
            return new OneEventResult(event.getAssociatedUsername(), eventID, event.getPersonID(), event.getLatitude(), event.getLongitude(),
                    event.getCountry(), event.getCity(), event.getEventType(), event.getYear(), success);

        } catch (DataAccessException e) {
            try {
                db.closeConnection(false);
            } catch (DataAccessException e2) {
                e2.printStackTrace();
            }
        }
        return new OneEventResult("error - Internal error Encountered while getting single event from the data");   /*result will be returned*/
    }
}
