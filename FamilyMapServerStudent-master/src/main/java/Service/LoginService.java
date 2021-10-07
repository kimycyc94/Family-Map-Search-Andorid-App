package Service;

import Dao.AuthTokenDao;
import Dao.DataAccessException;
import Dao.Database;
import Dao.UserDao;
import Model.AuthToken;
import Model.Users;
import Request.LoginRequest;
import Result.LoginResult;
import java.sql.Connection;

/**
 * Login class in service package
 */
public class LoginService {
    private Database db = new Database();
    private Connection conn;
    boolean success;
    /**
     * @param r is login request
     * @return login Result will be returned
     */
    public LoginResult login(LoginRequest r) {
        try {
            conn = db.openConnection();
            UserDao uDao = new UserDao(conn);
            Users userReq = uDao.findUser(r.getUsername());
            System.out.println(userReq + " " + r.getUsername());
            //check user request
            if (userReq != null && userReq.getPassword().equals(r.getPassword())) {
                AuthTokenDao aDao = new AuthTokenDao(conn);
                AuthToken token = aDao.findTokenFromUserName(r.getUsername());
                if (token == null) {
                    token = new AuthToken(userReq.getUsername());
                    aDao.addAuthToken(token);
                }
                db.closeConnection(true);
                success = true;
                return new LoginResult(token.getAuthtoken(), userReq.getUsername(), userReq.getPersonID(), success);
            }
            else {
                db.closeConnection(false);
                return new LoginResult("error - Login has been failed due to incorrect information.");
            }
        } catch (DataAccessException e) {
            try {
                db.closeConnection(false);
            } catch (DataAccessException e2) {
                e2.printStackTrace();
            }
        }
        return new LoginResult("error - Internal error encountered while logging in");   /*result will be returned*/
    }
}
