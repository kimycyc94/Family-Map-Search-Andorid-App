package Service;

import Dao.*;
import Model.*;
import Request.LoadRequest;
import Result.LoadResult;

import java.sql.Connection;

/**
 * Load Service class in service package
 */
public class LoadService {
    private Connection conn;
    boolean success;
    public LoadService() {}
    /**
     * @param r is load request
     * @return load Result will be returned
     */
    public LoadResult load(LoadRequest r) {
        Database db = new Database();
        ClearService clearServe = new ClearService();
        try {
            clearServe.clear();
            conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
            PersonDao pDao = new PersonDao(conn);
            UserDao uDao = new UserDao(conn);

            Events[] events = r.getEvents();
            Persons[] persons = r.getPersons();
            Users[] users = r.getUsers();

            if (events == null || persons == null || users == null) {
                throw new Exception ("error - Missing values");
            }

            int numE = 0;
            int numP = 0;
            int numU = 0;

            try {
                for (Events e : events) {
                    eDao.insert(e);
                    numE++;
                }
                for (Persons p : persons) {
                    pDao.insert(p);
                    numP++;
                }
                for (Users u : users) {
                    uDao.insert(u);
                    numU++;
                }
            } catch (DataAccessException e) {
                try {
                    db.closeConnection(false);
                } catch (DataAccessException e2) {
                    e2.printStackTrace();
                }
                return new LoadResult("error - Load has been failed due to invalid inputs.");
            }
            success = true;
            db.closeConnection(true);
            return new LoadResult("Successfully added " + numU + " users, " + numP + " persons, and " + numE + " events to the database.", success);
        } catch (DataAccessException e) {
            try {
                db.closeConnection(false);
            } catch (DataAccessException e2) {
                e2.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new LoadResult("error - Internal error encountered while loading values");
    }
}
