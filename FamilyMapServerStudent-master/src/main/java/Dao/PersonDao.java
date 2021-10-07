package Dao;

import java.sql.Connection;
import Model.Persons;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * PersonDao class in Dao package
 * Handles person's data
 */
public class PersonDao {
    /**
     * private data member that connect SQL
     */
    private final Connection conn;

    /**
     * PersonDao constructor
     * @param conn connect SQL
     */
    public PersonDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * @param person that you want to insert
     */
    public void insert(Persons person) throws DataAccessException {
        String sql = "INSERT INTO persons (personID, userName, firstName, lastName, gender, " +
                "fatherID, motherID, spouseID) VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getAssociatedUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database", e);
        }
    }

    /**
     * @param personID that you want to find
     * @return new Person that you find
     */
    public Persons findPerson(String personID) throws DataAccessException {
        Persons person;
        ResultSet rs = null;
        String sql = "SELECT * FROM persons WHERE personID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                person = new Persons(rs.getString("personID"), rs.getString("userName"),
                        rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"),
                        rs.getString("fatherID"), rs.getString("motherID"), rs.getString("spouseID"));
                return person;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding person", e);
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
    public Persons findPersonByUserName(String userName) throws DataAccessException {
        Persons person;
        ResultSet rs = null;
        String sql = "SELECT * FROM persons WHERE personID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
            rs = stmt.executeQuery();
            if (rs.next()) {
                person = new Persons(rs.getString("personID"), rs.getString("userName"),
                        rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"),
                        rs.getString("fatherID"), rs.getString("motherID"), rs.getString("spouseID"));
                return person;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding person by userName", e);
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
     * @return all persons
     */
    public Persons[] findAll(String userName) throws DataAccessException {
        Persons[] persons = new Persons[0];
        Persons person;
        ResultSet rs = null;
        String sql = "SELECT * FROM persons WHERE userName = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
            rs = stmt.executeQuery();
            ArrayList<Persons> personsArray = new ArrayList<Persons>();
            while (rs.next()) {
                person = new Persons(rs.getString("personID"), rs.getString("userName"),
                        rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"),
                        rs.getString("fatherID"), rs.getString("motherID"), rs.getString("spouseID"));
                personsArray.add(person);
            }
            if (personsArray.size() > 0) {
                persons = new Persons[personsArray.size()];
                for (int i = 0; i < personsArray.size(); i++) {
                    persons[i] = personsArray.get(i);
                }
                return persons;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding all persons by userName", e);
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        throw new DataAccessException("Cannot find the user's persons", null); //changed
    }

    /**
     * @param userName that you want to delete
     */
    public void deletePerson(String userName) throws DataAccessException {
        String sql = "DELETE FROM persons WHERE userName = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // execute the delete statement
            stmt.setString(1, userName);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while deleting person", e);
        }
    }

    /**
     * clear all persons
     */
    public void clearPerson() throws DataAccessException {
        String sql = "DELETE FROM persons";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // execute the delete statement
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while clearing person", e);
        }
    }
}
