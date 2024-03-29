package dao.impl.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class used to create an object T with given params in the ResultSet.
 * @author Nathan Salez
 */
public abstract class RowMapper<T> {

    public abstract T map(ResultSet rs) throws SQLException;
}
