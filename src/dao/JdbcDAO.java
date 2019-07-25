package dao;

/**
 * @author Nathan Salez
 */
public abstract class JdbcDAO<T> {

    protected RowMapper<T> mapper;
}
