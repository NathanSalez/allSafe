package service;

import dao.DAOException;
import dao.UserDao;
import dao.RoleDao;
import model.User;

import model.Role;
import java.util.Collection;

/**
 * @author Nathan Salez
 */
public class UserManagementService extends AbstractService {

    private UserDao userDao;

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
}
