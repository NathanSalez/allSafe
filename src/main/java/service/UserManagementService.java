package service;

import dao.DAOException;
import dao.RoleDao;
import dao.UserDao;
import model.Role;
import model.User;

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

    public boolean updateUserRole(User executorUser, String pseudoAffectedUser, Role newRole)
    {
        errors.clear();
        try {
            User affectedUser = userDao.find(pseudoAffectedUser);
            if (executorUser == null || affectedUser == null || newRole == null)
            {
                errors.put("error","Sent request is invalid.");
                return false;
            }
            if( affectedUser.getRole().equals(newRole))
            {
                errors.put("error","User <strong>" + affectedUser.getPseudo() + "</strong> not updated, new role equals to current role.");
                return false;
            }
            if (!roleDao.hasTheRightTo(executorUser.getRole(), "updateAs", affectedUser.getRole(), newRole))
            {
                errors.put("error","User <strong>" + affectedUser.getPseudo() + "</strong> not updated, check your rights.");
                return false;
            }
            userDao.updateRole(affectedUser.getPseudo(), newRole);
        } catch (DAOException e)
        {
            errors.put(DATABASE_FIELD,"Syntax Error on SQL request.");
        }
        return true;
    }

    public boolean deleteUser(User executorUser,String pseudoAffectedUser)
    {
        errors.clear();
        try
        {
            User affectedUser = userDao.find(pseudoAffectedUser);
            if (executorUser == null || affectedUser == null)
            {
                errors.put("error","Sent request is invalid.");
                return false;
            }
            if( executorUser.equals(affectedUser) )
            {
                errors.put("error","User <strong>" + affectedUser.getPseudo() + "</strong> not deleted, you can't delete your account.");
            }
            if (!roleDao.hasTheRightTo(executorUser.getRole(), "delete", affectedUser.getRole(),null))
            {
                errors.put("error","User <strong>" + affectedUser.getPseudo() + "</strong> not deleted, check your rights.");
                return false;
            }
            userDao.delete(affectedUser.getPseudo());
        } catch (DAOException e)
        {
            errors.put(DATABASE_FIELD,"Syntax Error on SQL request.");
        }
        return false;
    }
}
