package Dao;

/**
 * DataAccessException class in Dao package
 * Throws data Access exception
 */
public class DataAccessException extends Exception {
    public DataAccessException(String message, Exception ex)
    {
        super(message, ex);
    }
    DataAccessException()
    {
        super();
    }
}
