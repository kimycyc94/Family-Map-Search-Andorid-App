package Service;

import Dao.*;
import Model.AuthToken;
import Model.Persons;
import Result.OnePersonResult;
import java.sql.Connection;

/**
 * Person class in service package
 */
public class PersonService {
    private Database db = new Database();
    private Connection conn;
    boolean success;

    public PersonService() {}
    /**
     * @param authToken
     * @param personID
     * @return
     */
    public OnePersonResult getPerson (String authToken, String personID) {
        try {
            conn = db.openConnection();
            AuthTokenDao aDao = new AuthTokenDao(conn);
            AuthToken token = aDao.findAuthToken(authToken);

            if (token == null) {
                db.closeConnection(false);
                return new OnePersonResult("error - Invalid AuthToken");
            }

            PersonDao pDao = new PersonDao(conn);
            Persons person = pDao.findPerson(personID);
            if (person == null) {
                db.closeConnection(false);
                return new OnePersonResult("error - Invalid PersonID");
            }

            if(!person.getAssociatedUsername().equals(token.getAssociatedUsername())) {
                db.closeConnection(false);
                return new OnePersonResult("error - unmatched userName");
            }
            db.closeConnection(true);
            success = true;
            return new OnePersonResult(person.getAssociatedUsername(), personID, person.getFirstName(), person.getLastName(),
                    person.getGender(), person.getFatherID(), person.getMotherID(), person.getSpouseID(), success);

        } catch (DataAccessException e) {
            try {
                db.closeConnection(false);
            } catch (DataAccessException e2) {
                e2.printStackTrace();
            }
        }
        return new OnePersonResult("error - Internal error Encountered while getting single person from the data");
    }
}
