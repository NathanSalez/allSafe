package service;

import java.lang.Exception;
import javax.servlet.http.HttpServletRequest;

import dao.DAOException;
import dao.UserDao;
import model.*;
import utils.PasswordUtils;

/**
 * @author Nathan Salez
 */
public class RegisterService extends AbstractService {

    private static final String PSEUDO_FIELD = "pseudo";
    private final static int MIN_LENGTH_PSEUDO = 3;
    private final static int MAX_LENGTH_PSEUDO = 15;
    private final static String PATTERN_PSEUDO = "(\\w|\\d)*";

    private static final String PASSWORD_FIELD = "password";
    private final static int MIN_LENGTH_PASSWORD = 6;
    private final static int MAX_LENGTH_PASSWORD = 30;
    private static final String CRYPT_ALGO = "SHA-256";

    private static final String CONFIRM_PASSWORD_FIELD = "confirmPassword";

    private UserDao userDao;

    private User newUser;

    private PasswordUtils passwordUtils;

    public RegisterService(UserDao userDao) {
        super();
        this.userDao = userDao;
        passwordUtils = PasswordUtils.getInstance();
    }

    public User registerUser( HttpServletRequest request)
    {
        errors.clear();
        if( request == null)
            throw new NullPointerException("Parameter request is null.");

        String pseudo = request.getParameter(PSEUDO_FIELD).trim();
        String password = request.getParameter(PASSWORD_FIELD);
        String confirmPassword = request.getParameter(CONFIRM_PASSWORD_FIELD);

        return registerUser(pseudo,password,confirmPassword);
    }

    /**
     *
     * @param pseudo
     * @param password
     * @param confirmPassword
     * @return
     */
    private User registerUser( String pseudo, String password, String confirmPassword )
    {
        newUser = new User();
        managePseudo(pseudo);
        managePassword(password);
        manageConfirmPassword(password,confirmPassword);
        newUser.setRole( new Role("SIMPLE","Simple user"));

        if( errors.isEmpty() )
        {
            try {
                userDao.create(newUser);
            } catch( DAOException e)
            {
                e.printStackTrace();
                errors.put(DATABASE_FIELD,"Intern server error. Please contact the webmaster.");
            }
        }

        return newUser;
    }


    private void managePseudo(String pseudo)
    {
        try
        {
            validateFieldPseudo(pseudo);
        }catch(Exception e)
        {
            errors.put(PSEUDO_FIELD,e.getMessage());
        }
        newUser.setPseudo(pseudo);
    }

    private void managePassword(String password)
    {
        try
        {
            validateFieldPassword(password);
        }catch(Exception e) {
            errors.put(PASSWORD_FIELD, e.getMessage());
        }
        newUser.setPassword(password);
    }

    private void manageConfirmPassword(String password, String confirmPassword)
    {
        try
        {
            validateFieldConfirmPassword(password,confirmPassword);
        }catch(Exception e)
        {
            errors.put(CONFIRM_PASSWORD_FIELD,e.getMessage());
        }

        String encryptedPassword = passwordUtils.encryptPassword( password);

        newUser.setPassword(encryptedPassword);
    }

    /**
     * raises an exception if :
     * - pseudo is empty.
     * - pseudo is too short.
     * - pseudo is too long.
     * @param pseudo pseudo's field value.
     * @throws Exception if a condition above is respected.
     */
    private void validateFieldPseudo(String pseudo) throws Exception
    {
        if( pseudo == null || pseudo.isEmpty() )
            throw new Exception("Pseudo field is empty.");

        if( pseudo.length() < MIN_LENGTH_PSEUDO)
            throw new Exception("Pseudo is too short. Min authorized : " + MIN_LENGTH_PSEUDO + " characters.");

        if( MAX_LENGTH_PSEUDO < pseudo.length() )
            throw new Exception("Pseudo is too long. Max authorized : " + MAX_LENGTH_PSEUDO + " characters.");

        if( !pseudo.matches(PATTERN_PSEUDO))
            throw new Exception("Pseudo contains invalid characters. Please remember that only words and number characters are valid.");

        if( userDao.find(pseudo) != null )
            throw new Exception("Pseudo '" + pseudo + "' is already registered.");
    }

    /**
     * raises an exception if :
     * - password is empty.
     * - password is too short.
     * - password is too long.
     * @param password password's field value.
     * @throws Exception if a condition above is respected.
     */
    private void validateFieldPassword(String password) throws Exception
    {
        if( password == null || password.isEmpty() )
            throw new Exception("Password field is empty.");

        if( password.length() < MIN_LENGTH_PASSWORD)
            throw new Exception("Password is too short. Min authorized : " + MIN_LENGTH_PASSWORD + " characters.");

        if( MAX_LENGTH_PASSWORD < password.length() )
            throw new Exception("Pseudo is too long. Max authorized : " + MAX_LENGTH_PASSWORD + " characters.");
    }

    /**
     * raises an exception if
     * - password equals confirmPassword.
     * @param password password's field value.
     * @param confirmPassword password's confirm's field value.
     * @throws Exception f a condition above is respected.
     */
    private void validateFieldConfirmPassword(String password, String confirmPassword) throws Exception
    {
        if( password == null)
            return;

        if( !password.equals(confirmPassword) )
            throw new Exception("Confirm password is different from original password.");
    }
}
