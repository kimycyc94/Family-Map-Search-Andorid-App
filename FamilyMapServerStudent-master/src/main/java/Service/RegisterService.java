package Service;

import Request.RegisterRequest;
import Result.FillResult;
import Result.RegisterResult;
import Model.*;
import Dao.*;

import java.sql.Connection;
import java.util.UUID;

/**
 * Register class in service package
 */
public class RegisterService {
    private Database db = new Database();
    private Connection conn;
    boolean success;

    public RegisterService() {}
    /**
     * @param  r is register request
     * @return Register Result will be returned
     */
    public RegisterResult register(RegisterRequest r) {
        try {
            conn = db.openConnection();
            AuthTokenDao aDao = new AuthTokenDao(conn);
            PersonDao pDao = new PersonDao(conn);
            UserDao uDao = new UserDao(conn);
            Users userReq = uDao.findUser(r.getUsername());
            // Is UserName already exist?
            if (userReq != null) {
                db.closeConnection(false);
                return new RegisterResult("error - userName already taken by another user");
            }

            try {
                String pID = UUID.randomUUID().toString();
                Users user = new Users(r.getUsername(), r.getPassword(), r.getEmail(), r.getFirstName(),
                        r.getLastName(), r.getGender(), pID);
                AuthToken token = new AuthToken(user.getUsername());
                Persons person = new Persons(pID, r.getUsername(), r.getFirstName(), r.getLastName(), r.getGender());

                uDao.insert(user);
                aDao.addAuthToken(token);
                pDao.insert(person);

                db.closeConnection(true);
                FillService fillServe = new FillService();
                FillResult fillResult = fillServe.fill(user.getUsername(), 4);
                if (fillResult.getSuccess() != true) {
                    throw new Exception(fillResult.getMessage());
                }
                success = true;
                return new RegisterResult(token.getAuthtoken(), user.getUsername(), user.getPersonID(), success);
            } catch (DataAccessException e) {
                try {
                    db.closeConnection(false);
                } catch (DataAccessException e2) {
                    e2.printStackTrace();
                }
                return new RegisterResult("error - Invalid or Missing error");
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (DataAccessException e) {
            try {
                db.closeConnection(false);
            } catch (DataAccessException e2) {
                e2.printStackTrace();
            }
        }
        return new RegisterResult("error - Internal error Encountered while Register");   /*result will be returned*/
    }
}
