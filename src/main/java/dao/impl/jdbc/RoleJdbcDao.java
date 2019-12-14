package dao.impl.jdbc;

import dao.DAOException;
import dao.DAOFactory;
import dao.RoleDao;
import model.Role;
import utils.DaoUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Nathan Salez
 */
public class RoleJdbcDao extends JdbcDAO<Role> implements RoleDao {

    private DAOFactory daoFactory;

    private static final String GET_POSSIBLE_ROLES_QUERY = "SELECT newRole from rights where executorRole = ? and affectedRole = ? and action='updateAs'";

    private static final String GET_POSSIBLE_ACTIONS_QUERY = "SELECT action from rights where executorRole = ? and affectedRole = ?";

    public RoleJdbcDao(DAOFactory daoFactory)
    {
        this.daoFactory = daoFactory;
        mapper = new RowMapper<Role>() {
            @Override
            public Role map(ResultSet rs) throws SQLException {
                Role r = new Role();
                r.setCode( rs.getString("code"));
                r.setDescription( rs.getString("description"));
                return r;
            }
        };
    }

    /**
     * return a boolean meaning if an user has the right to do an action.
     * An action can be an update.
     * @param executorRole the executor role.
     * @param action a string verb.
     * @param affectedRole
     * @param newRole
     * @return false if executorUser or action is null.
     * @throws DAOException
     */
    @Override
    public boolean hasTheRightTo(Role executorRole, String action, Role affectedRole, Role newRole) throws DAOException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        ArrayList<Object> args = new ArrayList<>(4);
        String sql = "SELECT * from rights where executorRole = ? and action = ?";
        boolean isAllowed = false;
        if( executorRole == null || action == null || action.isEmpty())
            return false;

        args.add(executorRole.getCode());
        args.add(action);

        if( affectedRole != null )
        {
            sql += " and affectedRole = ?";
            args.add(affectedRole.getCode());
            if( newRole != null)
            {
                sql += " and newRole = ?";
                args.add(newRole.getCode());
            }
        }
        try {
            connection = daoFactory.getConnection();
            pstmt = DaoUtils.buildPreparedStatement(connection, sql, false, args.toArray());
            resultSet = pstmt.executeQuery();
            if( resultSet.next() )
                isAllowed = true;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DaoUtils.closeDatabaseConnexion(resultSet, pstmt, connection);
        }

        return isAllowed;
    }

    @Override
    public Role[] findAll() throws DAOException {
        return null;
    }

    @Override
    public Collection<Role> getNewPossibleRoles(Role executorRole, Role affectedRole) throws DAOException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        ArrayList<Role> newPossibleRoles = new ArrayList<>(3);
        try {
            connection = daoFactory.getConnection();
            pstmt = DaoUtils.buildPreparedStatement(connection, GET_POSSIBLE_ROLES_QUERY, false, executorRole.getCode(), affectedRole.getCode() );
            resultSet = pstmt.executeQuery();
            while(resultSet.next()) {
                newPossibleRoles.add(mapper.map(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DaoUtils.closeDatabaseConnexion(resultSet, pstmt, connection);
        }
        return newPossibleRoles;
    }

    @Override
    public List<String> getPossibleActions(Role executorRole, Role affectedRole) throws DAOException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        ArrayList<String> possibleActions = new ArrayList<>();
        try {
            connection = daoFactory.getConnection();
            pstmt = DaoUtils.buildPreparedStatement(connection, GET_POSSIBLE_ACTIONS_QUERY, false, executorRole.getCode(), affectedRole.getCode() );
            resultSet = pstmt.executeQuery();
            while(resultSet.next()) {
                possibleActions.add( resultSet.getString(1));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DaoUtils.closeDatabaseConnexion(resultSet, pstmt, connection);
        }
        return possibleActions;
    }
}
