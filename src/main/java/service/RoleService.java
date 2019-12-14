package service;

import dao.DAOException;
import dao.RoleDao;
import model.Role;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * @author Nathan Salez
 */
public class RoleService extends AbstractService {

    private static final String AFFECTED_ROLE_FIELD = "affectedRole";

    private RoleDao roleDao;

    public RoleService(RoleDao roleDao) {
        super();
        this.roleDao = roleDao;
    }

    public Role[] getRoles()
    {
        Role[] returnedValue = null;
        try
        {
            returnedValue = roleDao.findAll();
        } catch (DAOException e)
        {
            errors.put(DATABASE_FIELD,"Syntax Error on SQL request.");
        }
        return returnedValue;
    }

    public Collection<Role> getNewPossibleRoles(Role executorRole, HttpServletRequest request)
    {
        Collection<Role> returnedValue = null;
        Role affectedRole = new Role(request.getParameter(AFFECTED_ROLE_FIELD));
        try
        {
            returnedValue = roleDao.getNewPossibleRoles(executorRole,affectedRole);
        } catch (DAOException e)
        {
            errors.put(DATABASE_FIELD,"Syntax Error on SQL request.");
        }
        return returnedValue;
    }

    public Collection<String> getPossibleActions(Role executorRole, Role affectedRole)
    {
        Collection<String> returnedValue = null;
        try
        {
            returnedValue = roleDao.getPossibleActions(executorRole,affectedRole);
        } catch (DAOException e)
        {
            errors.put(DATABASE_FIELD,"Syntax Error on SQL request.");
        }
        return returnedValue;

    }
}
