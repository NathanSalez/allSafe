package service;

import dao.DAOException;
import dao.UserDao;
import model.Role;
import model.User;

import javax.servlet.http.HttpServletRequest;
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

    public boolean updateUserRole(HttpServletRequest request, User affectedUser, Role newRole)
    {
        if( request == null)
            return false;
        // TODO : update user - write service method
        // check if current user session can update, and then update affected user.
        return false;
    }
}
