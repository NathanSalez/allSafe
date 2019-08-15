package service;

import dao.DAOException;
import dao.UserDao;
import model.User;

import java.util.Collection;

/**
 * @author Nathan Salez
 */
public class UserManagementService extends AbstractService {

    private UserDao userDao;

    public UserManagementService(UserDao userDao) {
        super();
        this.userDao = userDao;
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
}
