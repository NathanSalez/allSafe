package service;

import javax.servlet.http.HttpServletRequest;

import dao.DAOException;
import dao.UserDao;
import model.*;
import utils.PasswordUtils;

/**
 * @author Nathan Salez
 */
public class LoginService extends AbstractService {

    private static final String PSEUDO_FIELD = "pseudo";
    private static final String PASSWORD_FIELD = "password";
    private static final String STAY_LOGGED_FIELD = "stayLogged";

    private boolean stayLogged;

    private UserDao userDao;

    private PasswordUtils passwordUtils;

    public LoginService(UserDao userDao) {
        super();
        this.userDao = userDao;
        passwordUtils = PasswordUtils.getInstance();
    }

    public boolean isStayLogged() {
        return stayLogged;
    }

    public User loginUser(HttpServletRequest request)
    {
        errors.clear();
        String pseudo = request.getParameter(PSEUDO_FIELD);
        String password = request.getParameter(PASSWORD_FIELD);
        String stayLogged = request.getParameter(STAY_LOGGED_FIELD);

        this.stayLogged = "on".equals(stayLogged);

        User loggedUser = null;
        try
        {
            loggedUser = userDao.find(pseudo);
            if( loggedUser == null )
            {
                errors.put(PSEUDO_FIELD,"Pseudo not found.");
            }
            else if( !passwordUtils.checkPassword(password,loggedUser.getPassword()))
            {
                errors.put(PASSWORD_FIELD,"Invalid password.");
            }
            else
            {
                userDao.updateState(loggedUser.getPseudo(),true);
            }
        } catch( DAOException e)
        {
            e.printStackTrace();
            errors.put(DATABASE_FIELD,"Intern server error. Please contact the webmaster.");
        }

        return loggedUser;
    }
}
