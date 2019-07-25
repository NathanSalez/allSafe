package dao;

import model.Role;
import utils.DaoUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Nathan Salez
 */
public class RoleJdbcDao extends JdbcDAO<Role> implements RoleDao {

    private DAOFactory daoFactory;

    RoleJdbcDao(DAOFactory daoFactory)
    {
        this.daoFactory = daoFactory;
        mapper = null;
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

        args.add(executorRole.toString());
        args.add(action);

        if( affectedRole != null )
        {
            sql += " and affectedRole = ?";
            args.add(affectedRole.toString());
            if( newRole != null)
            {
                sql += " and newRole = ?";
                args.add(newRole.toString());
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
        return Role.values();
    }
}
