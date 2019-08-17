package dao.impl.jdbc;

/**
 * @author Nathan Salez
 */
public abstract class JdbcDAO<T> {

    protected RowMapper<T> mapper;
}
