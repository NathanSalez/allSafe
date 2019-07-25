package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import config.InitialisationDaoFactory;
import dao.DAOFactory;
import dao.UserDao;
import service.LoginService;

import static controller.BaseController.VIEW_FIELD_NAME;
import static utils.SessionUtils.USER_SESSION_FIELD;

import model.User;

@WebServlet("/accessible/login")
public class LoginController extends HttpServlet {

    private final static String VIEW_NOT_LOGGED = "/WEB-INF/accessible/login.jsp";
    public final static String VIEW_LOGGED = "/WEB-INF/restrictive/usersManagement.jsp";

    private LoginService loginService;

    @Override
    public void init() {
        UserDao userDao = ( (DAOFactory) getServletContext().getAttribute(InitialisationDaoFactory.ATT_DAO_FACTORY) ).getUserDao();
        loginService = new LoginService(userDao);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User loggedUser = loginService.loginUser(request);

        HttpSession session = request.getSession();
        if( loginService.getFeedback() )
        {
            session.setAttribute(USER_SESSION_FIELD,loggedUser);
            if( loginService.isStayLogged() )
            {
                // TODO : creation of cookie that will last 1 year, with a session id for security.
                /*DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String todayDateToString = dateFormat.format(new Date());
                CookieUtils.setCookie(response, CookieUtils.LAST_CONNEXION_FIELD, todayDateToString);*/
            }
            else
            {
                // TODO : cookie will be deleted at the navigator's close.
                //CookieUtils.setCookie( response, CookieUtils.LAST_CONNEXION_FIELD, "", -1 );
            }
            String urlRedirection = DisconnectController.URL_BASE + request.getContextPath() + "/restrictive/users/management";
            response.sendRedirect(urlRedirection);
        }
        else
        {
            session.setAttribute(USER_SESSION_FIELD,null);
            request.setAttribute("feedback", loginService.getFeedback() ? "ok" : "ko");
            request.setAttribute("form", loginService);
            request.setAttribute("input",loggedUser);
            request.setAttribute(VIEW_FIELD_NAME,"login");
            this.getServletContext().getRequestDispatcher( VIEW_NOT_LOGGED ).forward( request, response );
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute(VIEW_FIELD_NAME,"login");
        this.getServletContext().getRequestDispatcher( VIEW_NOT_LOGGED ).forward( request, response );
    }
}
