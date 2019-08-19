package service;

import dao.DAOException;
import dao.RoleDao;
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

    public boolean updateUserRole(User executorUser, User affectedUser, Role newRole)
    {
        try {
            if (executorUser == null || affectedUser == null || newRole == null)
                return false;
            if( affectedUser.getRole().equals(newRole))
                return false;
            if (!roleDao.hasTheRightTo(executorUser.getRole(), "updateAs", affectedUser.getRole(), newRole))
                return false;
            userDao.updateRole(affectedUser.getPseudo(), newRole);
        } catch (DAOException e)
        {
            errors.put(DATABASE_FIELD,"Syntax Error on SQL request.");
        }
        return true;
    }
}
