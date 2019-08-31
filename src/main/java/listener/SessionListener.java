package listener;

import dao.DAOFactory;
import dao.UserDao;
import model.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import static config.InitialisationDaoFactory.ATT_DAO_FACTORY;
import static utils.SessionUtils.USER_SESSION_FIELD;

@WebListener()
public class SessionListener implements HttpSessionListener, ServletContextListener {

    private UserDao userDao;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        DAOFactory daoFactory = (DAOFactory) servletContext.getAttribute( ATT_DAO_FACTORY );
        userDao = daoFactory.getUserDao();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

    // -------------------------------------------------------
    // HttpSessionListener implementation
    // -------------------------------------------------------
    public void sessionCreated(HttpSessionEvent se) {
    }

    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        User u = (User) session.getAttribute(USER_SESSION_FIELD);
        if( u != null) {
            System.out.println("SessionListener.Destroyed - Destroyed Session for user " + u.getPseudo());
            userDao.updateState(u.getPseudo(),false);
            // TODO : register cookie if needed
        }
    }
}
