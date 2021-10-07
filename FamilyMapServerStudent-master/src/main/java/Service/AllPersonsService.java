package Service;

import Dao.*;
import Model.*;
import Result.AllPersonsResult;
import java.sql.Connection;

/**
 * AllPersonsService class in service package
 */
public class AllPersonsService {
    public AllPersonsService() { }

    public AllPersonsResult getAllPersons(String authToken) {
        Database db = new Database();
        try {
            Connection conn = db.openConnection();
            Persons[] persons;
            AuthTokenDao aDao = new AuthTokenDao(conn);
            PersonDao pDao = new PersonDao(conn);
            AuthToken token = aDao.findAuthToken(authToken);
            boolean success;

            if (token == null) {
                db.closeConnection(false);
                return new AllPersonsResult("error - Invalid AuthToken");
            }
            persons = pDao.findAll(token.getAssociatedUsername());
            db.closeConnection(true);
            success = true;
            return new AllPersonsResult(persons, success);
        } catch (DataAccessException e) {
            try {
                db.closeConnection(false);
            } catch (DataAccessException e2) {
                e2.printStackTrace();
            }
        }
        return new AllPersonsResult("error - Internal error encountered while getting all persons from the database");
    }
}
