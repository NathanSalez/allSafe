package utils;

import javax.servlet.http.HttpServletRequest;
import java.security.SecureRandom;

/**
 * @author Nathan Salez
 */
public class SecurityUtils {

    private static final int SECURITY_LEVEL = 20;

    public static final String TOKEN_FIELD = "token";

    public static String generateToken()
    {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[SECURITY_LEVEL];
        random.nextBytes(bytes);
        return bytes.toString();
    }

    /**
     * Check if the param request is vulnerable against CSRF attack.
     * @param request
     * @return
     */
    public static boolean checkRequest(HttpServletRequest request)
    {
        if( request == null)
            throw new IllegalArgumentException("param request must be not null.");

        return request.getParameter(TOKEN_FIELD).equals(request.getSession().getAttribute(TOKEN_FIELD));
    }
}
