package utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Nathan Salez
 */
public class SessionUtils {
    public static final String USER_SESSION_FIELD = "userSession";

    public static final String COOKIE_VERIFICATION_FIELD = "safeCookie";


    /**
     *
     * @param request
     * @param fieldName
     * @return
     */
    public static Object getFieldValue(HttpServletRequest request, String fieldName ) {

        if( fieldName == null || fieldName.isEmpty() )
            return null;
        if( request == null)
            return null;

        HttpSession userSession = request.getSession();
        return userSession.getAttribute( fieldName );
    }
}
