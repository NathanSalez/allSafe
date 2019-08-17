package filter;

import utils.SessionUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static utils.SessionUtils.USER_SESSION_FIELD;


import java.io.IOException;

@WebFilter("/restrictive/*")
public class RestrictiveFilter implements Filter {

    private static String PUBLIC_ACCESS = "/WEB-INF/accessible/publicAccess.jsp";
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        /* Cast des objets request et response */
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        if ( SessionUtils.getFieldValue(request,USER_SESSION_FIELD) == null ) {
            request.getRequestDispatcher( PUBLIC_ACCESS ).forward( request, response );
        } else {
            chain.doFilter( request, response );
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
