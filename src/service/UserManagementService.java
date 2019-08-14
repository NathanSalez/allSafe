package service;

import dao.DAOException;
import dao.UserDao;
import dao.RoleDao;
import model.User;

import model.Role;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * @author Nathan Salez
 */
public class UserManagementService extends AbstractService {

    private UserDao userDao;

    private static final String AFFECTED_ROLE_FIELD = "affectedRole";

    private RoleDao roleDao;

    public UserManagementService(UserDao userDao, RoleDao roleDao) {
        super();
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    public Collection<User> getAllUsers()
    {
        errors.clear();
        Collection<User> users = null;
        try
        {
            users = userDao.findAll();
        } catch (DAOException e)
        {
            errors.put(DATABASE_FIELD,"Syntax Error on SQL request.");
        }
        return users;
    }

    public Long countLoggedUsers()
    {
        errors.clear();
        Long returnedValue = 0L;
        try
        {
            returnedValue = userDao.countLoggedUsers();
        } catch (DAOException e)
        {
            errors.put(DATABASE_FIELD,"Syntax Error on SQL request.");
        }
        return returnedValue;
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
        Role affectedRole = Role.getRole(request.getParameter(AFFECTED_ROLE_FIELD));
        try
        {
            returnedValue = roleDao.getNewPossibleRoles(executorRole,affectedRole);
        } catch (DAOException e)
        {
            errors.put(DATABASE_FIELD,"Syntax Error on SQL request.");
        }
        return returnedValue;
    }
}
