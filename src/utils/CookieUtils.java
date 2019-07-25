package utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Nathan Salez
 */
public class CookieUtils {

    public static final int COOKIE_MAX_AGE = 60 * 60 * 24 * 365;  // 1 year
    /**
     *
     * @param response
     * @param name
     * @param value
     * @param expiry
     */
    public static void setCookie(HttpServletResponse response, String name, String value, int expiry ) {
        Cookie cookie = new Cookie( name, value );
        cookie.setMaxAge( expiry );
        response.addCookie( cookie );
    }

    public static void setCookie(HttpServletResponse response, String name, String value) {
        setCookie(response,name,value,COOKIE_MAX_AGE);
    }


    /**
     *
     * @param request
     * @param nom
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, String name ) {
        Cookie[] cookies = request.getCookies();
        if ( cookies != null ) {
            for ( Cookie cookie : cookies ) {
                if ( name.equals( cookie.getName() ) ) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
