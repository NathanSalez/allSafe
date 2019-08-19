package controller;

import config.InitialisationDaoFactory;
import dao.DAOFactory;
import dao.UserDao;
import model.User;
import service.UserManagementService;
import utils.SecurityUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

import static controller.BaseController.FORM_ATTRIBUTE;
import static controller.BaseController.VIEW_FIELD_NAME;
import static utils.SecurityUtils.TOKEN_FIELD;


@WebServlet("/restrictive/users/management")
public class UserManagementController extends HttpServlet {

    private final static String VIEW = "/WEB-INF/template/restrictive/usersManagement.jsp";

    private final static String USERS_ATTRIBUTE = "users";
    private final static String FEEDBACK_ATTRIBUTE = "feedback";
    private UserManagementService userManagementService;

    @Override
    public void init() {
        DAOFactory daoFactory = (DAOFactory) getServletContext().getAttribute(InitialisationDaoFactory.ATT_DAO_FACTORY);
        UserDao userDao =  daoFactory.getUserDao();
        userManagementService = new UserManagementService(userDao);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(VIEW_FIELD_NAME,"usersTable");
        Collection<User> users = userManagementService.getAllUsers();
        request.setAttribute(FORM_ATTRIBUTE,userManagementService);
        if( userManagementService.getFeedback())
        {
            // protection against CSRF
            String CSRFToken = SecurityUtils.generateToken();
            request.getSession().setAttribute(TOKEN_FIELD,CSRFToken);
            request.setAttribute(USERS_ATTRIBUTE,users);
        }
        else
        {
            request.setAttribute(FEEDBACK_ATTRIBUTE,"ko");
        }
        this.getServletContext().getRequestDispatcher( VIEW ).forward( request, response );

    }
}
