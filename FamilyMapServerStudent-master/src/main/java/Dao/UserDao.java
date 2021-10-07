package Dao;

import java.sql.Connection;
import Model.Users;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * UserDao class in Dao package
 * Handles user's data
 */
public class UserDao {
    /**
     * private data member that connect SQL
     */
    private final Connection conn;

    /**
     * UserDao constructor
     * @param conn that connect SQL
     */
    public UserDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * @param user that you want to insert
     */
    public void insert(Users user) throws DataAccessException {
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO users (userName, password, email, firstName, " +
                "lastName, gender, personID) VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getPersonID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database", e);
        }
    }

    /**
     * @param userName that you want to find
     * @return new User that you want to find
     */
    public Users findUser(String userName) throws DataAccessException {
        Users user;
        ResultSet rs = null;
        String sql = "SELECT * FROM users WHERE userName = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
            rs = stmt.executeQuery();
            if (rs.next()) {
                user = new Users(rs.getString("userName"), rs.getString("password"),
                        rs.getString("email"), rs.getString("firstName"), rs.getString("lastName"),
                        rs.getString("gender"), rs.getString("personID"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding user", e);
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * @param userName that you want to delete
     */
    public void deleteUser(String userName) throws DataAccessException {
        String sql = "DELETE FROM users";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // execute the delete statement
            stmt.setString(1, userName);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while deleting user", e);
        }
    }

    /**
     * clear all users
     */
    public void clearUser() throws DataAccessException {
        String sql = "DELETE FROM users";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // execute the delete statement
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while clearing user", e);
        }
    }
}
