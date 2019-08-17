package filter;

import utils.SessionUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static controller.DisconnectController.URL_BASE;
import static utils.SessionUtils.USER_SESSION_FIELD;

@WebFilter("/accessible/*")
public class LoggedFilter implements Filter {

    private static final String VIEW_LOGGED = "/WEB-INF/index.jsp";
    public void destroy() {
    }

    /**
     * Check if user's is not already logged in.
     * If yes, do a forward to page usersManagement.jsp
     * @param req
     * @param resp
     * @param chain
     * @throws ServletException
     * @throws IOException
     */
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        if ( SessionUtils.getFieldValue(request,USER_SESSION_FIELD) == null ) {
            chain.doFilter( request, response );
        } else {
            String urlRedirection = URL_BASE + request.getContextPath() + "/welcome";
            response.sendRedirect(urlRedirection);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
