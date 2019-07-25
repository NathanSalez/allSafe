package dao;

import model.Role;
import model.User;
import utils.DaoUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.TreeSet;

/**
 * @author Nathan Salez
 */
public class UserJdbcDao extends JdbcDAO<User> implements UserDao {

    private DAOFactory daoFactory;

    private static final String SELECT_USER = "SELECT * FROM users WHERE pseudo = ?";

    private static final String INSERT_USER = "INSERT INTO users(pseudo,password,date_inscription,role) values(?,?,NOW(),'SIMPLE')";

    private static final String CONNECT_USER = "UPDATE users SET logged = 1 WHERE pseudo = ?";

    private static final String DISCONNECT_USER = "UPDATE users SET logged = 0 WHERE pseudo = ?";

    private static final String SELECT_ALL_USERS = "SELECT * FROM users";

    private static final String COUNT_LOGGED_USERS = "SELECT COUNT(*) AS LOGGED_USERS FROM users where logged = 1";

    private static final String DELETE_USER = "DELETE FROM users WHERE pseudo = ?";

    UserJdbcDao(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
        mapper = new RowMapper<User>() {
            @Override
            public User map(ResultSet rs) throws SQLException {
                User u = new User();
                u.setId(rs.getLong("id"));
                u.setPseudo(rs.getString("pseudo"));
                u.setPassword(rs.getString("password"));
                u.setRole(Role.getRole((String) rs.getObject("role")));
                u.setRegisterDate(rs.getDate("date_inscription"));
                u.setLogged( rs.getBoolean("logged"));
                return u;
            }
        };
    }

    @Override
    public void create(User user) throws DAOException {
        if( user == null)
            throw new NullPointerException("user must be not null.");

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet generatedIdSet;
        try {
            connection = daoFactory.getConnection();
            pstmt = DaoUtils.buildPreparedStatement(connection, INSERT_USER, true,
                    user.getPseudo(), user.getPassword());
            int status = pstmt.executeUpdate();
            if (status == 0)
                throw new DAOException("Failed to create a new user in database : no line inserted.");
            generatedIdSet = pstmt.getGeneratedKeys();
            if (generatedIdSet.next())
                user.setId(generatedIdSet.getLong(1));
            else
                throw new DAOException("Failed to create a new user in database : no id generated.");
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DaoUtils.closeDatabaseConnexion(pstmt, connection);
        }
    }


    @Override
    public User find(String pseudo) throws DAOException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = daoFactory.getConnection();
            pstmt = DaoUtils.buildPreparedStatement(connection, SELECT_USER, false, pseudo);
            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                user = mapper.map(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DaoUtils.closeDatabaseConnexion(resultSet, pstmt, connection);
        }
        return user;
    }

    @Override
    public void updateState(String pseudo, boolean connect) throws DAOException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        String request = connect ? CONNECT_USER : DISCONNECT_USER;
        try {
            connection = daoFactory.getConnection();
            pstmt = DaoUtils.buildPreparedStatement(connection, request, false, pseudo);
            int nbAffectedLines = pstmt.executeUpdate();
            if( nbAffectedLines == 0 )
            {
                throw new DAOException("User " + pseudo + " not found.");
            } else if( nbAffectedLines != 1)
            {
                throw new DAOException("User " + pseudo + "is not unique.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DaoUtils.closeDatabaseConnexion(pstmt, connection);
        }
    }

    @Override
    public Long countLoggedUsers() throws DAOException {
        Connection connection = null;
        ResultSet result = null;
        Long nbLoggedUsers;
        try {
            connection = daoFactory.getConnection();
            result = DaoUtils.executeSelectStatement(connection, COUNT_LOGGED_USERS);
            if( result.next())
                nbLoggedUsers = result.getLong("LOGGED_USERS");
            else
                throw new DAOException("No result found in users table.");
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DaoUtils.closeDatabaseConnexion(result, connection);
        }
        return nbLoggedUsers;
    }

    @Override
    public Collection<User> findAll() throws DAOException {
        Connection connection = null;
        ResultSet results = null;
        TreeSet<User> collection = new TreeSet<>();
        try {
            connection = daoFactory.getConnection();
            results = DaoUtils.executeSelectStatement(connection, SELECT_ALL_USERS);
            while (results.next()) {
                collection.add(mapper.map(results));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DaoUtils.closeDatabaseConnexion(results, connection);
        }

        return collection;
    }

    @Override
    public void delete(String pseudo) throws DAOException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = daoFactory.getConnection();
            pstmt = DaoUtils.buildPreparedStatement(connection, DELETE_USER, false, pseudo);
            if( pstmt.executeUpdate() != 1 )
            {
                throw new DAOException("User " + pseudo + " not found.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DaoUtils.closeDatabaseConnexion(pstmt, connection);
        }
    }
}
