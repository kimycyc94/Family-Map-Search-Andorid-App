package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import Model.Events;

/**
 * EventDao class in Dao package
 * Handles event data
 */
public class EventDao {
    /**
     * private data member that connect SQL
     */
    private final Connection conn;

    /**
     * constructor EventDao
     * @param conn that connect SQL
     */
    public EventDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * @param event that you want to insert
     */
    public void insert(Events event) throws DataAccessException {
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO events (eventID, userName, personID, latitude, longitude, " +
                "country, city, eventType, year) VALUES(?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, event.getEventID());
            stmt.setString(2, event.getAssociatedUsername());
            stmt.setString(3, event.getPersonID());
            stmt.setDouble(4, event.getLatitude());
            stmt.setDouble(5, event.getLongitude());
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEventType());
            stmt.setInt(9, event.getYear());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database", e);
        }
    }

    /**
     * @param eventID that you want to find
     * @return new Event that you find
     */
    public Events findEvent(String eventID) throws DataAccessException {
        Events event;
        ResultSet rs = null;
        String sql = "SELECT * FROM events WHERE eventID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                event = new Events(rs.getString("eventID"), rs.getString("userName"),
                        rs.getString("personID"), rs.getDouble("latitude"), rs.getDouble("longitude"),
                        rs.getString("country"), rs.getString("city"), rs.getString("eventType"),
                        rs.getInt("year"));
                return event;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding event by eventID", e);
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
     * @param userName userName to find person
     * @return person
     */
    public Events findEventByUserName(String userName) throws DataAccessException {
        Events event;
        ResultSet rs = null;
        String sql = "SELECT * FROM events WHERE userName = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
            rs = stmt.executeQuery();
            if (rs.next()) {
                event = new Events(rs.getString("eventID"), rs.getString("userName"),
                        rs.getString("personID"), rs.getDouble("latitude"), rs.getDouble("longitude"),
                        rs.getString("country"), rs.getString("city"), rs.getString("eventType"),
                        rs.getInt("year"));
                return event;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding event by userName", e);
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        throw new DataAccessException("Cannot find the userName", null);  //changed
    }

    /**
     * @return all events
     */
    public Events[] findAll(String userName) throws DataAccessException {
        Events[] events = new Events[0];
        Events event;
        ResultSet rs = null;
        String sql = "SELECT * FROM events WHERE userName = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
            rs = stmt.executeQuery();
            ArrayList<Events>  eventsArray = new ArrayList<Events>();
            while (rs.next()) {
                event = new Events(rs.getString("eventID"), rs.getString("userName"),
                        rs.getString("personID"), rs.getDouble("latitude"), rs.getDouble("longitude"),
                        rs.getString("country"), rs.getString("city"), rs.getString("eventType"),
                        rs.getInt("year"));
                eventsArray.add(event);
            }
            if (eventsArray.size() > 0) {
                events = new Events[eventsArray.size()];
                for (int i = 0; i < eventsArray.size(); i++) {
                    events[i] = eventsArray.get(i);
                }
                return events;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding all events by userName", e);
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
    public void deleteEvent(String userName) throws DataAccessException {
        String sql = "DELETE FROM events WHERE userName = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while deleting event", e);
        }
    }

    /**
     * clear all events
     */
    public void clearEvent() throws DataAccessException {
        String sql = "DELETE FROM events";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // execute the delete statement
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while clearing event", e);
        }
    }
}
