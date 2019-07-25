package controller;

import config.InitialisationDaoFactory;
import dao.DAOFactory;
import dao.RoleDao;
import dao.UserDao;
import model.Role;
import model.User;
import service.UserManagementService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

import static controller.BaseController.FORM_ATTRIBUTE;
import static controller.BaseController.VIEW_FIELD_NAME;


@WebServlet("/restrictive/users/management")
public class UserManagementController extends HttpServlet {

    private final static String VIEW = "/WEB-INF/restrictive/usersManagement.jsp";

    private final static String USERS_ATTRIBUTE = "users";
    private final static String COUNT_LOGGED_USERS_ATTRIBUTE = "loggedUsers";
    private final static String FEEDBACK_ATTRIBUTE = "feedback";
    private final static String ROLES_ATTRIBUTE = "roles";
    private UserManagementService userManagementService;

    @Override
    public void init() {
        DAOFactory daoFactory = (DAOFactory) getServletContext().getAttribute(InitialisationDaoFactory.ATT_DAO_FACTORY);
        UserDao userDao =  daoFactory.getUserDao();
        RoleDao roleDao = daoFactory.getRoleDao();

        userManagementService = new UserManagementService(userDao,roleDao);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(VIEW_FIELD_NAME,"usersTable");
        Collection<User> users = userManagementService.getAllUsers();
        Long countLoggedUsers = userManagementService.countLoggedUsers();
        Role[] roles = userManagementService.getRoles();
        request.setAttribute(FORM_ATTRIBUTE,userManagementService);
        if( userManagementService.getFeedback())
        {
            request.setAttribute(USERS_ATTRIBUTE,users);
            request.setAttribute(COUNT_LOGGED_USERS_ATTRIBUTE,countLoggedUsers);
            request.setAttribute(ROLES_ATTRIBUTE,roles);
        }
        else
        {
            request.setAttribute(FEEDBACK_ATTRIBUTE,"ko");
        }
        this.getServletContext().getRequestDispatcher( VIEW ).forward( request, response );

    }
}
