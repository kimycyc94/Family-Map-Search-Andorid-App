package Dao;

import Model.AuthToken;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * AuthTokenDao class in Dao package
 * Handles authentication token
 */
public class AuthTokenDao {
    /**
     * private data member that connect SQL
     */
    private final Connection conn;

    /**
     * constructor AuthTokenDao
     * @param conn that connect SQL
     */
    public AuthTokenDao(Connection conn) { this.conn = conn; }

    /**
     * @param token that you want to add
     */
    public void addAuthToken(AuthToken token) throws DataAccessException {
        String sql = "INSERT INTO authToken(AuthToken, userName) VALUES(?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, token.getAuthtoken());
            stmt.setString(2, token.getAssociatedUsername());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database", e);
        }
    }

    public AuthToken findTokenFromUserName(String userName) throws DataAccessException {
        AuthToken authToken;
        ResultSet rs = null;
        String sql = "SELECT * FROM authToken WHERE userName = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
            rs = stmt.executeQuery();
            if (rs.next()) {
                authToken = new AuthToken(rs.getString("AuthToken"), rs.getString("userName"));
                return authToken;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding authToken", e);
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
     * @param token that you want to find
     * @return new AuthTokenDao
     */
    public AuthToken findAuthToken(String token) throws DataAccessException {
        AuthToken authToken;
        ResultSet rs = null;
        String sql = "SELECT * FROM authToken WHERE AuthToken = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, token);
            rs = stmt.executeQuery();
            if (rs.next()) {
                authToken = new AuthToken(rs.getString("AuthToken"), rs.getString("userName"));
                return authToken;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding authToken", e);
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
     * @return Find all authTokens
     */
    public AuthToken[] findAll(String userName) throws DataAccessException {
        AuthToken[] authTokens = new AuthToken[0];
        AuthToken authToken;
        ResultSet rs = null;
        String sql = "SELECT * FROM authToken WHERE userName = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
            rs = stmt.executeQuery();
            ArrayList<AuthToken> tokensArray = new ArrayList<AuthToken>();
            while (rs.next()) {
                authToken = new AuthToken(rs.getString("authToken"), rs.getString("userName"));
                tokensArray.add(authToken);
            }
            if (tokensArray.size() > 0) {
                authTokens = new AuthToken[tokensArray.size()];
                for (int i = 0; i < tokensArray.size(); i++) {
                    authTokens[i] = tokensArray.get(i);
                }
                return authTokens;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding all authTokens", e);
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        throw new DataAccessException("Cannot find the authTokens", null);  //changed
    }

    /**
     * Delete authToken
     * @param token
     * @throws DataAccessException
     */
    public void deleteAuthToken(String token) throws DataAccessException {
        String sql = "DELETE FROM authToken WHERE AuthToken = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // execute the delete statement
            stmt.setString(1, token);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while deleting authToken", e);
        }
    }

    /**
     * clear all authToken
     */
    public void clearAuthToken() throws DataAccessException {
        String sql = "DELETE FROM authToken";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // execute the delete statement
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while clearing authToken", e);
        }
    }
}
