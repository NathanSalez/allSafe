package controller;

import com.google.gson.Gson;
import config.InitialisationDaoFactory;
import dao.DAOFactory;
import dao.RoleDao;
import dao.UserDao;
import model.Role;
import model.User;
import service.UserManagementService;
import utils.SecurityUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/users/")
public class UsersAjaxController extends HttpServlet {

    private UserManagementService userManagementService;

    @Override
    public void init() {
        DAOFactory daoFactory = (DAOFactory) getServletContext().getAttribute(InitialisationDaoFactory.ATT_DAO_FACTORY);
        UserDao userDao =  daoFactory.getUserDao();
        RoleDao roleDao = daoFactory.getRoleDao();
        userManagementService = new UserManagementService(userDao,roleDao);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        response.setContentType("application/json");
        String jsonResponse = "";
        switch(action)
        {
            case "updateUser" :
                jsonResponse = doPostUpdateUserRole(request);
                break;

            case "deleteUser" :
                break;

            default:
                jsonResponse = doMethodError();
        }
        response.getWriter().write(jsonResponse);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        response.setContentType("application/json");
        String jsonResponse = "";
        switch(action)
        {
            case "countNbUsers" :
                jsonResponse = doGetSearchConnectedUsers();
                break;

            case "searchUsers" :
                break;

            default:
                jsonResponse = doMethodError();
        }
        response.getWriter().write(jsonResponse);
    }

    private String doGetSearchConnectedUsers()
    {
        Long loggedUsers = userManagementService.countLoggedUsers();
        Map<String,Object> mapResponse = new HashMap<>();
        if( userManagementService.getFeedback() )
        {
            mapResponse.put("feedback","ok");
            mapResponse.put("nbConnectedUsers",loggedUsers);
        }
        else
        {
            mapResponse.put("feedback","ko");
            mapResponse.put("error", userManagementService.getErrors());
        }
        return new Gson().toJson(mapResponse);
    }

    private String doPostUpdateUserRole(HttpServletRequest request)
    {
        Map<String,Object> mapResponse = new HashMap<>();
        Map<String,Object> updateInfos = new HashMap<>();
        String pseudoAffectedUser = request.getParameter("pseudo");
        Role currentRole = Role.getRole(request.getParameter("currentRole"));
        Role newRole = Role.getRole(request.getParameter("newRole"));
        updateInfos.put("affectedUser", pseudoAffectedUser);
        updateInfos.put("newRole",newRole.name());
        mapResponse.put("update",updateInfos);
        mapResponse.put("feedback","ko");
        if( SecurityUtils.checkRequest(request) )
        {
            User affectedUser = new User();
            affectedUser.setPseudo(pseudoAffectedUser);
            affectedUser.setRole(currentRole);
            User executorUser = (User) SessionUtils.getFieldValue(request,SessionUtils.USER_SESSION_FIELD);
            if( userManagementService.updateUserRole(executorUser,affectedUser,newRole) ) {
                mapResponse.put("feedback", "ok");
            } else
            {
                mapResponse.put("error","User not updated, check your rights.");
            }
        } else
        {
            mapResponse.put("error","Request sent is vulnerable against CSRF.");
        }

        return new Gson().toJson(mapResponse);
    }

    private String doMethodError()
    {
        Map<String,Object> mapResponse = new HashMap<>();
        mapResponse.put("feedback","ko");
        mapResponse.put("error", "Action parameter not found.");
        return new Gson().toJson(mapResponse);
    }
}
